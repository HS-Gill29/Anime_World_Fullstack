<template>
    <div class="loading-page">
      <div class="loading-content">
        <img src="@/assets/loading.gif" alt="Loading..." />
        <h1>Loading, please wait...</h1>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'LoadingPage'
  }
  </script>
  
  <style scoped>
  .loading-page {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    width: 100vw;
    background-color: #f4f4f4;
  }
  
  .loading-content {
    text-align: center;
  }
  
  .loading-content img {
    width: 100px;
    height: auto;
    margin-bottom: 20px;
  }
  
  .loading-content h1 {
    color: #555;
    font-family: 'Arial', sans-serif;
  }
  </style>
  