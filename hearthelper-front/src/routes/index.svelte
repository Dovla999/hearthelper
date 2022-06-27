<script context="module">
	export const prerender = true;
</script>

<script>
	import OnlyPage from '$lib/OnlyPage.svelte';
</script>

<svelte:head>
	<title>Home</title>
</svelte:head>

<section>
	<OnlyPage />
</section>

<style>
	section {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		flex: 1;
	}
</style>
