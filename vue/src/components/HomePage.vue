<template>
  <div class="recommended-anime">
    <h1>Recommended Anime</h1>
    <div v-if="recommendedAnime.length > 0" class="anime-list">
      <div v-for="(anime, index) in recommendedAnime" :key="index" class="anime-card">
        <a :href="anime.trailer.embed_url" target="_blank">
        <img :src="anime.images.jpg.image_url" class="anime-image" /></a>
        <p class="anime-title">{{ anime.title }}</p>
      </div>
    </div>
  </div>
  <div class="upcoming-anime">
    <h1>Upcoming Seasons:</h1>
    <div v-for="(season,index) in upcomingSeasons" :key="index" class="anime-card">
      <a :href="season.trailer.embed_url" target="_blank">
    <img :src="season.images.jpg.image_url" :alt="season.title" class="anime-image" />
  </a>  <p class="anime-title">{{ season.title }}</p>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      recommendedAnime: [],
      upcomingSeasons: []
    };
  },
  async created() {
    try {
      const response = await fetch("https://api.jikan.moe/v4/top/anime");
      const data = await response.json();

      this.recommendedAnime = data.data.slice(0, 5); 

      const upcomingResponse = await fetch("https://api.jikan.moe/v4/seasons/upcoming");
      const upcomingData = await upcomingResponse.json();

      this.upcomingSeasons = upcomingData.data.slice(0, 5); 

    } catch (error) {
      console.error("Error fetching recommended anime:", error);
    }
  }
};
</script>

<style scoped>
.recommended-anime {
  padding: 20px;
}
.upcoming-anime{
  display: flex;
}

.anime-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.anime-card {
  width: 200px;
  margin: 10px;
  text-align: center;
}

.anime-image {
  width: 150px;
  height: 200px;
  object-fit: cover;
  border-radius: 5px;
}

.anime-title {
  margin-top: 5px;
  font-size: 16px;
}
</style>




