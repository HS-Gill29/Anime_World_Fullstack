<template>
  <div class="reviews-container">
    <h3 class="reviews-title">Reviews for Selected Anime:</h3>
    <ul class="review-list">
      <li
        v-for="r in reviews"
        :key="r.reviewId"
        class="review-item"
        @click="selectReview(r)"
      >
        <div class="image-container">
          <img :src="r.imgUrl" alt="Anime image" class="anime-img" />
        </div>
        <div class="review-content">
          <h4>{{ r.animeTitle }}</h4>
          <p><strong>Rating:</strong> {{ r.rating }} Stars</p>
          <p><strong>Review:</strong> {{ r.reviewText }}</p>
          <p><strong>Duration:</strong> {{ r.duration }}</p>
          <p><strong>Episodes:</strong> {{ r.episodes }}</p>
          <p>
            <strong>Studio:</strong>
            <a :href="review.studioUrl" target="_blank">{{ r.studioName }}</a>
          </p>
          <p><strong>Genres:</strong> {{ r.genres }}</p>
          <p><strong>Background:</strong> {{ r.background }}</p> 
          <div class="review-edit-container" v-if="r.reviewId">
            <button class="edit-review-button" @click="toggleForm">Edit Review</button>
            <form v-if="showForm" @submit.prevent="saveReviewChanges">
              <div class="form-group">
                <label for="reviewRating">Rating:</label>
                <input
                  type="number"
                  id="reviewRating"
                  v-model.number="r.rating"
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
                  v-model="r.reviewText"
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
      showForm: false,
      reviews: [],
      review: {
        reviewId: null,
        rating: "",
        reviewText: "",
      },
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    selectReview(selectedReview) {
      this.review = { ...selectedReview }; // Spread operator to copy selected review properties to the editable form
    },

    toggleForm() {
      this.showForm = !this.showForm;
    },
    async saveReviewChanges() {
      try {
        await ProfileService.updateReview(this.review.reviewId, this.review);
        this.toggleForm();
        // alert("Review updated successfully!");
      } catch (error) {
        console.error("Failed to update review:", error);
        alert("Failed to update review.");
      }
    },
    async fetchData() {
      try {
        const reviewsResponse = await ProfileService.getUserReviews();
        this.reviews = reviewsResponse.data;
      } catch (error) {
        console.error("There was an error fetching the review data:", error);
      }
    },
    resetReviewForm() {
      // Resetting only the editable fields to empty
      this.review.rating = 1;
      this.review.reviewText = "";
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
  box-shadow: 0px 2px 3px 0px rgba(0,0,0,0.7);
  transition: background-color 300ms;
  cursor: pointer;
}

.edit-review-button:hover {
  background-color: whitesmoke;
}
</style>
