
import { RouterLink } from 'vue-router';
<template>
  <div id="capstone-app">
    <nav id="nav">
      <router-link :to="{ name: 'home' }" class="nav-link">Home</router-link>
      <router-link :to="{ name: 'logout' }" class="nav-link" v-if="$store.state.token != ''">Logout</router-link>
      <router-link :to="{ name: 'review'}" class="nav-link">My Reviews</router-link>
      <router-link :to="{ name: 'watchlist'}" class="nav-link">My Watchlist</router-link>
    </nav>
    <router-view />
  </div>
</template>


<style scoped>

nav {
  display: flex;
  justify-content: center;
  background-color: #cda949;
}

.nav-link {
  color: #fff;
  text-decoration: none;
  padding: 10px 15px;
  margin: 0 5px;
  background-color: inherit;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.nav-link:hover, .nav-link:focus {
  background-color: #000;
  color: #ffffff;
}

@media (max-width: 600px) {
  nav#nav {
    padding: 10px;
  }

  .nav-link {
    display: block;
    margin: 5px auto;
    width: 90%;
    text-align: center;
  }
}
</style>
