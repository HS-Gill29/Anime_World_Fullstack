<template>
  <div id="login" class="login-container">
    <router-link to="/" class="title">ANIME WORLD</router-link>
    <form v-on:submit.prevent="login" class="login-form">
      <h1>Sign In</h1>
      <div role="alert" v-if="invalidCredentials" class="alert">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration" class="alert">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button type="submit">Sign in</button>
      <p>
        <router-link v-bind:to="{ name: 'register' }">Need an account? Sign up.</router-link>
      </p>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>
.title{
  display: flex;
    font-weight: 400;
    font-size: 60px;
    justify-content: center;
    margin-top: 15px;
    color: #364a66;
}
  .login-container {
    background-color: #e0cdb7;
    width: 100vw;
    height: 100vh;
    position: absolute;
    transform: translate(-50%,-50%);
    left: 50%;
    top: 50%;
  }
  
  .login-form {
    height: 520px;
    width: 400px;
    background-color: #b5b5b5;
    position: absolute;
    transform: translate(-50%,-50%);
    top: 50%;
    left: 50%;
    border-radius: 10px;
    border: 2px solid rgba(255,255,255,0.1);
    box-shadow: 0 0 10px rgba(8,7,16,0.6);
    padding: 50px 35px;
  }
  
  .login-form * {
    font-family: 'Poppins', sans-serif;
    color: #485fc7;
    font-weight: bold;
    letter-spacing: 0.5px;
    outline: none;
    border: none;
  }
  
  .login-form label {
    display: block;
    margin-top: 30px;
    font-size: 16px;
    font-weight: lighter;
    color: #090707;
  }
  
  .login-form input {
    display: block;
    height: 50px;
    width: 100%;
    background-color: #fffffff1;  
    border-radius: 3px;
    padding: 0 10px;
    margin-top: 8px;
    font-size: 16px; 
    font-weight: 500;
    color: #1a1a1a; 
  }

  .login-form button {
    margin-top: 40px;
    width: 100%;
    background-color: #007bff; 
    color: #1a1a1a;
    padding: 15px 0;
    font-size: 18px;
    font-weight: 600;
    border-radius: 5px;
    cursor: pointer;
  }
  .login-form button:hover{
    background-color: #196fcb;
  }
  
  .form-input-group {
    margin-bottom: 1rem;
  }

  .login-form p {
    margin-top: 2rem; 
  }
</style>








