<template>

    <el-menu
        v-if="role === '2'"
        :router="true"
        :default-active="currentPage"
        class="menu"
        @open="handleOpen"
        background-color=#d9e0e9
      >
      
      <el-menu-item index="/home/student/test" >
        <el-icon><Clock /></el-icon>
        <template #title>考试信息</template>
      </el-menu-item>

      <el-menu-item index="/home/student/class" >
        <el-icon ><IconMenu/></el-icon>
        <template #title>我的班级</template>
      </el-menu-item>

      <el-menu-item index="/home/student/score" >
        <el-icon><document /></el-icon>
        <template #title>我的成绩</template>
      </el-menu-item>

      <el-menu-item index="/home/student/message">
        <el-icon><Message /></el-icon>
        <template #title>系统信息</template>
      </el-menu-item>

      <el-menu-item index="/home/student/user">
        <el-icon><User /></el-icon>
        <template #title>个人信息</template>
      </el-menu-item>

    </el-menu>

    <el-menu
    v-if="role ==='1' "
    :router="true"
    :default-active="currentPage"
    class="menu"
    @open="handleOpen"
    background-color=#d9e0e9
  >
      <el-menu-item index="/home/teacher">
        <el-icon><document /></el-icon>
        <template #title>主页</template>
      </el-menu-item>

      <el-menu-item index="/home/teacher/class">
        <el-icon><document /></el-icon>
        <template #title>班级管理</template>
      </el-menu-item>

      
      <el-menu-item index="/home/teacher/exam">
        <el-icon><Message /></el-icon>
        <template #title>考试管理</template>
      </el-menu-item>

  
      <el-sub-menu index="paper">
        <template #title>
          <el-icon><document /></el-icon>试卷管理</template>
        <el-menu-item index="/home/teacher/paperlist">试卷列表</el-menu-item>
        <el-menu-item index="/home/teacher/papercreate">创建试卷</el-menu-item>
      </el-sub-menu>


      <el-sub-menu index='problem'>
        <template #title>
          <el-icon><document /></el-icon>题库管理</template>
        <el-menu-item index="/home/teacher/problemlist">题目列表</el-menu-item>
        <el-menu-item index="/home/teacher/problemcreate">创建题目</el-menu-item>
      </el-sub-menu>

    </el-menu>

   <el-menu
    v-if="role === '0'"
    :router="true"
    class="menu"
    :default-active="currentPage"
    @open="handleOpen"
    background-color=#d9e0e9
  >
      <el-menu-item index="/home/score" >
        <el-icon><document /></el-icon>
        <template #title>试卷管理</template>
      </el-menu-item>

      <el-menu-item index="/home/message">
        <el-icon><Message /></el-icon>
        <template #title>安排考试</template>
      </el-menu-item>

      <el-menu-item index="/home/user">
        <el-icon><User /></el-icon>
        <template #title>题库管理</template>
      </el-menu-item>

    </el-menu>
    


      

  </template>
  
  <script lang="ts" setup>
import {
  Document,
  Menu as IconMenu,
  User,
  Clock,
  Message,
} from '@element-plus/icons-vue'
import { useStore } from 'vuex';
import { getCookie } from '../utils/tool'
import { useRoute } from 'vue-router';
const route = useRoute();
const store = useStore();
const role = getCookie('role');
const isLoggedIn =getCookie('isLoggedIn');
var currentPage

const handleOpen = (key: string, keyPath: string[]) => {
  currentPage=route.path
  console.log(currentPage)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

</script>

  
  <style>
.menu{
  height:77vh;
}
  </style>
  