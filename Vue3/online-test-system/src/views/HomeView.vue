<script setup>
import { RouterLink, RouterView } from 'vue-router'
import HomeHeader from '../components/home/home_components/HomeHeader.vue';
import HomeMenu from '../components/home/home_components/HomeMenu.vue';
import HomeFooter from '../components/home/home_components/HomeFooter.vue';

</script>

<template>
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
</template>

<style scoped>
.home-body {
  height: 100vh;
  width: 100vw;

}

.el-header {
  background-color: rgb(17 72 147);
  height: 8vh;
}

.main-container {
  height: 77vh;


}



.main-right {
  padding-right: 0px;
  padding-left: 0px;
  padding-bottom: 0px;
 


}

.el-footer {
  background-color: rgb(17 72 147);
  height: 15vh;
  padding-left: 50px;
  padding-right: 50px;

}
.foot-container{
  padding-top: 11px;
  padding-bottom: 11px;

}
</style>