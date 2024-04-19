<template>
  <div style="margin-left: 12px;">
    <el-button plain @click="dialogFormVisible = true">
      创建班级
    </el-button>
  </div>
  <el-dialog v-model="dialogFormVisible" title="创建班级" width="500" draggable>
    <el-form ref="formRef" :model="form" :rules="create_rules">
      <el-form-item label="班级名称" :label-width="formLabelWidth" prop="className">
        <el-input v-model="form.className" autocomplete="off" />
      </el-form-item>

      <el-form-item label="班级描述" :label-width="formLabelWidth" prop="classDesc">
        <el-input v-model="form.classDesc" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="submitClass(formRef)" :disabled="buttonVisible">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>

  <el-table ref="classListTable" row-key="class_id" :data="tableData" stripe>
    <el-table-column prop="class_id" label="班级ID" width="200" />
    <el-table-column prop="name" label="班级名称" width="380" sortable />
    <el-table-column prop="class_num" label="班级人数" sortable width="380" />
    <el-table-column prop="class_token" label="班级口令">
      <template #default="{ row }">
        <el-tag type="success">{{row.class_token}}</el-tag>
      </template>
    </el-table-column>

    <el-table-column prop="creat_time" label="创建时间" sortable width="280" column-key="creat_time"
      :filters="timeFilterOptions()" :filter-method="filterHandler" />

  </el-table>
</template>

<script lang="ts" setup>
import { reactive, onMounted } from 'vue'
import { getCookie } from '../utils/tool'
import { useRouter } from 'vue-router';
import { creatClass, loadClass } from '../../../requests/api';
const dialogFormVisible = ref(false)
const buttonVisible = ref(false)

const router = useRouter();
const isLoggedIn = getCookie('isLoggedIn') === 'true';
const role = getCookie('role');
const formLabelWidth = '140px'
const tableData = ref<Class>([])

interface Class {
  class_id: string
  name: string
  class_token: string
  creat_time: string
  class_num: number
}

onMounted(async () => {
  loadClassList()
    });

const validate = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error('班级名称不能为空'));
  }

  else if (value.length > 15) {
    return callback(new Error('班级名称不能超过15个字'));
  }
  else {
    callback();
  }
}

const create_rules = reactive<FormRules<typeof form>>({
  className: [{ validator: validate, trigger: 'blur' }],
})

const form = reactive({
  className: '',
  classDesc: '',
  createrId:getCookie('id')
})
const formRef = ref<FormInstance>()

const submitClass = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate(async (valid) => {
    if (valid) {
      console.log('创建班级')
      buttonVisible.value=true
      const result =await creatClass(form)
      console.log(result)

      if (result != null) {
        console.log(result.data.code)
        loadClassList()
        buttonVisible.value=false
        dialogFormVisible.value=false
      }

      if (result == null) { //错误提示
        buttonVisible.value=false
        dialogFormVisible.value=true
      }

    } else {
      console.log('error submit!')
    }
  })
}

const loadClassList = async () => {
  console.log('加载班级列表');
  try {
   await loadClass({
      createrId: getCookie('id')
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
} else if (role !== '1') {

  router.push('/');
}


import { reactive, ref } from 'vue'
import type { FormInstance, FormRules, TableColumnCtx, TableInstance } from 'element-plus'
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