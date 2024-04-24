<template>
  <div style="margin-left: 12px;">
    <el-button plain @click="dialogFormVisible = true">
      加入班级
    </el-button>
  </div>
  <el-dialog v-model="dialogFormVisible" title="加入班级" width="500" draggable destroy-on-close @close="handleVisibel">
    <el-form ref="formRef" :model="form" :rules="create_rules">
      <el-form-item label="班级令牌" :label-width="formLabelWidth" prop="classToken">
        <el-input v-model="form.classToken" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleVisibel">取消</el-button>
        <el-button type="primary" @click="submitClass(formRef)" :disabled="buttonVisible">
          确定
        </el-button>
      </div>
    </template>
    <el-alert type="error" show-icon v-if="alertVisible" :closable="false">{{errorMessages}}</el-alert>

  </el-dialog>

  <el-table ref="classListTable" row-key="class_id" :data="tableData" stripe>
    <el-table-column prop="class_id" label="班级ID" width="200" />
    <el-table-column prop="name" label="班级名称" width="280" sortable />
    <el-table-column prop="class_num" label="班级人数" sortable width="280" />
    <el-table-column prop="class_desc" label="班级描述"/>
    <el-table-column prop="class_desc" label="老师名字" width="180">
      <template #default="{ row }">
        <el-tag type="success">{{row.teacher_name}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="join_time" label="加入时间" sortable width="280" column-key="join_time"
      :filters="timeFilterOptions()" :filter-method="filterHandler" />
  </el-table>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted } from 'vue'
import { getCookie } from '../utils/tool'
import { useRouter } from 'vue-router';
import { joinClass,  myClass } from '../../../requests/api';
import type { FormInstance, FormRules, TableColumnCtx, TableInstance } from 'element-plus'
const dialogFormVisible = ref(false)
const buttonVisible = ref(false)
const alertVisible = ref(false)
const errorMessages = ref()
const router = useRouter();
const isLoggedIn = getCookie('isLoggedIn') === 'true';
const role = getCookie('role');
const formLabelWidth = '140px'
const tableData = ref<Class>([])

const handleVisibel = (
) => {
  dialogFormVisible.value=false
  alertVisible.value=false
}


interface Class {

grade:number;
  name:string;
   teacher_name:string;
   class_id:number;
   creater_id:string;
   class_desc:string;
   class_num:string;
   join_time:string;
}

onMounted(async () => {
  loadClassList()
    });

const validate = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error('不能为空'));
  }
  else {
    callback();
  }
}

const create_rules = reactive<FormRules<typeof form>>({
  classToken: [{ validator: validate, trigger: 'blur' }],
})

const form = reactive({
  classToken: '',
  studentId:getCookie('id')
})
const formRef = ref<FormInstance>()

const submitClass = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate(async (valid) => {
    if (valid) {
      console.log('加入班级')
      buttonVisible.value=true
      const result =await joinClass(form)
      console.log('结果')
      console.log(result)

      if (result.data.code == 200) {
        console.log(result.data)
        loadClassList()
        buttonVisible.value=false
        dialogFormVisible.value=false
        alertVisible.value=false
      }

      else { //错误提示
        buttonVisible.value=false
        dialogFormVisible.value=true
        errorMessages.value=result.data.message
        alertVisible.value=true
      }

    } else {
      console.log('error submit!')
    }
  })
}

const loadClassList = async () => {
  console.log('加载班级列表');
  try {
   await myClass({
    studentId: getCookie('id')
    }).then(data => {
    tableData.value=data.data.data
    console.log('获取的数据：', tableData.value);
    // 在这里处理返回的数据
    return tableData.value
  })
    
  } catch (error) {
    console.error('加载班级列表出错：', error);
    throw error; // 抛出错误，交给调用方处理
  }
};




if (!isLoggedIn) {
  router.push('/');
} else if (role !== '2') {

  router.push('/');
}



import { List } from '@element-plus/icons-vue';



const classListTable = ref<TableInstance>()

const resetDateFilter = () => {
  classListTable.value!.clearFilter(['date'])
}

const clearFilter = () => {
  classListTable.value!.clearFilter()
}


const filterHandler = (
  value: string,
  row: Class,
  column: TableColumnCtx<Class>
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

</script>