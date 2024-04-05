<template>
  <div style="margin-left: 12px;"><el-button plain @click="dialogFormVisible = true">
    创建班级
  </el-button>
    </div>
    <el-dialog v-model="dialogFormVisible" title="创建班级" width="500" draggable>
      <el-form ref="formRef" :model="form" :rules="create_rules">
        <el-form-item label="班级名称" :label-width="formLabelWidth" prop="className" >
          <el-input v-model="form.className" autocomplete="off" />
        </el-form-item>

        <el-form-item label="班级描述" :label-width="formLabelWidth" prop="classDesc" >
          <el-input v-model="form.classDesc" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary"  @click="submitClass(formRef)">
            确定
          </el-button>
        </div>
      </template>
    </el-dialog>
  
    <el-table ref="classListTable" row-key="id" :data="classListData" stripe>
      <el-table-column prop="class_id" label="班级ID" width="200"/>
      <el-table-column prop="class_name" label="班级名称" width="380" sortable />
      <el-table-column prop="class_num" label="班级人数" sortable   width="380"/>
      <el-table-column prop="class_token" label="班级口令"  >
        <template #default>
          <el-tag type="success" >{{ 'AKS-UJK'}}</el-tag>
        </template>
      </el-table-column>
  
      <el-table-column prop="date" label="创建时间" sortable width="280" column-key="date" :filters="timeFilterOptions()" :filter-method="filterHandler" />
  
    </el-table>
  </template>
  
  <script lang="ts" setup>
import { onMounted } from 'vue';
import { getCookie } from '../utils/tool'
import { useRouter } from 'vue-router';
import {creatClass,loadClass} from '../../../requests/api';
const dialogFormVisible = ref(false)
const router = useRouter();
const isLoggedIn = getCookie('isLoggedIn') === 'true';
const role = getCookie('role');
const formLabelWidth = '140px'

onMounted(() => {
 
});


const validate = (rule: any, value: any, callback: any) => {
    if (!value) {
        return callback(new Error('班级名称不能为空'));
    }

    else if(value.length>15){
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
})
const formRef = ref<FormInstance>()

const submitClass = (formEl: FormInstance | undefined) => {
  if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            console.log('创建班级')
            const result=await (await creatClass(form)).data.data
            console.log(result)

            if(result != null) {
                console.log( )
                return ref(false)
        }

        if(result == null) { //错误提示
          return ref(true)
        }
            
        } else {
            console.log('error submit!')
            return false
        }
    })
}

const loadClassLIst = async () => {
  const account=getCookie('account')
  if (!account) return
        
            console.log('创建班级')
            console.log(account)
            const result=await (await creatClass(account)).data.data
            console.log(result)

            if(result != null) {
                console.log( )
                return result
        }

        else { //错误提示
          console.log('error submit!')
        }
        
            
        }
           
        const classListData=loadClassLIst() 
   


if (!isLoggedIn) {
  router.push('/');
} else if (role !== '1') {
  
  router.push('/');
}


  import { reactive, ref } from 'vue'
  import type { FormInstance, FormRules, TableColumnCtx, TableInstance } from 'element-plus'
  
  interface Class {
    class_name: string
    class_id: string
    class_num: number
    class_token: string
    date: string
  }

  
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
    const options=[
      { text: '2016-05-01', value: '2016-05-01' },
      { text: '2016-05-02', value: '2016-05-02' },
      { text: '2016-05-03', value: '2016-05-03' },
      { text: '2016-05-04', value: '2016-05-04' },
    ]
    return options
  }
  
  const tableData: Class[] = [
    {
      date: '2016-05-01',
      class_name: '计算机导论',
      class_id: '24130',
      class_num: 48,
      class_token: '线上'
    },
    {
      date: '2016-05-02',
      class_name: '软件工程',
      class_id: '24131',
      class_num: 79,
      class_token: '线下'
    },
    {
      date: '2016-05-03',
      class_name: '大学英语2',
      class_id: '24132',
      class_num: 69,
      class_token: '线上'
    },
  
  ]
  </script>