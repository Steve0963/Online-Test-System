<template>
  <!-- <div style="margin-left: 12px;"><el-input
    v-model="testName"
    style="width: 240px"
    size="medium"
    placeholder="考试名称"
    clearable
    :prefix-icon="Search"
    @keyup.enter.native="onSearch"
  /></div> -->

  <el-table ref="tableRef" row-key="id" :data="tableData" stripe>

    <el-table-column prop="exam_name" label="考试名称" sortable />
    <el-table-column prop="id" label="考试编号" />
    <el-table-column prop="start_time" label="考试时间" sortable column-key="start_time" :filters="timeFilterOptions()"
      :filter-method="filterHandler" />
    <el-table-column prop="exam_type" label="考试方式" sortable :filters="typeFilterOptions" :filter-method="filterHandler" >
      <template #default="scope">
       {{ getExamType(scope.row.exam_type) }}
      </template>
    </el-table-column>
    <el-table-column prop="operaiton" label="操作">
      <el-button type="primary" :icon="CaretRight" plain>开始考试</el-button>
    </el-table-column>

  </el-table>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import type { TableColumnCtx, TableInstance } from 'element-plus'
import { CaretRight, Search } from '@element-plus/icons-vue'
import { myExam } from '../../../requests/api';
import { getCookie ,getExamType} from '../utils/tool'
interface Exam {
  class_id: string
  id: string
  creater_id: string
  exam_name: string
  exam_type: string
  start_time: string
  create_time: string
  paper_id: string
  end_time: string
  exam_place: string
}
onMounted(async () => {
  loadMyExam()
});


const loadMyExam = async () => {
  console.log('加载考试列表');
  try {
    await myExam({
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


const filterTag = (value: string, row: Exam) => {
  return getTag(row.score) === value
}
const filterHandler = (
  value: string,
  row: Exam,
  column: TableColumnCtx<Exam>
) => {
  const property = column['property']
  return row[property] === value
}


const getTag = (score: number): string => {
  if (score >= 90) return '优'
  else if (score >= 80) return '良'
  else if (score >= 70) return '中'
  else if (score >= 60) return '差'
  else return '不及格'
}

const tagFilterOptions = [
  { text: '优', value: '优' },
  { text: '良', value: '良' },
  { text: '中', value: '中' },
  { text: '差', value: '差' },
  { text: '不及格', value: '不及格' },
]

const typeFilterOptions = [
  { text: '线上', value: '1' },
  { text: '线下', value: '0' },
]

const timeFilterOptions = () => {
  const options = [
    { text: '2016-05-01', value: '2016-05-01' },
    { text: '2016-05-02', value: '2016-05-02' },
    { text: '2016-05-03', value: '2016-05-03' },
    { text: '2016-05-04', value: '2016-05-04' },
  ]
  return options
}

const tableData= ref<Exam>([])

</script>