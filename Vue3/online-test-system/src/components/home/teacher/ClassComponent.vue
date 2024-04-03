<script lang="ts" setup>
import { getCookie } from '../utils/tool'
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
班级管理 {{ getCookie("isLoggedIn") }}
{{ getCookie("role") }}

  </template>
  
  <style>
  </style>
  