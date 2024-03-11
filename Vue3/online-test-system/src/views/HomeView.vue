<script setup>
import { RouterLink, RouterView } from 'vue-router'
import HomeHeader from '../components/home/home_components/HomeHeader.vue';
import HomeMenu from '../components/home/home_components/HomeMenu.vue';
import HomeFooter from '../components/home/home_components/HomeFooter.vue';

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
  


  
}

.home-menu {
  height: 77vh;
  background-color: #11489336;
  
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
  padding-top: 19px;
  padding-bottom: 19px;
}
</style>../components/home/home_components/HomeFooter.vue../components/home/home_components/HomeHeader.vue../components/home/home_components/HomeMenu.vue