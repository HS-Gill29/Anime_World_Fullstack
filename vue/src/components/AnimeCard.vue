<template>
  <div class="card">
    <div class="card-content">
      <div class="image-container">
        <img
          :src="anime.images.jpg.large_image_url"
          :alt="anime.title + ' Poster'"
        />
      </div>
      <div class="text-container">
        <h3 class="anime-title">
          {{ anime.title }} ({{ anime.title_english }})
        </h3>
        <p class="anime-info">
          Duration: <span>{{ anime.duration }}</span>
        </p>
        <p class="anime-info">
          Episodes: <span>{{ anime.episodes }}</span>
        </p>
        <p class="anime-info">
          Studio:
          <a :href="anime.studios[0].url" target="_blank">{{
            anime.studios[0].name
          }}</a>
        </p>
        <p class="anime-info">
          Genres: <span>{{ anime.genres[0].name }}</span>
        </p>
        <p class="anime-info">
          Trailer Link:
          <a :href="anime.trailer.embed_url" target="_blank">Watch Trailer</a>
        </p>
        <p class="anime-info">
          Website Link: <a :href="anime.url" target="_blank">Website Link</a>
        </p>

        <p class="anime-info">
          Background: <span>{{ anime.background }}</span>
        </p>
        <p class="anime-info toggle-synopsis" @click="toggleSynopsis">
          Synopsis
        </p>
        <!-- Conditional rendering for Synopsis -->
        <p v-if="showSynopsis" class="anime-synopsis">{{ anime.synopsis }}</p>

        <form @submit.prevent="submitReview">
          <input
            type="textarea"
            v-model="reviewText"
            placeholder="Enter your review"
            required
          />
          <input
            type="number"
            v-model.number="reviewRating"
            placeholder="Rating (1-5) Stars"
            max="5"
            required
          />
          <div class="button">
            <button type="submit" class="submit-btn">Submit Review</button>
            <button type="button" class="watchlist-btn" @click="addWatchlist">
              Add to Watchlist
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import ProfileService from "../services/ProfileService";
import { mapState } from "vuex";

export default {
  props: ["anime"],
  computed: {
    ...mapState(["userId"]), // Fetching userId from Vuex store
  },
  data() {
    return {
      reviewText: "",
      reviewRating: 1,
      showSynopsis: false,
    };
  },
  methods: {
    toggleSynopsis() {
      this.showSynopsis = !this.showSynopsis;
    },
    async submitReview() {
      const review = {
        anime: {
          title: this.anime.title,
          imgUrl: this.anime.images.jpg.large_image_url,
          duration: this.anime.duration,
          episodes: this.anime.episodes,
          studioName: this.anime.studios[0]?.name,
          studioUrl: this.anime.studios[0]?.url,
          genres: this.anime.genres.map((genre) => genre.name).join(", "),
          background: this.anime.background,
          synopsis: this.anime.synopsis,
        },
        review: {
          rating: this.reviewRating,
          reviewText: this.reviewText,
        }
      };
      try {
        await ProfileService.saveReview(review);
        alert("Review submitted successfully!");
        this.reviewText = ""; 
        this.reviewRating = "";
      } catch (error) {
        console.error("Failed to submit review:", error);
        alert("Failed to submit review.");
      }
    },
    async addWatchlist() {
      const anime = {
        userId: this.userId,
        title: this.anime.title,
        imgUrl: this.anime.images.jpg.large_image_url,
        duration: this.anime.duration,
        episodes: this.anime.episodes,
        studioName: this.anime.studios[0]?.name,
        studioUrl: this.anime.studios[0]?.url,
        genres: this.anime.genres.map((genre) => genre.name).join(", "), // Joining genre names as a string
        background: this.anime.background,
        synopsis: this.anime.synopsis,
      };
      try {
        await ProfileService.addToWatchlist(anime);
        alert("Added To Watchlist");
      } catch (error) {
        if (error.response && error.response.data && error.response.data.message === "Anime already exists in watchlist") {
            alert("This anime is already in your watchlist.");
        }
        else {
            console.error("Failed to add anime to watchlist:", error);
            alert("Failed to add anime to watchlist.");
        }
      }
    },
  },
};
</script>

<style scoped>
.card {
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  margin: 20px auto;
  width: 90%;
  max-width: 800px;
  cursor: context-menu;
}

.card-content {
  padding: 20px;
  display: flex;
  align-items: flex-start; /* Align items to the start (top) */
  background-color: #1a1224;
}

.image-container img {
  width: 150px;
  height: 200px;
  border-radius: 5px;
  margin-right: 20px; /* Add margin to the right of the image */
}

.text-container {
  flex: 1; /* Take up remaining space */
}

.anime-title {
  color: #eda553;
  font-size: 24px;
  margin-top: 10px;
}

.anime-info {
  font-size: 16px;
  color: #c8c6c6;
  margin: 5px 0;
  text-align: justify;
}

.anime-info a {
  color: #b6b6b6;
}

.anime-info a:hover {
  color: rgb(85, 85, 166);
}

.anime-synopsis {
  margin-top: 10px;
  color: #c7c7c7;
  text-align: justify;
}

.toggle-synopsis {
  display: contents;
  color: #eda553;
  cursor: pointer;
}
.toggle-synopsis:hover {
  color: rgb(85, 85, 166);
  cursor: pointer;
}

form {
  width: 100%;
  display: flex;
  flex-direction: column;
  margin-top: 20px;
}

input[type="textarea"],
input[type="number"] {
  padding: 10px;
  margin: 5px 0;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: calc(100% - 22px);
}

form div button {
  margin: 0 5px;
}

.submit-btn {
  background-color: #0056b3;
  margin-top: 7px;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 5px;
}

.watchlist-btn {
  background-color: #0056b3;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 5px;
}

.submit-btn:hover,
.watchlist-btn:hover {
  background-color: #004094;
}
</style>
