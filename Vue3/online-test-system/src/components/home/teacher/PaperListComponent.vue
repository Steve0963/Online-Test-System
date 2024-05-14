<template>
  <div style="margin-left: 12px;">
    <el-button plain @click="createForm">
      创建试卷
    </el-button>
  </div>
  <el-dialog v-model="dialogFormVisible" title="编辑试卷" width="1680" draggable>
    <el-form ref="ruleFormRef" style="max-width: 600px" :model="ruleForm" :rules="rules" label-width="auto"
      class="demo-ruleForm" :size="formSize" status-icon>
      <el-form-item label="试卷名称" prop="paper_name">
        <el-input v-model="ruleForm.paper_name" />
      </el-form-item>
      <div style="padding-bottom: 15px;">
        <el-tag type="success">选择题</el-tag>
        <el-tag type="primary">判断题</el-tag>
        <el-tag type="info">填空题</el-tag>
        <el-tag type="warning">简答题</el-tag>
        <el-tag type="danger">选项</el-tag>
      </div>
      <el-scrollbar max-height="600px" style="width: 1666px;">



        <p v-for="(item, index) in previewProblemList" :key="item.id" class="scrollbar-demo-item">
          <!-- <span> {{ index + 1 }}.:{{ item.desc }}</span>
            <span><el-button type="danger" @click="loadProblemList()">
              删除
             </el-button></span>
            <el-divider /> -->

          <el-tag closable @close="handleDelProblem(item.id)" :type="getProblemTagType(item.type)" size="large">
            第 {{ index + 1 }} 题：{{ addBlank(item.desc) }}
          </el-tag>
          <el-tag v-if="item.type == '0'" type="danger" size="large">
            {{ addIndex(item.options) }}
          </el-tag>
        </p>

      </el-scrollbar>

    </el-form>
    <el-button type="primary" @click="loadProblemList()">
      添加题目
    </el-button>

    <el-dialog v-model="innerVisible" width="1300" append-to-body draggable>
      <el-table ref="multipleTableRef" :data="problemTable" style="width: 100%"
        @selection-change="handleSelectionChange" stripe>
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="题目编号" width="130" />
        <el-table-column prop="type" label="题目类型" width="180" :filters="tagFilterOptions" :filter-method="filterTag"
          filter-placement="bottom-end">
          <template #default="scope">
            <el-tag :type="getProblemTagType(scope.row.type)" disable-transitions>{{ getProblemTag(scope.row.type)
              }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="desc" label="题目描述" />

      </el-table>
      <el-pagination v-model:current-page="currentPage3" :page-size="20" :small="small" :disabled="disabled"
        :background="background" layout="prev, pager, next, jumper" :total="problemTable.length"
        :hide-on-single-page="true" @current-change="handleCurrentChange" />

      <el-button type="primary" @click="handlePreview()">确定</el-button>
      <!-- {{ multipleSelection }} -->
    </el-dialog>


    <el-button type="primary" @click="submitForm()">
      保存试卷
    </el-button>
      <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled" icon-color="#626AEF"
      @confirm="handleCancleEdit()" title="未保存的进度将会丢失，确定取消吗？">
        <template #reference>
          <el-button> 取消</el-button>
        </template>
      </el-popconfirm>

    {{ ruleForm }}
    <!-- {{ ruleForm }}
    {{ previewProblemList }} -->
  </el-dialog>

  <el-table ref="paperListTable" row-key="id" :data="tableData" stripe>
    <el-table-column prop="id" label="试卷ID" width="150" />
    <el-table-column prop="paper_name" label="试卷名称" width="480" sortable />
    <el-table-column prop="create_time" label="创建时间" sortable width="680" column-key="start_time"
      :filters="timeFilterOptions()" :filter-method="filterHandler" />
    <el-table-column prop="operaiton" label="操作">
      <template #default="scope">
        <el-button type="primary" :icon="Edit" size="small" @click="editPaper(scope.row)">编辑</el-button>
        <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled" icon-color="#626AEF"
        @confirm="removePaper(scope.row)" title="确定要删除这套试卷吗">
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
import type { ComponentSize, FormInstance, FormRules, CheckboxValueType} from 'element-plus'
import { ElNotification ,ElMessage} from 'element-plus'
import { examList, loadClass, paperList, paperProblem, problemList, savePaperProblem } from '../../../requests/api';
import { getCookie, getExamType, getProblemTag, getProblemTagType, addIndex, addBlank } from '../utils/tool'
import { Delete,Edit,} from '@element-plus/icons-vue'
const checkAll = ref(false)
const indeterminate = ref(false)
const value = ref<CheckboxValueType[]>([])
const innerVisible = ref(false)
const multipleSelection = ref<Problem[]>([])
const previewProblemList = ref<Problem[]>([])
const tableData = ref<Paper>([])
const problemTable = ref<Problem>([])
const paper_class = ref<CheckboxValueType[]>([])
const paper_list = ref([])
const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>()

const ruleForm = reactive<Paper>({
  paper_name: '',
  id: '',
})

interface RuleForm {
  paper_name: string
  paper_place: string
  paper_type: string
  paper_list: string[]
  start_time: string
  end_time: string
  paper_class: string[]
  paperId: string
}

interface Paper {
  id: string
  paper_name: string
  create_time: string
}

interface Problem {
  id: string
  desc: string
  type: string
  create_time: string
  options: string
}


const rules = reactive<FormRules<Paper>>({
  paper_name: [
    { required: true, message: '请输入试卷名称', trigger: 'blur' },
  ],
})

const handleDelProblem = (id) => {
  previewProblemList.value = previewProblemList.value.filter(item => item.id !== id);
}

const handleSelectionChange = (val: Problem[]) => {
  multipleSelection.value = val
}

const handleCancleEdit = () => {
  dialogFormVisible.value = false
  previewProblemList.value = []
  ruleForm.id = ''
  ruleForm.paper_name = ''


}



const editPaper = async (paper) => {
  dialogFormVisible.value = true
  ruleForm.paper_name = paper.paper_name
  ruleForm.id = paper.id
  try {
    await paperProblem({
      paperId: paper.id
    }).then(data => {
      previewProblemList.value = data.data.data
      console.log('获取的数据：', previewProblemList.value);
      // 在这里处理返回的数据
      return previewProblemList.value
    })

  } catch (error) {
    console.error('加载考试列表出错：', error);
    throw error;
  }


}

const removePaper = async (paper) => {
  dialogFormVisible.value = true
  ruleForm.paper_name = paper.paper_name
  ruleForm.id = paper.id
  try {
    await paperProblem({
      paperId: paper.id
    }).then(data => {
      previewProblemList.value = data.data.data
      console.log('获取的数据：', previewProblemList.value);
      // 在这里处理返回的数据
      return previewProblemList.value
    })

  } catch (error) {
    console.error('加载考试列表出错：', error);
    throw error;
  }


}

const handlePreview = () => {

  const previewSet = new Set(previewProblemList.value.map(JSON.stringify));
  const newValues = multipleSelection.value.filter(value => !previewSet.has(JSON.stringify(value)));
  previewProblemList.value.push(...newValues);
  innerVisible.value = false;
}




const tagFilterOptions = [
  { text: '选择题', value: '0' },
  { text: '判断题', value: '1' },
  { text: '填空题', value: '2' },
  { text: '简答题', value: '3' },
  { text: '未知题型', value: '4' },
]

const filterTag = (value: string, row: Problem) => {
  return row.type === value
}
watch(value, (val) => {
  if (val.length === 0) {
    checkAll.value = false
    indeterminate.value = false
  } else if (val.length === paper_class.value.length) {
    checkAll.value = true
    indeterminate.value = false
  } else {
    indeterminate.value = true
  }
})

const handleCheckAll = (val: CheckboxValueType) => {
  indeterminate.value = false
  if (val) {
    value.value = paper_class.value.map((_) => _.value)
  } else {
    value.value = []
  }
}
const dialogFormVisible = ref(false)




const loadPaperList = async () => {
  console.log('加载试卷列表');
  try {
    await paperList({
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
      data.data.data.forEach(item => {
        paper_class.value.push({ value: item.class_id, label: item.name });
      });
    })
  } catch (error) {
    console.error('加载班级列表出错：', error);
    throw error;
  }
};

const loadProblemList = async () => {
  innerVisible.value = true
  console.log('加载题目列表');
  try {
    await problemList({
      createrId: getCookie('id')
    }).then(data => {
      console.log(data.data)
      problemTable.value = data.data.data
      console.log('获取的数据：', problemTable.value);
      // 在这里处理返回的数据
      return problemTable.value
    })

  } catch (error) {
    console.error('加载考试列表出错：', error);
    throw error;
  }
}
const submitForm = async () => {
  console.log('提交试卷题目');
  try {
    await savePaperProblem({
      createrId: getCookie('id'),
      paperId: ruleForm.id,
      paperName: ruleForm.paper_name,
      problemForm: previewProblemList.value,
    }).then(data => {
      console.log(data.data)
      if (data.data.code == 200) {
        ElMessage({
          showClose: true,
          message: data.data.message,
          type: 'success',
        })
        loadPaperList()
      dialogFormVisible.value = false
      }
      else {
        ElMessage({
          showClose: true,
          message: '保存失败',
          type: 'danger',
        })
      }
      
    })
  } catch (error) {
    console.error('加载出错：', error);
    throw error;
  }
}

const createForm = () => {
  dialogFormVisible.value = true
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
  row: Paper,
  column: TableColumnCtx<Paper>
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

const handleSizeChange = (val: number) => {
  console.log(`${val} items per page`)
}
const handleCurrentChange = (val: number) => {
  console.log(`current page: ${val}`)
}

onMounted(async () => {
  loadPaperList()
});

</script>
