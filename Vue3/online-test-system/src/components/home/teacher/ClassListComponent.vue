<template>
  <div style="margin-left: 12px;">
    <el-button plain @click="dialogFormVisible = true">
      创建班级
    </el-button>
  </div>

  <el-dialog v-model="dialogFormVisible" title="创建班级" width="500" draggable @closed="closeCreateClass()">
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
        <el-button @click="closeCreateClass()">取消</el-button>
        <el-button type="primary" @click="submitClass(formRef)" :disabled="buttonVisible">
          确定
        </el-button>
      </div>
    </template>{{ form }}{{ form }}
  </el-dialog>

  <el-table ref="classListTable" row-key="class_id" :data="tableData" stripe>
    <el-table-column prop="class_id" label="班级ID" width="100" />
    <el-table-column prop="name" label="班级名称" width="280" sortable />
    <el-table-column prop="class_desc" label="班级描述" width="280"/>
    <el-table-column prop="class_num" label="班级人数" sortable width="280">
      <template #default="{ row }">
        <span v-if="row.class_num === 0">暂时无人</span>
        <span v-else>{{ row.class_num }}</span>
      </template>
    </el-table-column>
    
    <el-table-column prop="class_token" label="班级口令">
      <template #default="{ row }">
        <el-tag type="success">{{row.class_token}}</el-tag>
      </template>
    </el-table-column>

    <el-table-column prop="creat_time" label="创建时间" sortable width="280" column-key="creat_time"
      :filters="timeFilterOptions()" :filter-method="filterHandler" >
      <template #default="{ row }">
        {{formatDateTime(row.creat_time)}}
      </template>
    
    </el-table-column>

      <el-table-column prop="operaiton" label="操作">
        <template #default="scope">
          <el-button type="primary" :icon="Edit" size="small" @click="editClass(scope.row)">编辑</el-button>
          <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled" icon-color="red"
          @confirm="deleteClass(scope.row)" title="确定要解散这个班级吗？与该班级相关的所有的内容都将被删除">
            <template #reference>
              <el-button type="danger" size="small" :icon="Delete"> 删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>

  </el-table>

</template>

<script lang="ts" setup>
import { reactive, onMounted , ref } from 'vue'
import { getCookie, formatDateTime} from '../utils/tool'
import { useRouter } from 'vue-router';
import { creatClass, deleteClassById, loadClass } from '../../../requests/api';
import { Delete,Edit,InfoFilled} from '@element-plus/icons-vue'
import type {FormInstance, FormRules, TableColumnCtx, TableInstance } from 'element-plus'
import {ElMessage} from 'element-plus'

const classListTable = ref<TableInstance>()
const dialogFormVisible = ref(false)
const buttonVisible = ref(false)
const router = useRouter();
const isLoggedIn = getCookie('isLoggedIn') === 'true';
const role = getCookie('role');
const formLabelWidth = '140px'
const tableData = ref<Class>([])
const formRef = ref<FormInstance>()


const form = reactive({
  className: '',
  classDesc: '',
  classId:'',
  createrId:getCookie('id')
})

interface Class {
  class_id: string
  name: string
  class_token: string
  creat_time: string
  class_num: number
  class_desc:string
}
const checkLogin = () => {
  if (!getCookie('isLoggedIn') === 'true' || getCookie('role') !== '1')
    router.push('/');
}


const closeCreateClass = () => {

  dialogFormVisible .value= false
  form.classDesc=''
  form.className=''
  form.classId=''
  
}

const editClass = (row) => {
  dialogFormVisible.value=true
  form.classDesc=row.class_desc
  form.className=row.name
  form.classId=row.class_id
 
}

const deleteClass= async (row) => {
  try {
    await deleteClassById({
      classId:row.class_id
    }).then(data => {

      if (data.data.code == 200) {
        ElMessage({
          showClose: true,
          message: data.data.message,
          type: 'success',
        })
        loadClassList()
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
        ElMessage({
    showClose: true,
    message: result.data.message,
    type: 'success',
  })
        loadClassList()
        buttonVisible.value=false
        dialogFormVisible.value=false
      }

      else { //错误提示
        buttonVisible.value=false
        dialogFormVisible.value=true
        ElMessage({
    showClose: true,
    message: result.data.message,
    type: 'danger',
  })
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


onMounted(async () => {
  loadClassList()
  checkLogin()
    });

</script>