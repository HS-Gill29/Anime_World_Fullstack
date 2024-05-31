<template>
  <div class="reviews-container">
    <h3 class="reviews-title">Reviews for Selected Anime:</h3>
    <ul class="review-list">
      <li v-for="animeReview in animeReviews" :key="animeReview.review.reviewId" class="review-item">
        <div class="image-container">
          <img :src="animeReview.anime.imgUrl" alt="Anime image" class="anime-img" />
        </div>
        <div class="review-content">
          <h4>{{ animeReview.anime.title }}</h4>
          <p><strong>Rating:</strong> {{ animeReview.review.rating }} Stars</p>
          <p><strong>Review:</strong> {{ animeReview.review.reviewText }}</p>
          <p><strong>Duration:</strong> {{ animeReview.anime.duration }}</p>
          <p><strong>Episodes:</strong> {{ animeReview.anime.episodes }}</p>
          <p>
            <strong>Studio:</strong>
            <a :href="animeReview.anime.studioUrl" target="_blank">{{ animeReview.anime.studioName }}</a>
          </p>
          <p><strong>Genres:</strong> {{ animeReview.anime.genres }}</p>
          <p><strong>Background:</strong> {{ animeReview.anime.background }}</p>
          <button @click="deleteReview(animeReview.review.reviewId)">Delete Review</button>
          <div class="review-edit-container">
            <button class="edit-review-button" @click.stop="startEditing(animeReview.review)">
              Edit Review
            </button>
            <form v-if="animeReview.review.showForm" @submit.prevent="saveReviewChanges(animeReview.review)">
              <div class="form-group">
                <label for="reviewRating">Rating:</label>
                <input
                  type="number"
                  id="reviewRating"
                  v-model.number="tempReview.rating"
                  placeholder="Rating (1-5)"
                  min="1"
                  max="5"
                  required
                />
              </div>
              <div class="form-group">
                <label for="reviewContent">Review:</label>
                <textarea
                  id="reviewContent"
                  v-model="tempReview.reviewText"
                  placeholder="Enter your review"
                  required
                ></textarea>
              </div>
              <button type="submit" class="btn-update">Update Review</button>
            </form>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>


<script>
import ProfileService from "../services/ProfileService";

export default {
  data() {
    return {
      animeReviews: [],
      tempReview: {},
    };
  },
  created() {
    this.fetchData();
  },
  mounted() {
    document.body.style.backgroundImage = "url('/cream-background.jpg')";
  },
  beforeUnmount() {
    document.body.style.backgroundImage = '';
  },
  methods: {
    startEditing(review) {
      this.tempReview = { ...review };
      review.showForm = true;
    },
    toggleForm(review) {
      review.showForm = !review.showForm;
    },
    async saveReviewChanges(originalReview) {
      const index = this.animeReviews.findIndex(
        (ar) => ar.review.reviewId === originalReview.reviewId
      );
      if (index !== -1) {
        this.animeReviews[index].review = { ...this.tempReview };
        originalReview.showForm = false;
        await this.updateReview(this.tempReview);
      }
    },
    async updateReview(review) {
      try {
        await ProfileService.updateReview(review.reviewId, review);
      } catch (error) {
        console.error("Failed to update review:", error);
        alert("Failed to update review.");
      }
    },
    async deleteReview(reviewId) {
      try {
        await ProfileService.deleteReview(reviewId);
        this.animeReviews = this.animeReviews.filter(ar => ar.review.reviewId !== reviewId);
      } catch (error) {
        console.error("Failed to delete review:", error);
        alert("Failed to delete review.");
      }
    },
    async fetchData() {
      try {
        const response = await ProfileService.getUserReviews();
        this.animeReviews = response.data.map((animeReview) => ({
          ...animeReview,
          review: { ...animeReview.review, showForm: false },
        }));
      } catch (error) {
        console.error("There was an error fetching the review data:", error);
      }
    },
  },
};
</script>


<style scoped>
.reviews-container {
  max-width: 800px;
  margin: auto;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.reviews-title {
  font-size: 24px;
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

.review-list {
  list-style-type: none;
  padding: 0;
}

.review-item {
  display: flex;
  align-items: flex-start;
  border-bottom: 1px solid #dee2e6;
  padding-bottom: 15px;
  margin-bottom: 15px;
}

.image-container {
  margin-right: 20px;
  flex-shrink: 0;
}

.anime-img {
  width: 120px;
  height: 180px;
  object-fit: cover;
  border-radius: 8px;
}

.review-content {
  flex-grow: 1;
}

h4 {
  color: #0366d6;
  margin-bottom: 10px;
}

p {
  color: #666;
  margin: 5px 0;
  line-height: 1.4;
}

strong {
  color: #333;
}

a {
  color: #007bff;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
.review-edit-container {
  max-width: 600px;
  margin: auto;
  padding: 20px;
  background-color: #f4f4f4;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  color: #333;
}

form {
  margin-top: 12px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  color: #666;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.btn-update {
  display: block;
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.btn-update:hover {
  background-color: #0056b3;
}

.edit-review-button {
  background-color: white;
  outline: none;
  border: 1px solid transparent;
  padding: 8px;
  border-radius: 5px;
  box-shadow: 0px 2px 3px 0px rgba(0, 0, 0, 0.7);
  transition: background-color 300ms;
  cursor: pointer;
}

.edit-review-button:hover {
  background-color: whitesmoke;
}
</style>
