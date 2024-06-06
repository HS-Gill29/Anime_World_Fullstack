import axios from "axios";
export default {

    animeExists(title){
      // check if anime already exist in database;  
      return axios.get(`/watchlist/exists?title=${encodeURIComponent(title)}`) 
       .then(response => {
        // Check if response status is 200 OK and data is true
        if (response.data === true) {
            return true; // Anime exists
        } else {
            return false; // Anime does not exist
        }
      }
       )},

    getUserReviews() {
        return axios.get(`/review`); // gets the review for user
      },
      getUserWatchlist() {
        return axios.get(`/watchlist`); // gets watchlist of the user 
      },
      saveReview(review) {
        return axios.post(`/review`,review);// save the review for user
      },
      addToWatchlist(anime){
        return axios.post('/watchlist',anime); //add the anime into watchlist 
      },
      removeFromWatchlist(watchlistId) {
        return axios.delete(`/watchlist/${watchlistId}`) //remove the watchlist
      },
      updateReview(reviewId, review) {
        return axios.put(`/review/${reviewId}`, review);// edit user review 
      },
      deleteReview(reviewId){
        return axios.delete(`/review/${reviewId}`)// delete the user review
      }
}