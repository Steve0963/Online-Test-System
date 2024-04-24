<template>
  <div style="margin-left: 12px;">
    <el-button plain @click="createForm">
      发布考试
    </el-button>
  </div>
  <el-dialog v-model="dialogFormVisible" title="编辑考试" width="680" draggable>
  <el-form
    ref="ruleFormRef"
    style="max-width: 600px"
    :model="ruleForm"
    :rules="rules"
    label-width="auto"
    class="demo-ruleForm"
    :size="formSize"
    status-icon
  >
    <el-form-item label="考试名称" prop="exam_name">
      <el-input v-model="ruleForm.exam_name" />
    </el-form-item>
    <el-form-item label="考试类型" prop="exam_type">
      <el-select v-model="ruleForm.exam_type" placeholder="请选择">
        <el-option label="线上" value="1" />
        <el-option label="线下" value="0" />
      </el-select>
    </el-form-item>

    <el-form-item label="考试地点" prop="exam_place" v-if="ruleForm.exam_type=='0'"style="width: 500px;">
      <el-input v-model="ruleForm.exam_place" />
    </el-form-item>

    <el-form-item label="考试试卷" prop="paper_list">
      <el-select-v2
        v-model="ruleForm.paper_list"
        placeholder="请选择"
        :options="options"
      />
    </el-form-item>

    <el-form-item label="考试班级" prop="exam_class">
      <el-select
    v-model="value"
    multiple
    clearable
    collapse-tags
    placeholder="选择班级"
    popper-class="custom-header"
    :max-collapse-tags="2"
    style="width: 240px"
  >
    <template #header>
      <el-checkbox
        v-model="checkAll"
        :indeterminate="indeterminate"
        @change="handleCheckAll"
      >
        全部
      </el-checkbox>
    </template>
    <el-option
      v-for="item in exam_class"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    />
  </el-select>
    </el-form-item>
    <el-form-item label="考试时间" required>
      <el-col :span="11">
        <el-form-item prop="start_time">
          <el-date-picker
            v-model="ruleForm.start_time"
            type="date"
            label="Pick a date"
            placeholder="开始时间"
            style="width: 100%"
          />
        </el-form-item>
      </el-col>
      <el-col class="text-center" :span="2">
        <span class="text-gray-500">-</span>
      </el-col>
      <el-col :span="11">
        <el-form-item prop="end_time">
          <el-time-picker
            v-model="ruleForm.end_time"
            label="Pick a time"
            placeholder="结束时间"
            style="width: 100%"
          />
        </el-form-item>
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">
        发布
      </el-button>
      <el-button @click="resetForm(ruleFormRef)">重置</el-button>
      <el-button @click="dialogFormVisible = false">取消</el-button>
    </el-form-item>
  </el-form>
</el-dialog>

<el-table ref="examListTable" row-key="id" :data="tableData" stripe  >
  <el-table-column prop="id" label="考试ID" width="150" />
  <el-table-column prop="exam_name" label="考试名称" width="280" sortable />
  <el-table-column prop="exam_type" label="考试方式" sortable :filters="typeFilterOptions" :filter-method="filterHandler" >
    <template #default="scope">
     {{ getExamType(scope.row.exam_type) }}
    </template>
  </el-table-column>
  <el-table-column prop="class_token" label="考试口令">
    <template #default="{ row }">
      <el-tag type="success">{{row.class_token}}</el-tag>
    </template>
  </el-table-column>

  <el-table-column prop="start_time" label="考试时间" sortable width="280" column-key="start_time"
    :filters="timeFilterOptions()" :filter-method="filterHandler" />
    <el-table-column prop="operaiton" label="操作">
      <el-button type="primary" :icon="CaretRight" plain>编辑考试</el-button>
    </el-table-column>

</el-table>
</template>

<script lang="ts" setup>
import { reactive, ref , watch,onMounted} from 'vue'
import type { ComponentSize, FormInstance, FormRules,CheckboxValueType } from 'element-plus'
import { examList,loadClass } from '../../../requests/api';
import { getCookie,getExamType } from '../utils/tool'
const checkAll = ref(false)
const indeterminate = ref(false)
const value = ref<CheckboxValueType[]>([])

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
const tableData= ref<Exam>([])
const exam_class = ref([])



watch(value, (val) => {
  if (val.length === 0) {
    checkAll.value = false
    indeterminate.value = false
  } else if (val.length === exam_class.value.length) {
    checkAll.value = true
    indeterminate.value = false
  } else {
    indeterminate.value = true
  }
})

const handleCheckAll = (val: CheckboxValueType) => {
  indeterminate.value = false
  if (val) {
    value.value = exam_class.value.map((_) => _.value)
  } else {
    value.value = []
  }
}
const dialogFormVisible = ref(false)


interface RuleForm {
  exam_name: string
  exam_place: string
  exam_type: string
  paper_list: string[]
  start_time: string
  end_time: string
  exam_class: string[]
}

const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive<RuleForm>({
  exam_name: 'Hello',
  exam_place: '逸夫楼',
  exam_type: '',
  paper_list: [],
  start_time: '',
  end_time: '',
  exam_class: [],
})


const rules = reactive<FormRules<RuleForm>>({
  exam_name: [
    { required: true, message: 'Please input Activity exam_name', trigger: 'blur' },
    { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
  ],
  exam_type: [
    {
      required: true,
      message: '请选择考试类型',
      trigger: 'change',
    },
  ],
  exam_place: [
    {
      required: true,
      message: '请填写考试地点',
      trigger: 'change',
    },
  ],
  exam_class: [
    {
      required: true,
      message: '请选择参与此次考试的班级',
      trigger: 'change',
    },
  ],
  paper_list: [
    {
      required: true,
      message: '请选择此次考试的试卷',
      trigger: 'change',
    },
  ],
  start_time: [
    {
      type: 'date',
      required: true,
      message: '请选择考试开始时间',
      trigger: 'change',
    },
  ],
  end_time: [
    {
      type: 'date',
      required: true,
      message: '请选择考试结束时间',
      trigger: 'change',
    },
  ],
})
const loadExamList = async () => {
  console.log('加载已发布的考试列表');
  try {
   await examList({
      createrId: getCookie('id')
    }).then(data => {
    tableData.value=data.data.data
    console.log('获取的数据：', tableData.value);
    // 在这里处理返回的数据
    return tableData.value
  })
    
  } catch (error) {
    console.error('加载考试列表出错：', error);
    throw error;
  }
}
const loadClassList = async () => {
  console.log('考试班级列表');
  try {
   await loadClass({
      createrId: getCookie('id')
    }).then(data => {
      data.data.data.forEach(item => {
        exam_class.value.push({ value: item.class_id, label: item.name });
    });
  })
  } catch (error) {
    console.error('加载班级列表出错：', error);
    throw error; 
  }
};

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log('submit!')
    } else {
      console.log('error submit!', fields)
    }
  })
}

const createForm = () => {
  dialogFormVisible.value=true
  loadClassList()
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

const options = Array.from({ length: 10000 }).map((_, idx) => ({
  value: `${idx + 1}`,
  label: `${idx + 1}`,
}))


const filterHandler = (
  value: string,
  row: Exam,
  column: TableColumnCtx<Exam>
) => {
  const property = column['property']
  return row[property] === value
}

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

onMounted(async () => {
  loadExamList()
    });

</script>
