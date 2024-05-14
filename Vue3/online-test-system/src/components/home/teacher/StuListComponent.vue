<template>
  <el-table ref="tableRef" row-key="id" :data="tableData" stripe>

    <el-table-column prop="stu_id" label="学号" width="150" sortable />
    <el-table-column prop="stu_name" label="学生姓名" sortable width="350" />
    <el-table-column prop="class_name" label="班级名称" sortable width="350" :filters="classNameList"
      :filter-method="filterTag" filter-placement="bottom-end">
    </el-table-column>
    <el-table-column prop="join_time" label="加入时间" sortable width="580" column-key="date" :filters="timeFilterOptions()"
      :filter-method="filterHandler" />
    <el-table-column prop="operaiton" label="操作">
      <template #default="scope">
        <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled" icon-color="#626AEF"
        @confirm="remove(scope.row)" title="确定要从班级中移除该学生吗?">
          <template #reference>
            <el-button type="danger" size="small" :icon="Delete"> 移除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  {{  classNameList}}
</template>

<script lang="ts" setup>

import { getCookie, getCurrentPage } from '../utils/tool'
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue'
import type { TableColumnCtx, TableInstance } from 'element-plus'
import { InfoFilled } from '@element-plus/icons-vue'
import { removeStudent, studentList } from '../../../requests/api';
import { Delete,Edit,} from '@element-plus/icons-vue'
import { ElNotification ,ElMessage} from 'element-plus'
const router = useRouter();
const tableRef = ref<TableInstance>()
const tableData = ref<Student>([])

const classNameList = ref<{ text: string, value: string }[]>([]);

interface Student {
  stu_id: string
  stu_name: string
  join_time: string
  class_name: string
  class_id:string
}


const loadStudentList = async () => {
  console.log('加载学生列表');
  try {
    await studentList({
      createrId: getCookie('id')
    }).then(data => {
      tableData.value = data.data.data
      console.log('获取的数据：', tableData.value);
      const uniqueClassNames = Array.from(new Set(data.data.data.map(student => student.class_name)));
      classNameList.value=uniqueClassNames.map(className => ({ text: className, value: className }));
      return tableData.value
    })

  } catch (error) {
    console.error('加载班级列表出错：', error);
    throw error; // 抛出错误，交给调用方处理
  }
};




const checkLogin = () => {
  if (!getCookie('isLoggedIn') === 'true' || getCookie('role') !== '1')
    router.push('/');
}

const remove= async (row) => {
  try {
    await removeStudent({
      studentId: row.stu_id,
      classId:row.class_id
    }).then(data => {

      if (data.data.code == 200) {
        ElMessage({
          showClose: true,
          message: data.data.message,
          type: 'success',
        })
        loadStudentList()
      }
      else {
        ElMessage({
          showClose: true,
          message: data.data.message,
          type: 'danger',
        })
      }
    })

  } catch (error) {
    console.error('加载考试列表出错：', error);
    throw error;
  }


}

const resetDateFilter = () => {
  tableRef.value!.clearFilter(['date'])
}

const clearFilter = () => {
  tableRef.value!.clearFilter()
}

const filterTag = (value: string, row: Student) => {
  return row.class_name === value
}


const filterHandler = (
  value: string,
  row: User,
  column: TableColumnCtx<User>
) => {
  const property = column['property']
  return row[property] === value
}



const timeFilterOptions = () => {
  const options = [
    { text: '2016-05-01', value: '2016-05-01' },
    { text: '2016-05-02', value: '2016-05-02' },
    { text: '2016-05-03', value: '2016-05-03' },
    { text: '2016-05-04', value: '2016-05-04' },
  ]
  return options
}

onMounted(async () => {
  checkLogin()
  loadStudentList()
});
</script>