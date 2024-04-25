<template>
  <router-link to="/" class="title">ANIME WORLD</router-link>
  <div id="register" class="text-center">
    <form v-on:submit.prevent="register" class="register-form">
      <p style="color: black;">Create Your Account </p>
      <div v-if="registrationErrors" class="alert danger">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword">Confirm Password</label>
        <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
      </div>
      <button type="submit" class="btn primary">Create Account</button>
      <p class="login-link">
        <router-link v-bind:to="{ name: 'login' }">Already have an account? Log in.</router-link>
      </p>
    </form>
  </div>
  <footer>
    <div class="about-page">
      <h1>About Us</h1>
      <p>Welcome to our website! We are dedicated to providing you with the best experience possible.</p>
      <p>Our mission is to deliver high-quality content and services that meet your needs and exceed your expectations.</p>
      <p>Feel free to explore our site and learn more about what we have to offer.</p>
    </div>
  </footer>
</template>

<script>
import authService from '../services/AuthService';

export default {
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
.title{
  background-color: #e0cdb7;
    width: auto;
    display: flex;
    justify-content: center;
    padding-top: 50px;
    font-size: 30px;
}
.title:hover{
  color: black;
}
strong {
    color: #f0f0f0;
    font-weight: 700;
}
#register {
  display: flex;
  justify-content: center;
  height: 100%;
  background-color: #e0cdb7;
}

.register-form {
  width: 100%; 
  max-width: 400px; 
  padding: 30px; 
  border-radius: 8px; 
  display: flex;
  flex-direction: column;
  align-items: center; 
}

.form-input-group {
  width: 100%; 
  margin-bottom: 20px; 
}

label {
  display: block;
  margin-bottom: 5px;
  font-size: 16px; 
  font-weight: 600;
  color:#0d2733;
}

input {
  width: 100%; 
  height: 40px; 
  padding: 0 10px;
  font-size: 14px; 
  border: 1px solid #ccc; 
  border-radius: 4px; 
  background-color: #fafafa; 
}

input:focus {
  border-color: #0056b3; 
  outline: none;
}

.alert {
  width: 100%;
  padding: 12px;
  border-radius: 6px;
  margin-bottom: 20px;
  text-align: center; 
  background-color: #f8d7da;
  border: 1px solid #f5c6cb;
  color: #721c24;
  font-size: 14px; 
}

.btn {
  padding: 10px 20px;
  width: 100%; 
  font-size: 16px; 
  font-weight: bold; 
  border: none;
  border-radius: 5px;
  cursor: pointer;
  color: #e8e8e8;
  background-color: #007bff; 
  transition: background-color 0.3s; 
}
.btn:hover{
  background-color: #196fcb;
}

.login-link {
  margin-top: 15px; 
  font-size: 14px; 
  color: #090a0b; 
}
.about-page {
  background-color: #e0cdb7;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
  text-align: center;
  cursor:all-scroll;
}
.about-page h1 {
  font-size: 32px;
  margin-bottom: 20px;
}
.about-page p {
  font-size: 18px;
  line-height: 1.6;
  margin-bottom: 10px;
}

</style>

