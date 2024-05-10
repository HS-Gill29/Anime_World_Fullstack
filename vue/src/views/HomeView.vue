<template>
  <div class="app">
    <header>
      <a href="/">
        <h1>
          <span class="anime">Anime </span>
          <span class="world">World</span>
        </h1>
      </a>
      <form class="search-box" @submit.prevent="handleSearch">
        <svg
          class="search-icon"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 512 512"
        >
          <path
            d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"
          />
        </svg>
        <input
          type="text"
          class="search-bar"
          placeholder="Search for an anime..."
          v-model="search_query"
        />
      </form>
    </header>
    <main>
      <LoadingPage :visible="isLoading" />
      <div v-if="showAnimeList && !isLoading">
        <div class="cards">
          <AnimeCard
            v-for="anime in animeList"
            :key="anime.mal_id"
            :type="TV"
            :anime="anime"
          />
        </div>
      </div>
      <div v-else-if="!isLoading">
        <HomePage />
      </div>
    </main>
  </div>
</template>

<script>
import { ref } from "vue";
import AnimeCard from "../components/AnimeCard.vue";
import HomePage from "../components/HomePage.vue";
import LoadingPage from "../components/LoadingPage.vue";

export default {
  components: {
    AnimeCard,
    HomePage,
    LoadingPage,
  },
  setup() {
    const search_query = ref("");
    const animeList = ref([]);
    const showAnimeList = ref(false); // Flag to toggle between showing anime list and homepage
    const isLoading = ref(false);

    const handleSearch = async () => {
      isLoading.value = true;
      try {
        const res = await fetch(
          `https://api.jikan.moe/v4/anime?q=${search_query.value}`
        );
        const data = await res.json();
        animeList.value = data.data.filter(
          (anime) => anime.episodes > 0 && anime.type === "TV"
        );

        // Show anime list only if there are search results
        showAnimeList.value = animeList.value.length > 0;
      } catch (error) {
        console.error("Failed to fetch anime:", error);
      } finally {
        isLoading.value = false; // End loading
      }

      search_query.value = "";
    };

    return {
      search_query,
      animeList,
      handleSearch,
      showAnimeList,
      isLoading,
    };
  },
};
</script>

<style>
.app {
  background-image: url("/cream-background.jpg");
  background-size: cover;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: Arial, sans-serif;
}

a {
  text-decoration: none;
}

header {
  padding-top: 50px;
  padding-bottom: 50px;
}

header h1 {
  font-size: 40px;
  font-weight: 200;
  text-align: center;
  text-transform: uppercase;
  color: #06c;
  cursor: pointer;
}
.anime {
  font-weight: lighter;
  font-style: normal;
}

header h1:hover {
  color: #180045;
}

.search-box {
  display: flex;
  padding-left: 20px;
  padding-right: 30px;
  width: fit-content;
  margin: 10px auto;
  background-color: white;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.15);
  border-radius: 8px;
  color: black;
  transition: background-color 0.3s, color 0.3s;
  /* position: relative;
  justify-content: center;
  align-items: center; */
}

/* Hover and focus effects for the entire search box and its children */
.search-box:hover,
.search-box:focus,
.search-box:hover .search-icon,
.search-box:focus .search-icon,
.search-box:hover .search-bar,
.search-box:focus .search-bar {
  background-color: black;
  color: white;
}

.search-icon {
  width: 20px;
  fill: currentColor;
}

.search-bar {
  border: none;
  outline: none;
  padding: 15px 45px 15px 20px;
  color: inherit;
  font-size: 24px;
  transition: background-color 0.3s, color 0.3s;
  /* display: block;
  width: 100%;
  max-width: 600px;
  margin-left: 20px; */
}

main {
  max-width: 1200px;
  margin: 0 auto;
  padding-left: 30px;
  padding-right: 30px;
}
.cards {
  display: flex;
  flex-wrap: wrap;
}
</style>
