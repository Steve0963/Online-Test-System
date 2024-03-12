<template>
  <div style="margin-left: 12px;"><el-button @click="clearFilter">重置筛选</el-button>
    <el-button @click="clearFilter">导出成绩单</el-button></div>
  
    <el-table ref="tableRef" row-key="id" :data="tableData" stripe>
  
      <el-table-column prop="name" label="考试名称" width="180" sortable />
      <el-table-column prop="id" label="考试编号" />
      <el-table-column prop="score" label="考试成绩" sortable />
      <el-table-column prop="type" label="考试方式" sortable />
      <el-table-column prop="tag" label="得分等级" width="180" sortable :filters="tagFilterOptions" :filter-method="filterTag" filter-placement="bottom-end" >
        <template #default="scope">
          <el-tag :type="getTagType(scope.row.score)" disable-transitions>{{ getTag(scope.row.score) }}</el-tag>
        </template>
      </el-table-column>
  
      <el-table-column prop="date" label="考试时间" sortable width="180" column-key="date" :filters="timeFilterOptions()" :filter-method="filterHandler" />
  
    </el-table>
  </template>
  
  <script lang="ts" setup>
  import { ref } from 'vue'
  import type { TableColumnCtx, TableInstance } from 'element-plus'
  
  interface User {
    name: string
    id: string
    score: number
    type: string
    date: string
  }
  
  const tableRef = ref<TableInstance>()
  
  const resetDateFilter = () => {
    tableRef.value!.clearFilter(['date'])
  }
  
  const clearFilter = () => {
    tableRef.value!.clearFilter()
  }
  
  
  const filterTag = (value: string, row: User) => {
    return getTag(row.score)  === value
  }
  const filterHandler = (
    value: string,
    row: User,
    column: TableColumnCtx<User>
  ) => {
    const property = column['property']
    return row[property] === value
  }
  
  
  const getTagType = (score: number): string => {
    if (score>=90) return 'success'
    else if(score>=80) return 'primary'
    else if(score>=70) return 'info'
    else if(score>=60) return 'warning'
    else return 'danger'
  }
  
  const getTag = (score: number): string => {
    if (score>=90) return '优'
    else if(score>=80) return '良'
    else if(score>=70) return '中'
    else if(score>=60) return '差'
    else return '不及格'
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
  
  const tableData: User[] = [
    {
      date: '2016-05-01',
      name: '计算机导论',
      id: '24130',
      score: 48,
      type: '线上'
    },
    {
      date: '2016-05-02',
      name: '软件工程',
      id: '24131',
      score: 79,
      type: '线下'
    },
    {
      date: '2016-05-03',
      name: '大学英语2',
      id: '24132',
      score: 69,
      type: '线上'
    },
    {
      date: '2016-05-04',
      name: '线性代数',
      id: '24133',
      score: 99,
      type: '线下'
    },
    {
      date: '2016-05-05',
      name: '大数据y',
      id: '24134',
      score: 89,
      type: '线上'
    },
    {
      date: '2016-05-05',
      name: '大数据t',
      id: '24135',
      score: 49,
      type: '线上'
    },
    {
      date: '2016-05-05',
      name: '大数据r',
      id: '24136',
      score: 59,
      type: '线上'
    },
    {
      date: '2016-05-05',
      name: '大数据e',
      id: '24137',
      score: 69,
      type: '线上'
    },
    {
      date: '2016-05-05',
      name: '大数据w',
      id: '24138',
      score: 79,
      type: '线上'
    },
    {
      date: '2016-05-05',
      name: '大数据q',
      id: '24139',
      score: 89,
      type: '线上'
    },
  
  
  ]
  </script>