<template>
  <el-table ref="tableRef" row-key="id" :data="filteredTableData" stripe>
    <el-table-column prop="exam_name" label="考试名称" sortable />
    <el-table-column prop="id" label="考试编号" />
    <el-table-column prop="start_time" label="考试时间" sortable column-key="start_time" :filters="timeFilterOptions()"
      :filter-method="filterHandler">
      <template #default="{ row }">
        {{ formatDateTime(row.start_time) }}
      </template>
    </el-table-column>
    <el-table-column prop="exam_type" label="考试方式" sortable :filters="typeFilterOptions" :filter-method="filterHandler">
      <template #default="scope">
        {{ getExamType(scope.row.exam_type) }}
      </template>
    </el-table-column>
    <el-table-column prop="operation" label="操作">
      <template #default="scope">
        <el-popconfirm
          confirm-button-text="确定"
          cancel-button-text="取消"
          :icon="InfoFilled"
          icon-color="#626AEF"
          @confirm="startExam(scope.row)"
          title="确定开始作答吗？"
        >
          <template #reference>
            <el-button
              type="danger"
              size="small"
              :disabled="!canStartExam(scope.row)"
            >
              {{ getExamStatus(scope.row) }}
            </el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
    <template #empty>
       暂无未完成的考试！
    </template>
  </el-table>

  <!-- 倒计时弹出框 -->
  <el-dialog  v-model="countdownVisible" width="30%" style="height: 30%;">
    <div>考试将于 {{ countdown }} 秒后开始...</div>
  </el-dialog>

  {{ tableData }}
  {{ getVariable('examId') }}
  asd
</template>

<script lang="ts" setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import type { TableColumnCtx, TableInstance } from 'element-plus'
import { CaretRight, Search, InfoFilled } from '@element-plus/icons-vue'
import { myExam } from '../../../requests/api';
import { getCookie, getExamType, formatDateTime, setCookie, getVariable } from '../utils/tool'

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
  finished: number
}

const router = useRouter()
const tableData = ref<Exam[]>([])
const countdownVisible = ref(false)
const countdown = ref(3)
const currentTime = ref(new Date())

onMounted(async () => {
  await loadMyExam()
  setInterval(() => {
    currentTime.value = new Date()
  }, 1000)
});

const loadMyExam = async () => {
  console.log('加载考试列表');
  try {
    await myExam({
      studentId: getCookie('id')
    }).then(data => {
      console.log('获取的数据：', data);
      tableData.value = data.data.data.filter(exam => exam.finished !== 1)
      console.log('过滤后的数据：', tableData.value);
      // 在这里处理返回的数据
      return tableData.value
    })
  } catch (error) {
    console.error('加载考试列表出错：', error);
    throw error; // 抛出错误，交给调用方处理
  }
};

const startExam = async (row) => {
  countdown.value = 3
  countdownVisible.value = true

  const interval = setInterval(() => {
    if (countdown.value > 0) {
      countdown.value -= 1
    } else {
      clearInterval(interval)
      countdownVisible.value = false
      router.push(`/exam/`)
    }
  }, 1000)
  setCookie('examId', row.id)
  setCookie('paperId', row.paper_id)
  setCookie('end_time', row.end_time)
}

const canStartExam = (row: Exam): boolean => {
  const now = currentTime.value
  const start = new Date(row.start_time)
  const end = new Date(row.end_time)
  return now >= start && now <= end
}

const getExamStatus = (row: Exam): string => {
  const now = currentTime.value
  const start = new Date(row.start_time)
  const end = new Date(row.end_time)
  if (now < start) {
    return '考试未开始'
  } else if (now > end) {
    return '考试已结束'
  } else {
    return '开始考试'
  }
}



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

// Computed property to filter out finished exams
const filteredTableData = computed(() => {
  return tableData.value.filter(exam => exam.finished !== 1)
})

</script>
