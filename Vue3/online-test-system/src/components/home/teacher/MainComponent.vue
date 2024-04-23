<script setup lang="ts">
import { getCookie,getCurrentPage } from '../utils/tool'
import { useRouter } from 'vue-router';

const router = useRouter();
const isLoggedIn = getCookie('isLoggedIn') === 'true';
const role = getCookie('role');


if (!isLoggedIn) {
  router.push('/');
} else if (role !== '1') {
  
  router.push('/');
}


</script>


<template>
主页
  </template>
  
  <style>
  </style>
  