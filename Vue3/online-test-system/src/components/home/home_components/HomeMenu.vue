<template>

    <el-menu
        :router="true"
        @open="handleOpen"
        @close="handleClose"
        
      >
      
      <el-menu-item index="/home/test">
        <el-icon><icon-menu /></el-icon>
        <template #title>考试信息</template>
      </el-menu-item>
      <el-menu-item index="/home/class">
        <el-icon><document /></el-icon>
        <template #title>我的班级</template>
      </el-menu-item>

      <el-menu-item index="/home/user">
        <el-icon><setting /></el-icon>
        <template #title>个人信息</template>
      </el-menu-item>

      <el-menu-item index="/home/score">
        <el-icon><setting /></el-icon>
        <template #title>我的成绩</template>
      </el-menu-item>



       <!-- <el-sub-menu index="/home/score">
        <template #title>
          <el-icon><location /></el-icon>
          <span>我的成绩</span>
        </template>
        <el-menu-item-group>
          <template #title><span>Group One</span></template>
          <el-menu-item index="1-1">item one</el-menu-item>
          <el-menu-item index="1-2">item two</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="Group Two">
          <el-menu-item index="1-3">item three</el-menu-item>
        </el-menu-item-group>
        <el-sub-menu index="1-4">
          <template #title><span>item four</span></template>
          <el-menu-item index="1-4-1">item one</el-menu-item>
        </el-sub-menu>
      </el-sub-menu>  -->

      </el-menu>


      

  </template>
  
  <script lang="ts" setup>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
</script>

  
  <style>
  </style>
  