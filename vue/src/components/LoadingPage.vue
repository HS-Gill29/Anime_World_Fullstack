<template>
  <div class="holder">
    <div class="preloader">
      <div v-for="n in 10" :key="n"></div>
      <!-- Vue loop to create divs -->
    </div>
  </div>
</template>

<script>
export default {
  name: "LoadingPage",
  props: {
    visible: Boolean,
  },
};
</script>

<style scoped>
.holder {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: #2d2f48;
  display: flex;
  align-items: center;
  justify-content: center;
}

.preloader {
  width: 100px;
  height: 100px;
  position: relative;
  animation: rotatePreloader 2s linear infinite;
}

@keyframes rotatePreloader {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

.preloader div {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 15px;
  height: 15px;
  background-color: #ffffff;
  border-radius: 50%;
  transform: translate(-50%, -50%) scale(0);
  animation: fade 2s infinite;
  animation-delay: calc(var(--n) * -0.2s);
}

@keyframes fade {
  0%,
  100% {
    opacity: 0;
    transform: translate(-50%, -50%) scale(0);
  }
  50% {
    opacity: 1;
    transform: translate(-50%, -50%) scale(1);
  }
}
</style>
