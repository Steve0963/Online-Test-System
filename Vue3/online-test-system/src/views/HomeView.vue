<script setup>
import { RouterLink, RouterView } from 'vue-router'
import HomeHeader from '../components/home/HomeHeader.vue';
import HomeMenu from '../components/home/HomeMenu.vue';
import HomeFooter from '../components/home/HomeFooter.vue';

</script>

<template>
  <div class="common-layout">
    <el-container class="home-body">
      <el-header>
        <HomeHeader></HomeHeader>
      </el-header>
      <el-container class="main-container">
        <el-aside width="200px">
          <HomeMenu class="home-menu"></HomeMenu>
        </el-aside>
        <el-main class="main-right">
          <el-scrollbar>
            <RouterView></RouterView>
            <p v-for="item in 100" :key="item" class="scrollbar-demo-item">{{ item }}</p>
          </el-scrollbar>
        </el-main>
      </el-container>
      <el-footer>
        <el-container class="foot-container">
          <HomeFooter></HomeFooter>
        </el-container>
      </el-footer>
    </el-container>
  </div>
</template>

<style scoped>
.home-body {
  height: 100vh;
}

.el-header {
  background-color: rgb(17 72 147);
  height: 8vh;
}

.main-container {
  height: 77vh;
  background: url("https://alifei03.cfp.cn/creative/vcg/nowater800/new/VCG211370226423.jpg");
}

.home-menu {
  height: 77vh;
  background-color: beige;
  
}

.main-right {
  padding-right: 0px;
  padding-bottom: 0px;
  

}

.el-footer {
  background-color: rgb(17 72 147);
  height: 15vh;
  padding-left: 50px;
  padding-right: 50px;
}
.foot-container{
  padding-top: 13px;
}
</style>