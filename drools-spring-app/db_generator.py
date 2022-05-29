import requests
import random
import re

response = requests.get(
    "https://api.hearthstonejson.com/v1/139719/enUS/cards.collectible.json"
)

cards = response.json()

# print(cards[0])

rarity_cost = {"COMMON": 40, "RARE": 200, "EPIC": 400, "LEGENDARY": 1600}
classes = (
    "WARLOCK,WARRIOR,MAGE,PRIEST,DRUID,SHAMAN,PALADIN,DEMON_HUNTER,ROGUE,HUNTER".split(
        ","
    )
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
        "true" if random.randint(1, 100) == 5 else "false",
        re.sub(
            "[^0-9a-zA-Z ]+",
            "",
            card["name"].replace("'", "").replace("\n", "").strip(),
        ),
    ]
    for card in [card for card in cards if card["type"] == "MINION"]
]


def generate_rows_cards(cards_rows):
    ret_val = []
    for row in cards_rows:
        ret_val.append(
            f"({row[0]}, '{row[1]}',{row[2]}, {row[3]}, {row[4]}, '{row[5]}', '{row[6]}', '{row[7]}')"
        )
    return ret_val


card_insert = insert_template("card", cards_columns, generate_rows_cards(cards_rows))

with open("data.sql", "w") as sql_script:
    sql_script.write(card_insert)
