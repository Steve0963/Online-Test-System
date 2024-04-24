<template>
  <div style="margin-left: 12px;"><el-button @click="clearFilter">重置筛选</el-button>
    <el-button @click="clearFilter">导出成绩单</el-button></div>
  
    <el-table ref="tableRef" row-key="id" :data="tableData" stripe>
  
      <el-table-column prop="exam_name" label="考试名称" width="180" sortable />
      <el-table-column prop="id" label="考试编号" />
      <el-table-column prop="exam_score" label="考试成绩" sortable />
      <el-table-column prop="exam_type" label="考试方式" sortable />
      <el-table-column prop="tag" label="得分等级" width="180" :filters="tagFilterOptions" :filter-method="filterTag" filter-placement="bottom-end" >
        <template #default="scope">
          <el-tag :type="getTagType(scope.row.exam_score)" disable-transitions>{{ getTag(scope.row.exam_score) }}</el-tag>
        </template>
      </el-table-column>
  
      <el-table-column prop="start_time" label="考试时间" sortable width="180" column-key="start_time" :filters="timeFilterOptions()" :filter-method="filterHandler" />
  
    </el-table>
  </template>
  
  <script lang="ts" setup>
  import { ref,onMounted } from 'vue'
  import type { TableColumnCtx, TableInstance } from 'element-plus'
  import { getCookie,getTagType,getTag } from '../utils/tool'
import {   myScore } from '../../../requests/api';
  interface User {
    exam_score: number
	id: string
	exam_name: string
	start_time: string
	exam_type: string
  }
  onMounted(async () => {
  loadScore()
});

const loadScore = async () => {
  console.log('加载成绩列表');
  try {
    await myScore({
      studentId: getCookie('id')
    }).then(data => {
      console.log('获取的数据：', data);
      tableData.value = data.data.data
      console.log('获取的数据：', tableData.value);
      // 在这里处理返回的数据
      return tableData.value
    })

  } catch (error) {
    console.error('加载考试列表出错：', error);
    throw error; // 抛出错误，交给调用方处理
  }
};
  const tableRef = ref<TableInstance>()
  
  const resetDateFilter = () => {
    tableRef.value!.clearFilter(['date'])
  }
  
  const clearFilter = () => {
    tableRef.value!.clearFilter()
  }
  
  
  const filterTag = (value: string, row: User) => {
    return getTag(row.exam_score)  === value
  }
  const filterHandler = (
    value: string,
    row: User,
    column: TableColumnCtx<User>
  ) => {
    const property = column['property']
    return row[property] === value
  }
  
  
  const tagFilterOptions =[
    { text: '优', value: '优' },
    { text: '良', value: '良' },
    { text: '中', value: '中' },
    { text: '差', value: '差' },
    { text: '不及格', value: '不及格' },
  ]
  
  const timeFilterOptions = () => {
    const options=[
      { text: '2016-05-01', value: '2016-05-01' },
      { text: '2016-05-02', value: '2016-05-02' },
      { text: '2016-05-03', value: '2016-05-03' },
      { text: '2016-05-04', value: '2016-05-04' },
    ]
    return options
  }
  const tableData= ref<User>([])
  </script>