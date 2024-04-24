<template>
    <div class="profile-view">
    <!-- Loading Screen -->
    <div v-if="isLoading" class="loading-overlay">
     <LoadingPage />
    </div>
      <div v-if="!isLoading">
        <h1>Profile</h1>
        <h3>Reviews for Selected Anime:</h3>
        <ul>
          <li v-for="review in reviews" :key="review.reviewId">
            <img :src="review.imgUrl" alt="Anime image" class="anime-img">
            <div><strong>Rating:</strong> {{ review.rating }}</div>
            <div><strong>Text:</strong> {{ review.reviewText }}</div>
            <div v-if="review.animeDetails">
              <strong>Anime:</strong> {{ review.animeTitle }}
              <p>Type: {{ review.animeDetails.type }}</p>
              <p>Episodes: {{ review.animeDetails.episodes }}</p>
              <p>Score: {{ review.animeDetails.score }}</p>
            </div>
            <div v-else>
              <strong>Anime:</strong> {{ review.animeTitle }} (Details loading...)
            </div>
          </li>
        </ul>
        <h3>Watchlist:</h3>
        <ul>
          <li v-for="watchlist in watchlists" :key="watchlist.watchlistId">
            <img :src="watchlist.imgUrl" alt="Anime image" class="anime-img">
            <!-- <div><strong>Title:</strong> {{ watchlist.animeTitle }}</div> -->
            <div v-if="watchlist.animeDetails">
              <strong>Anime:</strong> {{ watchlist.animeTitle }}
              <p>Type: {{ watchlist.animeDetails.type }}</p>
              <p>Episodes: {{ watchlist.animeDetails.episodes }}</p>
              <p>Score: {{ watchlist.animeDetails.score }}</p>
            </div>
            <div v-else>
              <strong>Anime:</strong> {{ watchlist.animeTitle }} (Details loading...)
            </div>
          </li>
        </ul>
      </div>
    </div>
  </template>
  

  
<script>
import ProfileService from '../services/ProfileService';
import LoadingPage from '../components/LoadingPage.vue';

export default {
    name: 'ProfileView',
  components: {
    LoadingPage
  },
  data() {
    return {
      reviews: [],
      watchlists: [],
      isLoading: true
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
  async fetchWithRetry(url, retries = 5, backoff = 300) {
    try {
      const response = await fetch(url);
      if (!response.ok) {
        if (response.status === 429 && retries > 0) {
          await new Promise(resolve => setTimeout(resolve, backoff));
          return this.fetchWithRetry(url, retries - 1, backoff * 2);  // Exponential backoff
        }
        throw new Error(`API request failed with status ${response.status}: ${response.statusText}`);
      }
      return response.json();
    } catch (error) {
      console.error('Fetch failed:', error);
      throw error;
    }
  },

  async fetchAnimeDetails(title) {
    try {
      const data = await this.fetchWithRetry(`https://api.jikan.moe/v4/anime?q=${encodeURIComponent(title)}&limit=1`);
      if (!data || !data.data || !data.data.length) {
        throw new Error("No data returned from the API");
      }
      return data.data[0];
    } catch (error) {
      console.error('Error fetching anime details for:', title, error);
      return null;
    }
  },

    
    async fetchData() {
        this.isLoading = true;
      try {
        const reviewsResponse = await ProfileService.getUserReviews();
        const watchlistsResponse = await ProfileService.getUserWatchlist();
        
        const fetchDetailsPromises = reviewsResponse.data.map(async review => {
          const details = await this.fetchAnimeDetails(review.animeTitle);
          return { ...review, animeDetails: details };
        });
        this.reviews = await Promise.all(fetchDetailsPromises);

        const fetchWatchlistPromises =  watchlistsResponse.data.map(async watchlist => {
            const details = await this.fetchAnimeDetails(watchlist.animeTitle);
            return {...watchlist, animeDetails: details};
        })
        
        this.watchlists = await Promise.all(fetchWatchlistPromises);
        this.isLoading = false;

      } catch (error) {
        console.error('There was an error fetching the profile data:', error);
        this.isLoading = false;
      }
    }
  }
}
</script>

  
  <style scoped>
  .profile-view h1 {
    color: #333;
  }
  .anime-img {
  width: 200px;
  height: fit;
  object-fit: cover;
  border-radius: 5px;
  margin-right: 10px;
}

li {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.anime-info, .anime-synopsis, .anime-title {
  margin: 5px 0;
}
  </style>