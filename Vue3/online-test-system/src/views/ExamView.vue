<template>
    <el-container style="height: 100vh">
      <el-aside style="padding-left: 15px; padding-top: 20px; margin-right: 15px; width: 360px">
        <div class="left-card">
          <el-card style="height: 180px; text-align: center" shadow="always">

            
            <el-text>
                <el-icon><Clock /></el-icon>
                考试倒计时
              </el-text>

            <div class="countdown" style="margin-top: 10px;">{{ formatTime(countdown) }}</div>

            <div></div>
            <div style="font-weight: bold; font-size: 20px; margin-top: 25px;">姓名</div>

        
                

            

          </el-card>
  
          <el-card style="height: 76vh; margin-top: 25px;" shadow="always">
            <el-scrollbar height="65vh" style="width: 360px;">
              <div v-for="(type, index) in problemTypeList" :key="type">
                <div style="padding-bottom: 10px;">{{ type }}.{{ getProblemTag(type) }}</div>
                <div v-for="row in splitIntoRows(generateButtonGroups(buttonNum)[index].buttons, 6)" :key="row.join('-')" style="margin-bottom: 10px;">
                  <el-button v-for="button in row" :key="button" style="width: 39px; height: 39px;">{{ button }}</el-button>
                </div>
              </div>
            </el-scrollbar>
  
            <div style="text-align: center">
              <el-form ref="submitForm" @submit.native.prevent="handleSubmit">
                <el-button type="danger" plain size="large" @click="submitForm">提交试卷</el-button>
              </el-form>
            </div>
          </el-card>
        </div>
      </el-aside>
      <el-main style="background-color: green">题目详情</el-main>
    </el-container>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted } from 'vue';
  import { getProblemTag } from '../utils/tools';
  
  const problemTypeList = ref(['0', '1', '2', '3']);
  const buttonNum = ref<number[]>([5, 6, 7, 13]);
  
  const generateButtonGroups = (problemNum: number[]) => {
    const groups: { title: string, buttons: number[] }[] = [];
    for (let i = 0; i < problemNum.length; i++) {
      groups.push({
        title: `第${i + 1}组`,
        buttons: Array.from({ length: problemNum[i] }, (_, j) => j + 1)
      });
    }
    return groups;
  };
  
  const splitIntoRows = (arr: number[], rowSize: number) => {
    const rows = [];
    for (let i = 0; i < arr.length; i += rowSize) {
      rows.push(arr.slice(i, i + rowSize));
    }
    return rows;
  };
  

  const initialDuration = 7200; // 设定初始时长（例如2小时=7200秒）
  const countdown = ref(initialDuration);
  
  const formatTime = (time: number) => {
    const hours = String(Math.floor(time / 3600)).padStart(2, '0');
    const minutes = String(Math.floor((time % 3600) / 60)).padStart(2, '0');
    const seconds = String(time % 60).padStart(2, '0');
    return `${hours}:${minutes}:${seconds}`;
  };
  
  const submitForm = () => {
    const form = submitFormRef.value;
    if (form) {
      form.submit();
    }
  };
  
  const handleSubmit = () => {
    alert('试卷提交成功');
  };
  
  const submitFormRef = ref<InstanceType<typeof HTMLFormElement> | null>(null);
  
  onMounted(() => {
    const interval = setInterval(() => {
      if (countdown.value > 0) {
        countdown.value--;
      } else {
        clearInterval(interval);
        submitForm();
      }
    }, 1000);
  });
  </script>
  
  <style scoped>
  .countdown {
    font-size: 24px; /* 调整字体大小 */
    color: pink;    /* 调整字体颜色 */
  }
  </style>
  