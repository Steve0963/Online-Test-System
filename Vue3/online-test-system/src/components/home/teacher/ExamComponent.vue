<template>
  <div style="margin-left: 12px;">
    <el-button plain @click="createForm">
      发布考试
    </el-button>
  </div>
  <el-dialog v-model="dialogFormVisible" title="编辑考试" width="680" draggable @close="resetForm(ruleFormRef)">
    <el-form ref="ruleFormRef" style="max-width: 600px" :model="ruleForm" :rules="rules" label-width="auto"
      class="demo-ruleForm" :size="formSize" status-icon>
      <el-form-item label="考试名称" prop="exam_name">
        <el-input v-model="ruleForm.exam_name" />
      </el-form-item>

      <el-form-item label="考试类型" prop="exam_type">
        <el-select v-model="ruleForm.exam_type" placeholder="请选择">
          <el-option label="线上" value="1" />
          <el-option label="线下" value="0" />
        </el-select>
      </el-form-item>

      <el-form-item label="考试地点" prop="exam_place" v-if="ruleForm.exam_type == '0'" style="width: 500px;">
        <el-input v-model="ruleForm.exam_place" />
      </el-form-item>

      <el-form-item label="考试试卷" prop="paper_id">
        <el-select-v2 v-model="ruleForm.paper_id" placeholder="请选择" :options="paper_list" />
      </el-form-item>

      <el-form-item label="考试班级" prop="exam_class">
        <el-select v-model="ruleForm.exam_class" multiple clearable collapse-tags placeholder="选择班级"
          popper-class="custom-header" :max-collapse-tags="2" style="width: 240px">
          <template #header>
            <el-checkbox v-model="checkAll" :indeterminate="indeterminate" @change="handleCheckAll">
              全部
            </el-checkbox>
          </template>
          <el-option v-for="item in exam_class" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="考试时间" required>
        <el-col :span="11">
          <el-form-item prop="start_time">
            <el-date-picker v-model="ruleForm.start_time" type="datetime" placeholder="开始时间" />

          </el-form-item>
        </el-col>
        <el-col class="text-center" :span="2">
          <span class="text-gray-500">-</span>
        </el-col>
        <el-col :span="11">
          <el-form-item prop="end_time">
            <el-date-picker v-model="ruleForm.end_time" type="datetime" placeholder="结束时间" />
          </el-form-item>
        </el-col>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm(ruleFormRef)">
          保存
        </el-button>

        <el-button @click="resetForm(ruleFormRef)">重置</el-button>
        <el-button @click="dialogFormVisible = false">取消</el-button>

      </el-form-item>
    </el-form>
    {{ ruleForm }}
    <!-- {{ paper_list }} -->
  </el-dialog>

  <el-table ref="examListTable" row-key="id" :data="tableData" stripe sortable :border="parentBorder"
    style="width: 100%">
    <el-table-column type="expand">
      <template #default="props">
        <div m="4" style="
        padding-left: 15px;
        padding-right: 15px;">
          <p m="t-0 b-2">考试时长: {{ calculateExamDuration(props.row.start_time,props.row.end_time) }}</p>
          <p m="t-0 b-2">参考班级: {{ getClassLabels(props.row.exam_class) }}</p>
          <p m="t-0 b-2">考试人数: {{  getClassNumSum(props.row.exam_class) }}</p>
          <p m="t-0 b-2">采用试卷: {{ getPaperLabel(props.row.paper_id)}}</p>
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="id" label="考试ID" width="150" />
    <el-table-column prop="exam_name" label="考试名称" width="380" sortable />
    <el-table-column prop="exam_type" label="考试方式" sortable :filters="typeFilterOptions" :filter-method="filterHandler">
      <template #default="scope">
        {{ getExamType(scope.row.exam_type) }}
      </template>
    </el-table-column>

    <el-table-column prop="start_time" label="考试时间" sortable width="480" column-key="start_time"
      :filters="timeFilterOptions()" :filter-method="filterHandler">
      <template #default="{ row }">
       {{formatDateTime(row.start_time)}}----{{formatDateTime(row.end_time) }}
      </template>
    </el-table-column>
    <el-table-column prop="operaiton" label="操作">
      <template #default="scope">
        <el-button type="primary" :icon="Edit" size="small" @click="editExam(scope.row)">编辑</el-button>
        <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled" icon-color="#626AEF"
          @confirm="removeExam(scope.row)" title="确定要删除本场考试吗？">
          <template #reference>
            <el-button type="danger" size="small" :icon="Delete"> 删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>

  </el-table>
</template>

<script lang="ts" setup>
import { reactive, ref, watch, onMounted } from 'vue'
import { Delete, Edit,InfoFilled } from '@element-plus/icons-vue'
import type { ComponentSize, FormInstance, FormRules, CheckboxValueType, CaretRight } from 'element-plus'
import { deleteExam, examList, loadClass, paperList, saveExam } from '../../../requests/api';
import { getCookie, getExamType,calculateExamDuration ,formatExamTime,formatDateTime} from '../utils/tool'
import { ElNotification ,ElMessage} from 'element-plus'

const checkAll = ref(false)
const indeterminate = ref(false)
const dialogFormVisible = ref(false)
const value = ref<CheckboxValueType[]>([])
const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>()
const tableData = ref<Exam>([])
const exam_class = ref<CheckboxValueType[]>([])
const paper_list = ref([])
const class_list=ref([])
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


interface RuleForm {
  id: string
  exam_name: string
  exam_place: string
  exam_type: string
  paper_id: string[]
  start_time: string
  end_time: string
  exam_class: string[]
  createrId:string
}


const ruleForm = reactive<RuleForm>({
  exam_name: '',
  id: '',
  exam_place: '',
  exam_type: '',
  paper_id: [],
  start_time: '',
  end_time: '',
  exam_class: [],
  createrId:getCookie('id')
})


const rules = reactive<FormRules<RuleForm>>({
  exam_name: [
    { required: true, message: '请输入考试名称', trigger: 'blur' },
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
const checkLogin = () => {
  if (!getCookie('isLoggedIn') === 'true' || getCookie('role') !== '1')
    router.push('/');
}

const handleCheckAll = (val: CheckboxValueType) => {
  indeterminate.value = false
  if (val) {
    value.value = exam_class.value.map((_) => _.value)
  } else {
    value.value = []
  }
}

const getPaperLabel = (value) => {
  const item = paper_list.value.find(item => item.value === value);
  return item ? item.label : null;
};

const getClassLabels = (class_id_list) => {
  const labels = [];
  for (const class_id of class_id_list) {
    const item = exam_class.value.find(item => item.value === class_id);
    if (item) {
      labels.push(item.label);
    }
  }
  return labels.join(" , ");
};

const getClassNumSum = (class_id_list) => {
  return class_id_list.reduce((sum, class_id) => {
    const classItem = class_list.value.find(item => item.class_id === class_id);
    return sum + (classItem ? classItem.class_num : 0);
  }, 0);
};
const editExam = async (exam) => {
  dialogFormVisible.value = true
  ruleForm.exam_name = exam.exam_name
  ruleForm.id = exam.id
  ruleForm.paper_id = exam.paper_id
  ruleForm.exam_type = exam.exam_type
  ruleForm.exam_class=exam.exam_class
  ruleForm.start_time = exam.start_time
  ruleForm.end_time = exam.end_time
  ruleForm.exam_place = exam.exam_place
}

const removeExam = async (exam) => {
  try {
    await deleteExam({
      examId: exam.id
    }).then(data => {

      if (data.data.code == 200) {
        ElMessage({
          showClose: true,
          message: data.data.message,
          type: 'success',
        })
        loadExamList()
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


const loadExamList = async () => {
  console.log('加载已发布的考试列表');
  try {
    await examList({
      createrId: getCookie('id')
    }).then(data => {
      tableData.value = data.data.data
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
      console.log(data.data.data)
      class_list.value=data.data.data
      data.data.data.forEach(item => {
        exam_class.value.push({ value: item.class_id, label: item.name });
      });
    })
  } catch (error) {
    console.error('加载班级列表出错：', error);
    throw error;
  }
};

const loadPaperList = async () => {
  console.log('试卷列表');
  try {
    await paperList({
      createrId: getCookie('id')
    }).then(data => {
      console.log(data.data)
      data.data.data.forEach(item => {
        paper_list.value.push({ value: item.id, label: item.paper_name });
      });
    })
  } catch (error) {
    console.error('加载试卷列表出错：', error);
    throw error;
  }
};

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  try {
    await formEl.validate();
    console.log('Form validation passed');
    // 这里可以继续执行后续的逻辑

    // 调用 paperList
    const data = await saveExam({
      examForm: ruleForm,
    }).then(data => {
      console.log(data.data)
      if (data.data.code == 200) {
        ElMessage({
          showClose: true,
          message: data.data.message,
          type: 'success',
        })
        loadExamList()
      dialogFormVisible.value = false
      }
      else {
        ElMessage({
          showClose: true,
          message: data.data.message,
          type: 'danger',
        })
      }
      
    });
    console.log(data.data);
  } catch (error) {
    console.error('Form validation error:', error);
    // 如果出现错误，可以在这里处理错误情况
  }
}
const createForm = () => {
  dialogFormVisible.value = true
  loadClassList()
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
  ruleForm.exam_name = ''
  ruleForm.id = ''
  ruleForm.paper_id = ''
  ruleForm.exam_type = ''
  ruleForm.exam_class=[]
  ruleForm.start_time = ''
  ruleForm.end_time = ''
  ruleForm.exam_place = ''
}

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
  loadPaperList()
  loadClassList()
  checkLogin()
});

</script>
