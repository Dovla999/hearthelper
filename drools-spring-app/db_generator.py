import requests
import random
import re
import datetime

response = requests.get(
    "https://api.hearthstonejson.com/v1/139719/enUS/cards.collectible.json"
)

cards = response.json()

rarity_cost = {"COMMON": 40, "RARE": 200, "EPIC": 400, "LEGENDARY": 1600}
classes = (
    "WARLOCK,WARRIOR,MAGE,PRIEST,DRUID,SHAMAN,PALADIN,DEMON_HUNTER,ROGUE,HUNTER".split(
        ","
    )
)


USER_CONSTANT = 100
DECK_CONSTANT = 100
MATCH_CONSTANT = 1000
META_CONSTANT = 200

deck_cards = {}

deck_types = "AGGRO, CONTROL, ATTRITION, MIDGAME, MILL, COMBO, BIG_CARDS, TEMPO, ANTI_AGGRO".strip().split(
    ","
)

meta_ranks = "RANKS_25_20, RANKS_20_15, RANKS_15_10, RANKS_10_5, RANKS_5_1, RANKS_LEGEND_LOW, RANKS_LEGEND_MID, RANKS_LEGEND_HIGH".strip().split(
    ","
)


def insert_template(table_name, column_list, row_list):
    return f'insert into {table_name} ({",".join(column_list)}) values {",".join(row_list)}'


cards_columns = [
    "atk",
    "card_class",
    "cost",
    "crafting_cost",
    "def",
    "description",
    "is_centerpiece",
    "name",
]


cards_rows = [
    [
        card["attack"] if card["attack"] else 0,
        card["cardClass"] if card["cardClass"] in classes else "NEUTRAL",
        card["cost"] if card["cost"] else 0,
        rarity_cost[card["rarity"] if card["rarity"] in rarity_cost else "COMMON"],
        card["health"] if card["health"] else 0,
        re.sub(
            "[^0-9a-zA-Z ]+",
            "",
            card["text"].replace("'", "").replace("\n", "").strip(),
        )
        if "text" in card and card["text"]
        else "random description",
        "true"
        if random.randint(1, 80) == 5 and card["cardClass"] not in classes
        else "false",
        re.sub(
            "[^0-9a-zA-Z ]+",
            "",
            card["name"].replace("'", "").replace("\n", "").strip(),
        ),
    ]
    for card in [card for card in cards if card["type"] == "MINION"]
]


def generate_rows_cards(cards_rows):
    return [
        f"({row[0]}, '{row[1]}',{row[2]}, {row[3]}, {row[4]}, '{row[5]}', '{row[6]}', '{row[7]}')"
        for row in cards_rows
    ]


def generate_rows_decks(deck_rows):
    return [f"('{row[0]}','{row[1]}','{row[2]}','{row[3]}')" for row in deck_rows]


card_insert = insert_template("card", cards_columns, generate_rows_cards(cards_rows))


deck_columns = ["category", "deck_hero", "name", "strategy"]

deck_rows = [
    [
        deck_types[random.randint(0, len(deck_types) - 1)],
        classes[random.randint(0, len(classes) - 1)],
        "",
        "very special strategy you will never be able to execute! :)",
    ]
    for i in range(0, DECK_CONSTANT)
]

deck_rows = [[row[0], row[1], f"{row[0]} X {row[1]}", row[3]] for row in deck_rows]

deck_insert = insert_template("deck", deck_columns, generate_rows_decks(deck_rows))

from random import randint


def random_subset(s):
    out = set()
    for el in s:
        # random coin flip
        if randint(0, 1) == 0:
            out.add(el)
    return out


n_of_decks = len(deck_rows)
n_of_cards = len(cards_rows)

deck_card_quantity_rows = []

for i in range(1, n_of_decks + 1):
    n_of_cards_in_deck = 0
    used_up_cards = [0]
    while n_of_cards_in_deck < 30:
        deck_id = i
        eligible_cards = [
            card for card in cards_rows if card[1] in [deck_rows[i - 1][1], "NEUTRAL"]
        ]
        card_quantity = random.randint(1, 2)
        card_quantity_key = (
            cards_rows.index(eligible_cards[random.randint(0, len(eligible_cards) - 1)])
            + 1
        )
        if card_quantity_key in used_up_cards:
            continue
        used_up_cards.append(card_quantity_key)
        n_of_cards_in_deck += card_quantity
        deck_card_quantity_rows.append([i, card_quantity, card_quantity_key])
        if i in deck_cards:
            deck_cards[i].add(card_quantity_key)
        else:
            deck_cards[i] = {card_quantity_key}


def generate_rows_deck_card_quantity(deck_card_quantity_rows):
    return [f"({row[0]},{row[1]},{row[2]})" for row in deck_card_quantity_rows]


deck_card_quantity_insert = insert_template(
    "deck_card_quantity",
    ["deck_id", "card_quantity", "card_quantity_key"],
    generate_rows_deck_card_quantity(deck_card_quantity_rows),
)


def generate_rows_match():
    ret_val = []
    played_cards_first = []
    played_cards_second = []
    for i in range(MATCH_CONSTANT):
        first_player_class = classes[random.randint(0, len(classes) - 1)]
        second_player_class = classes[random.randint(0, len(classes) - 1)]
        eligible_decks_first = [
            deck for deck in deck_rows if deck[1] == first_player_class
        ]
        eligible_decks_second = [
            deck for deck in deck_rows if deck[1] == second_player_class
        ]
        deck_first = (
            deck_rows.index(
                eligible_decks_first[random.randint(0, len(eligible_decks_first) - 1)]
            )
            + 1
        )
        played_cards_first.append(list(random_subset(deck_cards[deck_first])))
        deck_second = (
            deck_rows.index(
                eligible_decks_second[random.randint(0, len(eligible_decks_second) - 1)]
            )
            + 1
        )
        played_cards_second.append(list(random_subset(deck_cards[deck_second])))
        first_player_won = random.randint(1, 212190) % 2 == 0
        ret_val.append(
            f"('{(datetime.datetime.now() - datetime.timedelta(random.randint(1, 100))).isoformat()}', '{first_player_class}', '{'true' if first_player_won else 'false'}', '{second_player_class}', '{'true' if not first_player_won else 'false'}', {deck_first}, {deck_second})"
        )
    return ret_val, played_cards_first, played_cards_second


match_rows, played_cards_first, played_cards_second = generate_rows_match()

# first_player = hero, second_player = hero
match_insert = insert_template(
    "match",
    [
        "date_played",
        "first_player",
        "first_player_won",
        "second_player",
        "second_player_won",
        "deck_first_player_id",
        "deck_second_player_id",
    ],
    match_rows,
)


def generate_cards_played_rows(played_cards):
    ret_val = []
    for i in range(len(played_cards)):
        for j in range(len(played_cards[i])):
            ret_val.append(f"({i+1}, {played_cards[i][j]})")
    return ret_val


match_cards_played_first_player_insert = insert_template(
    "match_cards_played_first_player",
    ["match_id", "cards_played_first_player_id"],
    generate_cards_played_rows(played_cards_first),
)

match_cards_played_second_player_insert = insert_template(
    "match_cards_played_second_player",
    ["match_id", "cards_played_second_player_id"],
    generate_cards_played_rows(played_cards_second),
)

meta_ranks_rows = []

for i in range(META_CONSTANT):
    meta_ranks_rows.append(
        f"('{meta_ranks[random.randint(1, 12010212) % len(meta_ranks)]}')"
    )

meta_insert = insert_template("meta", ["meta_rank"], meta_ranks_rows)


meta_centerpiece_card_winrate_rows = []
meta_counterpick_decks_rows = []
meta_deck_winrate_rows = []
meta_hero_winrate_rows = []
meta_match_history_rows = []
meta_meta_decks_rows = []
for i in range(META_CONSTANT):
    for j in range(len(cards_rows)):
        if cards_rows[j][6] == "true":
            meta_centerpiece_card_winrate_rows.append(
                f"({i+1},{random.randint(0, 100)+random.randint(0, 100)/100},{j+1})"
            )
    counterpick_decks = random.sample(
        deck_types, random.randint(0, len(deck_types) - 1)
    )
    for sampled_deck_type in counterpick_decks:
        meta_counterpick_decks_rows.append(f"({i+1}, '{sampled_deck_type}')")

    for deck in deck_types:
        if deck not in counterpick_decks:
            if random.randint(2, 4) % 2 == 0:
                meta_meta_decks_rows.append(f"({i+1}, '{deck}')")

    for j in range(len(deck_rows)):
        meta_deck_winrate_rows.append(
            f"({i+1},{random.randint(0, 100)+random.randint(0, 100)/100},{j+1})"
        )
    for hero in classes:
        meta_hero_winrate_rows.append(
            f"({i+1},{random.randint(0, 100)+random.randint(0, 100)/100},{classes.index(hero)+1})"
        )
    for j in range(len(match_rows)):
        meta_match_history_rows.append(f"({i+1}, {j+1})")


meta_centerpiece_card_winrate_insert = insert_template(
    "meta_centerpiece_card_winrate",
    ["meta_id", "centerpiece_card_winrate", "centerpiece_card_winrate_key"],
    meta_centerpiece_card_winrate_rows,
)
meta_counterpick_decks_insert = insert_template(
    "meta_counterpick_decks",
    ["meta_id", "counterpick_decks"],
    meta_counterpick_decks_rows,
)
meta_deck_winrate_insert = insert_template(
    "meta_deck_winrate",
    ["meta_id", "deck_winrate", "deck_winrate_key"],
    meta_deck_winrate_rows,
)
meta_hero_winrate_insert = insert_template(
    "meta_hero_winrate",
    ["meta_id", "hero_winrate", "hero_winrate_key"],
    meta_hero_winrate_rows,
)
meta_match_history_insert = insert_template(
    "meta_match_history",
    ["meta_id", "match_history_id"],
    meta_match_history_rows,
)
meta_meta_decks_insert = insert_template(
    "meta_meta_decks",
    ["meta_id", "meta_decks"],
    meta_meta_decks_rows,
)

meta_shift_rows = []
meta_shift_card_ranking_change_rows = []
meta_shift_deck_ranking_change_rows = []
meta_shift_hero_ranking_change_rows = []
meta_shift_new_deck_categories_rows = []

popping_meta = list(range(len(meta_ranks_rows)))
for i in range(len(meta_ranks_rows)):
    p = random.randint(0, len(popping_meta) - 1)
    meta_shift_rows.append(f"({i+1}, {popping_meta[p]+1})")
    popping_meta = popping_meta[0:p] + popping_meta[p + 1 :]
    for j in range(len(cards_rows)):
        meta_shift_card_ranking_change_rows.append(
            f"({i+1},{random.randint(0, 100)},{j+1})"
        )
    for j in range(len(deck_rows)):
        meta_shift_deck_ranking_change_rows.append(
            f"({i+1},{random.randint(0, 100)},{j+1})"
        )
    for hero in classes:
        meta_shift_hero_ranking_change_rows.append(
            f"({i+1},{random.randint(0, 100)},{classes.index(hero)+1})"
        )
    for deck in deck_types:
        if random.randint(3, 6) % 3 != 0:
            meta_shift_new_deck_categories_rows.append(f"({i+1}, '{deck}')")

meta_shift_insert = insert_template(
    "meta_shift",
    ["new_meta_id", "past_meta_id"],
    meta_shift_rows,
)
meta_shift_card_ranking_change_insert = insert_template(
    "meta_shift_card_ranking_change",
    ["meta_shift_id", "card_ranking_change", "card_ranking_change_key"],
    meta_shift_card_ranking_change_rows,
)
meta_shift_deck_ranking_change_insert = insert_template(
    "meta_shift_deck_ranking_change",
    ["meta_shift_id", "deck_ranking_change", "deck_ranking_change_key"],
    meta_shift_deck_ranking_change_rows,
)
meta_shift_hero_ranking_change_insert = insert_template(
    "meta_shift_hero_ranking_change",
    ["meta_shift_id", "hero_ranking_change", "hero_ranking_change_key"],
    meta_shift_hero_ranking_change_rows,
)
meta_shift_new_deck_categories_insert = insert_template(
    "meta_shift_new_deck_categories",
    ["meta_shift_id", "new_deck_categories"],
    meta_shift_new_deck_categories_rows,
)

user_rows = []
user_cards_owned_rows = []
user_decks_not_preferred_rows = []
user_personal_match_history_rows = []


for i in range(USER_CONSTANT):
    user_rows.append(f"({random.randint(0, 20000)})")
    for j in range(len(cards_rows)):
        if random.randint(0, 1500) < 400:
            user_cards_owned_rows.append(f"({i+1}, {j+1})")
    for j in range(len(deck_rows)):
        if random.randint(0, 1337) < 69:
            user_decks_not_preferred_rows.append(f"({i+1},{j+1})")


for match in range(MATCH_CONSTANT):
    first_user = random.randint(1, USER_CONSTANT)
    second_user = first_user
    while second_user == first_user:
        second_user = random.randint(1, USER_CONSTANT)
    user_personal_match_history_rows.append(f"({first_user}, {match+1})")
    user_personal_match_history_rows.append(f"({second_user}, {match+1})")


user_insert = insert_template(
    "user",
    ["dust"],
    user_rows,
)
user_cards_owned_insert = insert_template(
    "user_cards_owned",
    ["user_id", "cards_owned_id"],
    user_cards_owned_rows,
)
user_decks_not_preferred_insert = insert_template(
    "user_decks_not_preferred",
    ["user_id", "decks_not_preferred_id"],
    user_decks_not_preferred_rows,
)

user_personal_match_history_insert = insert_template(
    "user_personal_match_history",
    ["user_id", "personal_match_history_id"],
    user_personal_match_history_rows,
)


with open("data.sql", "w") as sql_script:
    sql_script.write(card_insert)
    sql_script.write(";")
    sql_script.write(deck_insert)
    sql_script.write(";")
    sql_script.write(deck_card_quantity_insert)
    sql_script.write(";")
    sql_script.write(match_insert)
    sql_script.write(";")
    sql_script.write(match_cards_played_first_player_insert)
    sql_script.write(";")
    sql_script.write(match_cards_played_second_player_insert)
    sql_script.write(";")
    sql_script.write(meta_insert)
    sql_script.write(";")
    sql_script.write(meta_centerpiece_card_winrate_insert)
    sql_script.write(";")
    sql_script.write(meta_counterpick_decks_insert)
    sql_script.write(";")
    sql_script.write(meta_deck_winrate_insert)
    sql_script.write(";")
    sql_script.write(meta_hero_winrate_insert)
    sql_script.write(";")
    sql_script.write(meta_match_history_insert)
    sql_script.write(";")
    sql_script.write(meta_meta_decks_insert)
    sql_script.write(";")
    sql_script.write(meta_shift_insert)
    sql_script.write(";")
    sql_script.write(meta_shift_card_ranking_change_insert)
    sql_script.write(";")
    sql_script.write(meta_shift_deck_ranking_change_insert)
    sql_script.write(";")
    sql_script.write(meta_shift_hero_ranking_change_insert)
    sql_script.write(";")
    sql_script.write(meta_shift_new_deck_categories_insert)
    sql_script.write(";")
    sql_script.write(user_insert)
    sql_script.write(";")
    sql_script.write(user_cards_owned_insert)
    sql_script.write(";")
    sql_script.write(user_decks_not_preferred_insert)
    sql_script.write(";")
    sql_script.write(user_personal_match_history_insert)
    sql_script.write(";")
