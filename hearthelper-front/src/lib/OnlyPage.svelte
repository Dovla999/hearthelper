<script>
	import { onMount } from 'svelte';
	import { slimscroll } from 'svelte-slimscroll';
	const base = 'http://localhost:8080/api/cards';

	function api(method, resource, data) {
		return fetch(`${base}/${resource}`, {
			method,
			headers: {
				'content-type': 'application/json'
			},
			body: data && JSON.stringify(data)
		});
	}

	let meta_ranks = [];
	let deck_categories = [];
	let heroes = [];
	let centerpiece_cards = [];
	let recommended_deck = {
		name: '',
		cards: []
	};

	let checked_cards = {};
	let checked_heroes = {};
	let checked_deck_types = {};

	let manual_input = true;

	let recommendBody = {
		hero: '',
		deckCategory: '',
		centerpieceCard: ''
	};

	let post_data = {
		heroes: [],
		deck_categories: [],
		centerpiece_cards: [],
		meta_rank: ''
	};

	onMount(async () => {
		const res_heroes = await api('GET', 'heroes');
		heroes = await res_heroes.json();
		heroes.forEach((element) => {
			checked_heroes[element] = false;
		});
		const res_meta_ranks = await api('GET', 'meta-ranks');
		meta_ranks = await res_meta_ranks.json();
		const rest_deck_categories = await api('GET', 'deck-categories');
		deck_categories = await rest_deck_categories.json();
		deck_categories.forEach((element) => {
			checked_deck_types[element] = false;
		});
		const rec_cp_cards = await api('GET', 'centerpiece-cards');
		centerpiece_cards = await rec_cp_cards.json();
		centerpiece_cards.forEach((element) => {
			checked_cards[element.id] = false;
		});

		post_data.meta_rank = meta_ranks[0];
	});

	async function recommendParams(owned = false) {
		const res = await api('GET', owned ? 'owned-cards' : 'match-history');
		recommendBody = await res.json();
		post_data.heroes = [recommendBody.hero];
		post_data.deck_categories = [recommendBody.deckCategory];
		post_data.centerpiece_cards = [recommendBody.centerpieceCard];
	}

	function addOrRemoveFromArray(arr, el) {
		if (arr.includes(el)) return arr.filter((e) => e != el);
		return [el, ...arr];
	}

	async function recommendDeck() {
		const res = await api('POST', 'decks/recommend-deck', post_data);
		//const res = await api('GET', 'sample-deck');
		const deck = await res.json();
		recommended_deck.name = deck.name;
		recommended_deck.cards = deck.cards;
	}

	function switchInput() {
		manual_input = !manual_input;
		post_data.heroes = [];
		post_data.centerpiece_cards = [];
		post_data.deck_categories = [];
		checked_cards = {};
		checked_heroes = {};
		checked_deck_types = {};
	}
</script>

<div>
	<div style="margin-bottom: 10px;">
	<button class="btn btn-primary" on:click={switchInput} style="width: 300px;"
		>{manual_input ? 'Manual input' : 'Recommended input'}</button
	>
	<button
		class="btn btn-secondary"
		style:visibility={!manual_input ? 'visible' : 'hidden'}
		on:click={() => recommendParams(false)}
	>
		Recommend input from match history
	</button>

	<button
		class="btn btn-secondary"
		style:visibility={!manual_input ? 'visible' : 'hidden'}
		on:click={() => recommendParams(true)}
	>
		Recommend input from owned cards
	</button>
	</div>

	<div class="recommended-inputs" style:visibility={!manual_input ? 'visible' : 'hidden'} >
		<div>
			<label class="option-font-style">Recommended options:</label>
		</div>
		<div>
		<input bind:value={recommendBody.hero} disabled placeholder="Hero" class="option-font-style"/>
		<input bind:value={recommendBody.deckCategory} disabled placeholder="Deck category" class="option-font-style"/>
		<input
			bind:value={recommendBody.centerpieceCard.name}
			disabled
			placeholder="Centerpiece card"
			class="option-font-style"
		/>
		</div>
	</div>

	<div class="container" style:visibility={manual_input ? 'visible' : 'hidden'}>
		<div class="heroes">
			<div>
				<label class="option-font-style" style="font-size: 20px; color:goldenrod;">Pick the heroes:</label>
			</div>
			<div>
			{#each heroes as hero}
				<input
					bind:checked={checked_heroes[hero]}
					on:change={() => {
						post_data.heroes = addOrRemoveFromArray(post_data.heroes, hero);
					}}
					type="checkbox"
					name={hero}
					value={hero}
				/>
				<label for={hero} class="option-font-style"> {hero}</label><br />
			{/each}
			</div>
		</div>
		<div class="types">
			<div>
				<label class="option-font-style" style="font-size: 20px; color:goldenrod;">Pick the categories:</label>
			</div>
			<div>
			{#each deck_categories as deck_category}
				<input
					bind:checked={checked_deck_types[deck_category]}
					on:change={() => {
						post_data.deck_categories = addOrRemoveFromArray(
							post_data.deck_categories,
							deck_category
						);
					}}
					type="checkbox"
					name={deck_category}
					value={deck_category}
				/>
				<label for={deck_category} class="option-font-style"> {deck_category}</label><br />
			{/each}
			</div>
		</div>
		<div class="cp-cards" use:slimscroll >
			<div>
				<label class="option-font-style" style="font-size: 20px; color:goldenrod;">Pick the centerpieces:</label>
			</div>
			<div>
			{#each centerpiece_cards as centerpiece_card}
				<input
					bind:checked={checked_cards[centerpiece_card.id]}
					on:change={() => {
						post_data.centerpiece_cards = addOrRemoveFromArray(
							post_data.centerpiece_cards,
							centerpiece_card.id
						);
					}}
					type="checkbox"
					name={centerpiece_card.name}
					value={centerpiece_card.name}
				/>
				<label for={centerpiece_card.name} class="option-font-style"> {centerpiece_card.name}</label><br />
			{/each}
			</div>
		</div>
		<br />
	</div>

	<div class="container-down" style="margin-top: 10px;">
		<div class="meta-rank">
			<label class="option-font-style">Choose your ranking:</label>
			<select bind:value={post_data.meta_rank} placeholder="Meta ranks">
				{#each meta_ranks as meta_rank}
					<option value={meta_rank}>{meta_rank.split('_')[2] + (meta_rank.split('_')[1] === "LEGEND" ? " LEGEND ELO" : ' to ' + meta_rank.split('_')[1])}</option>
				{/each}
			</select>
		</div>

		<button class=" recommend-deck btn btn-primary" on:click={recommendDeck} style="width: 200px;">Recommend deck</button>
	</div>

	<div class="container-deck">
		<div class="deck-name">{"Deck found: " + recommended_deck.name}</div>
		<div class="deck-content" style="height:200px;overflow:auto;">
			<table class="table">
				<thead>
					<tr>
						<th class="option-font-style">Card name x amount</th>
					</tr>
				</thead>
				<tbody>
					{#each Object.keys(recommended_deck.cards) as card}
						<div class="card">
							
								<label class="option-font-style" style="color:aqua;"
								>{card + "   x " + recommended_deck.cards[card]}</label>
							
						</div>
					{/each}
				</tbody>
			</table>
		</div>
	</div>
</div>

<style>
	.container {
		display: grid;
		grid-template-columns: 1fr 1fr 1fr;
		grid-template-rows: 1fr;
		gap: 0px 0px;
		grid-auto-flow: row;
		grid-template-areas: 'heroes types cp-cards';
		background-image: url("./options-bg.jpg");
		background-size: cover;
	}

	.heroes {
		grid-area: heroes;
	}

	.types {
		grid-area: types;
	}

	.cp-cards {
		grid-area: cp-cards;
	}

	.container-down {
		display: grid;
		grid-template-columns: 1fr 1fr 1fr;
		grid-template-rows: 1fr;
		gap: 0px 0px;
		grid-auto-flow: row;
		grid-template-areas: 'meta-rank a o recommend-deck ';
	}

	.meta-rank {
		grid-area: meta-rank;
	}

	.recommend-deck {
		grid-area: recommend-deck;
	}

	.recommended-inputs {
		position: absolute;
		top: 100px;
	}

	.container-deck {
		display: grid;
		grid-template-columns: 1fr;
		grid-template-rows: 0.2fr 1.8fr;
		gap: 0px 0px;
		grid-auto-flow: row;
		grid-template-areas:
			'deck-name'
			'deck-content';
		width: 50%;
	}

	.deck-name {
		grid-area: deck-name;
		margin: 0 auto;
		font-weight: bold;
		font-size: 30px;
		color: aliceblue;
	}

	.deck-content {
		grid-area: deck-content;
	}

	.option-font-style{
		font-weight: bold;
		color: aliceblue;
	}

	.card {
  /* Add shadows to create the "card" effect */
	box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
	transition: 0.3s;
	margin-top: 10px;
	margin-bottom: 10px;
	background-image: url("./card-bg.jpg");
	background-size: cover;
	font-style: italic;
	font-size: 20px;
	}

	/* On mouse-over, add a deeper shadow */
	.card:hover {
	box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
	}
	
</style>
