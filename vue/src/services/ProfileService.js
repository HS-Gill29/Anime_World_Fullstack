import axios from "axios";
export default {
    getUserReviews() {
        return axios.get(`/review`);
      },
      getUserWatchlist() {
        return axios.get(`/watchlist`);
      },
      saveReview(review) {
        return axios.post(`/review`,review);
      },
      addToWatchlist(watchlist){
        return axios.post('/watchlist',watchlist);
      },
      removeFromWatchlist(watchlistId) {
        return axios.delete(`/watchlist/${watchlistId}`)
      },
      updateReview(reviewId, review) {
        return axios.put(`/review/${reviewId}`, review);
      },
}