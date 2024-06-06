<template>
    <div class="watchlist-container">
      <h3 class="watchlist-title">My Watchlist:</h3>
      <ul class="watchlist">
        <li v-for="watchlist in watchlists" :key="watchlist.watchlist.watchlistId" class="watchlist-item">
          <div class="media">
            <img :src="watchlist.anime.imgUrl" alt="Anime image" class="anime-img">
          </div>
          <div class="watchlist-content">
            <h4>{{ watchlist.anime.title }}</h4>
            <p><strong>Duration:</strong> {{ watchlist.anime.duration }}</p>
            <p><strong>Episodes:</strong> {{ watchlist.anime.episodes }}</p>
            <p><strong>Studio:</strong> <a :href="watchlist.anime.studioUrl" target="_blank">{{ watchlist.anime.studioName }}</a></p>
            <p><strong>Genres:</strong> {{ watchlist.anime.genres }}</p>
            <p><strong>Background:</strong> {{ watchlist.anime.background }}</p>
            <button class="delete-btn" @click="deleteFromWatchlist(watchlist.watchlist.watchlistId)">Remove</button>
          </div>
        </li>
      </ul>
    </div>
</template>


<script>
import ProfileService from '../services/ProfileService';

export default {
  data() {
    return {
      watchlists: []
    };
  },
  created() {
    this.fetchData();
  },
  mounted() {
    // Set the background when the component mounts
    document.body.style.backgroundImage = "url('/cream-background.jpg')";
  },
  beforeUnmount() {
    // Clear the background when the component is about to be destroyed
    document.body.style.backgroundImage = '';
  },
  methods: {
    async fetchData() {
      try {
        const watchlistsResponse = await ProfileService.getUserWatchlist();
        this.watchlists = watchlistsResponse.data
      } catch (error) {
        console.error('There was an error fetching the watchlist data:', error);
      }
    },
    async deleteFromWatchlist(watchlistId) {
      try {
        await ProfileService.removeFromWatchlist(watchlistId);
        this.watchlists = this.watchlists.filter(watchlist => watchlist.watchlist.watchlistId !== watchlistId);
        alert('Item removed successfully!');
      } catch (error) {
        console.error('Failed to remove from watchlist:', error);
        alert('Failed to remove item from watchlist.');
      }
    }
  }
}
</script>

<style scoped>
.watchlist-container {
  max-width: 800px;
  margin: auto;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  padding: 20px;
}

.watchlist-title {
  text-align: center;
  font-size: 24px;
  color: #333;
  margin-bottom: 20px;
}

.watchlist {
  list-style: none;
  padding: 0;
}

.watchlist-item {
  display: flex;
  margin-bottom: 20px;
  border-bottom: 1px solid #eee;
  padding-bottom: 20px;
}

.media {
  margin-right: 20px;
}

.anime-img {
  width: 120px;
  height: auto;
  border-radius: 5px;
}

.watchlist-content {
  flex: 1;
}

.watchlist-content h4 {
  color: #0056b3; /* Clickable feel for titles */
  margin-bottom: 5px;
}

.watchlist-content p {
  color: #666;
  margin: 4px 0;
  font-size: 14px;
}

.watchlist-content a {
  color: #007BFF;
  text-decoration: none;
}

.watchlist-content a:hover {
  text-decoration: underline;
}

.synopsis {
  color: #444;
  margin-top: 10px;
  font-style: italic;
}

.delete-btn {
  padding: 6px 12px;
  background-color: #dc3545; 
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.delete-btn:hover {
  background-color: #c82333; 
}
</style>

