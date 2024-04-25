<template>
  <div class="card">
    <div class="card-content">
      <div class="image-container">
        <img :src="anime.images.jpg.large_image_url" :alt="anime.title + ' Poster'" />
      </div>
      <div class="text-container">
        <h3 class="anime-title">{{ anime.title }}</h3>
        <p class="anime-info">Duration: <span>{{ anime.duration }}</span></p>
        <p class="anime-info">Episodes: <span>{{ anime.episodes }}</span></p>
        <p class="anime-info">Studio: <a :href="anime.studios[0].url" target="_blank">{{ anime.studios[0].name }}</a></p>
        <p class="anime-info">Genres: <span>{{ anime.genres[0].name }}</span></p>
        <p class="anime-info"> Video Link: 
      <a :href="anime.trailer.url" target="_blank">{{ anime.trailer.embed_url }}</a></p>
       <p class="anime-info"> Website Link: 
       <a :href="anime.url" target="_blank">{{ anime.url }}</a></p>
        <p class="anime-background">Background: <span>{{ anime.background }}</span></p>
        <p class="anime-synopsis">Synopsis: <span>{{ anime.synopsis }}</span></p>
        <form @submit.prevent="submitReview">
          <input type="textarea" v-model="reviewText" placeholder="Enter your review" required>
          <input type="number" v-model.number="reviewRating" placeholder="Rating (1-5) Stars"  max="5" required>
          <button type="submit" class="submit-btn">Submit Review</button>
        </form>
        <button type="button" class="watchlist-btn" @click="addWatchlist">Add to Watchlist</button>
      </div>
    </div>
  </div>
</template>



<script>
import ProfileService from '../services/ProfileService';
import { mapState } from 'vuex';

export default {
  props: ['anime'],
  computed: {
    ...mapState(['userId']),  // Fetching userId from Vuex store
  },
  data() {
    return {
      reviewText: '',
      reviewRating: 1
    };
  },
  methods: {
    async submitReview() {
      const review = {
        userId: this.userId,  // From Vuex or auth state
    animeTitle: this.anime.title,
    rating: this.reviewRating,
    reviewText: this.reviewText,
    imgUrl: this.anime.images.jpg.large_image_url,
    duration: this.anime.duration,
    episodes: this.anime.episodes,
    studioName: this.anime.studios[0]?.name,
    studioUrl: this.anime.studios[0]?.url,
    genres: this.anime.genres.map(genre => genre.name).join(', '), 
    background: this.anime.background,
    synopsis: this.anime.synopsis
      };
      try {
        await ProfileService.saveReview(review);
        console.log("UserID from Vuex:", this.userId);
       console.log(review.userId)
        alert('Review submitted successfully!');
        this.reviewText = ''; // Reset form
        this.reviewRating = '';
      } catch (error) {
        console.error('Failed to submit review:', error);
        alert('Failed to submit review.');
      }
    },
    async addWatchlist(){
      const watchlist = {
        userId: this.userId,
    animeTitle: this.anime.title,
    imgUrl: this.anime.images.jpg.large_image_url,
    duration: this.anime.duration,
    episodes: this.anime.episodes,
    studioName: this.anime.studios[0]?.name,
    studioUrl: this.anime.studios[0]?.url,
    genres: this.anime.genres.map(genre => genre.name).join(', '),  // Joining genre names as a string
    background: this.anime.background,
    synopsis: this.anime.synopsis
      };
      try{
        await ProfileService.addToWatchlist(watchlist);
        alert('Added To Watchlist')
      } catch(error){
        console.log('Failed to add review', error)
        console.log(this.anime.images.jpg.large_image_url)
        alert('Failed to add watchlist.');
      }
    }
  }
}

</script>


<style scoped>
.card {
  display: flex;
  flex-direction: column;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  margin: 20px auto;
  width: 90%;
  max-width: 800px;
  align-items: center;
}

.card-content {
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #1a1224;;
}

.image-container img {
  max-width: 100%;
  height: auto;
  border-radius: 5px;
}

.anime-title {
  color: #eda553;
  font-size: 24px;
  margin-top: 10px;
}

.anime-info, .anime-background {
  font-size: 16px;
  color: #c8c6c6;
  margin: 5px 0;
  text-align: justify;
}

.anime-info a {
  color: #b6b6b6;
  text-decoration: none;
}

.anime-info a:hover {
  text-decoration: underline;
}

.anime-synopsis {
  margin-top: 10px;
  color: #c7c7c7;
  text-align: justify;
}

form {
  width: 100%;
  display: flex;
  flex-direction: column;
  margin-top: 20px;
}

input[type="textarea"], input[type="number"] {
  padding: 10px;
  margin: 5px 0;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: calc(100% - 22px);
}

.submit-btn, .watchlist-btn {
  background-color: #0056b3;
  color: white;
  border: none;
  padding: 10px 20px;
  margin-top: 10px;
  cursor: pointer;
  border-radius: 5px;
  width: 100%;
}

.submit-btn:hover, .watchlist-btn:hover {
  background-color: #004094;
}
</style>


