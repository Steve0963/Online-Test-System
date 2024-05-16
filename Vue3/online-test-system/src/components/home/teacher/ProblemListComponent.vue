<template>
  <div style="margin-left: 12px;">
    <el-button plain @click="dialogFormVisible = true">
      创建题目
    </el-button>
  </div>

  <el-dialog v-model="dialogFormVisible" title="创建题目" width="500" draggable @closed="closeCreateProblem()">

    <el-form ref="formRef" style="max-width: 600px" :model="form" :rules="rules" label-width="auto" class="demo-form"
      :size="formSize" status-icon>
      <el-form-item label="题目描述" prop="desc">
        <el-input v-model="form.desc" />
      </el-form-item>

      <el-form-item label="题目类型" prop="type">
        <el-select v-model="form.type" placeholder="请选择">
          <el-option label="选择题" value="0" />
          <el-option label="判断题" value="1" />
          <el-option label="填空题" value="2" />
          <el-option label="简答题" value="3" />
        </el-select>
      </el-form-item>

      <el-form-item label="选项" prop="options" v-if="form.type == '0'" style="width: 500px;">
        <!-- <el-input v-model="form.place" /> -->

        <div class="flex gap-2">
          <el-tag v-for="tag in problemOptionList" :key="tag" closable :disable-transitions="false"
            @close="handleClose(tag)">
            {{ tag }}
          </el-tag>
          <el-input v-if="inputVisible" ref="InputRef" v-model="inputValue" class="w-20" size="small"
            @keyup.enter="handleInputConfirm" @blur="handleInputConfirm" />
          <el-button v-else class="button-new-tag" size="small" @click="showInput">
            + 添加选项
          </el-button>
        </div>
      </el-form-item>

      <el-form-item label="考试地点" prop="place" v-if="form.type == '1'" style="width: 500px;">
        <!-- <el-input v-model="form.place" /> -->
        判断题编写
      </el-form-item>

      <el-form-item label="考试地点" prop="place" v-if="form.type == '2'" style="width: 500px;">
        <!-- <el-input v-model="form.place" /> -->
        填空题
      </el-form-item>

      <el-form-item label="考试地点" prop="place" v-if="form.type == '3'" style="width: 500px;">
        <!-- <el-input v-model="form.place" /> -->
        简答题
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm(formRef)">
          保存
        </el-button>

        <el-button @click="resetForm(formRef)">重置</el-button>
        <el-button @click="dialogFormVisible = false">取消</el-button>

      </el-form-item>
    </el-form>

    {{ form }}
    {{ problemOptionList }}
  </el-dialog>

  <el-table ref="multipleTableRef" :data="tableData" style="width: 100%" stripe>

    <el-table-column type="expand">
      <template #default="props">
        <div m="4" style="
        padding-left: 15px;
        padding-right: 15px;">
          <p m="t-0 b-2">题目类型: {{ getProblemTag(props.row.type) }}</p>
          <p m="t-0 b-2">{{ props.row.id }}. {{ addBlank(props.row.desc) }}</p>
          <p m="t-0 b-2" v-if="props.row.type === '0'">选项: {{ formatOptions(props.row.options) }}</p>
          <p m="t-0 b-2" v-if="props.row.type === '0'">答案: {{ getAnswerOption(props.row.options, props.row.answer) }}
          </p>
          <p m="t-0 b-2" v-if="props.row.type === '1'">答案: {{ getBoolAnswer(props.row.answer) }}</p>
          <p m="t-0 b-2" v-if="props.row.type === '2'">答案: {{ props.row.answer }}</p>
          <p m="t-0 b-2" v-if="props.row.type === '3'">答案: {{ props.row.answer }}</p>
          <p m="t-0 b-2">解析: {{ props.row.explain }}</p>
        </div>
      </template>
    </el-table-column>

    <el-table-column prop="id" label="题目编号" width="130" />
    <el-table-column prop="type" label="题目类型" width="180" :filters="tagFilterOptions" :filter-method="filterTag"
      filter-placement="bottom-end">
      <template #default="scope">
        <el-tag :type="getProblemTagType(scope.row.type)" disable-transitions>{{ getProblemTag(scope.row.type)
          }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="desc" label="题目描述" />



    <el-table-column prop="create_time" label="创建时间" sortable width="280" column-key="create_time"
      :filters="timeFilterOptions()" :filter-method="filterHandler">
      <template #default="{ row }">
        {{ formatDateTime(row.create_time) }}
      </template>

    </el-table-column>

    <el-table-column prop="operaiton" label="操作">
      <template #default="scope">
        <el-button type="primary" :icon="Edit" size="small" @click="editProblem(scope.row)">编辑</el-button>
        <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled" icon-color="red"
          @confirm="deleteProblem(scope.row)" title="确定要删除这个题目吗？">
          <template #reference>
            <el-button type="danger" size="small" :icon="Delete"> 删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>

  </el-table>

</template>

<script lang="ts" setup>
import { reactive, onMounted, ref, nextTick, } from 'vue'
import { getCookie, formatDateTime, getProblemTag, getProblemTagType, formatOptions, getAnswerOption, getBoolAnswer, addBlank } from '../utils/tool'
import { useRouter } from 'vue-router';
import { problemList } from '../../../requests/api';
import { Delete, Edit, InfoFilled } from '@element-plus/icons-vue'
import type { FormInstance, FormRules, TableColumnCtx, TableInstance } from 'element-plus'
import { ElMessage, ElInput } from 'element-plus'

const inputValue = ref('')
const problemOptionList = ref([])
const inputVisible = ref(false)
const InputRef = ref<InstanceType<typeof ElInput>>()

const problemListTable = ref<TableInstance>()
const dialogFormVisible = ref(false)
const buttonVisible = ref(false)
const router = useRouter();
const isLoggedIn = getCookie('isLoggedIn') === 'true';
const role = getCookie('role');
const formLabelWidth = '140px'
const tableData = ref<Problem>([])
const formRef = ref<FormInstance>()


const form = reactive<Problem>({
  id: '',
  desc: '',
  type: '',
  answer: '',
  explain: '',
  options: [],
  createrId: getCookie('id')
})


const tagFilterOptions = [
  { text: '选择题', value: '0' },
  { text: '判断题', value: '1' },
  { text: '填空题', value: '2' },
  { text: '简答题', value: '3' },
  { text: '未知题型', value: '4' },
]

interface Problem {
  id: string,
  desc: string,
  type: string,
  answer: string,
  explain: string,
  options: [],
  createrId: string
}

const rules = reactive<FormRules<Problem>>({
  desc: [
    { required: true, message: '请输入题目描述', trigger: 'blur' },
  ],
  type: [
    {
      required: true,
      message: '请选择题目类型',
      trigger: 'change',
    },
  ],
  answer: [
    {
      required: true,
      message: '请填写答案',
      trigger: 'change',
    },
  ],
  options: [
    {
      required: true,
      message: '请增加选项',
      trigger: 'change',
    },
  ],
})


const handleClose = (tag: string) => {
  problemOptionList.value.splice(problemOptionList.value.indexOf(tag), 1)
}

const showInput = () => {
  inputVisible.value = true
  nextTick(() => {
    InputRef.value!.input!.focus()
  })
}

const handleInputConfirm = () => {
  if (inputValue.value) {
    problemOptionList.value.push(inputValue.value)
  }
  inputVisible.value = false
  inputValue.value = ''
}


const checkLogin = () => {
  if (!getCookie('isLoggedIn') === 'true' || getCookie('role') !== '1')
    router.push('/');
}

const filterTag = (value: string, row: Problem) => {
  return row.type === value
}

const closeCreateProblem = () => {

  dialogFormVisible.value = false
  form.classDesc = ''
  form.className = ''
  form.classId = ''

}

const editProblem = (row) => {
  dialogFormVisible.value = true
  form.classDesc = row.class_desc
  form.className = row.name
  form.classId = row.class_id

}

const deleteProblem = async (row) => {
  try {
    await deleteProblemById({
      classId: row.class_id
    }).then(data => {

      if (data.data.code == 200) {
        ElMessage({
          showClose: true,
          message: data.data.message,
          type: 'success',
        })
        loadProblemList()
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


const submitProblem = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate(async (valid) => {
    if (valid) {
      console.log('创建班级')
      buttonVisible.value = true
      const result = await creatProblem(form)
      console.log(result)
      if (result != null) {
        console.log(result.data.code)
        ElMessage({
          showClose: true,
          message: result.data.message,
          type: 'success',
        })
        loadProblemList()
        buttonVisible.value = false
        dialogFormVisible.value = false
      }

      else { //错误提示
        buttonVisible.value = false
        dialogFormVisible.value = true
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

const loadProblemList = async () => {
  console.log('加载题目列表');
  try {
    await problemList({
      createrId: getCookie('id')
    }).then(data => {
      console.log(data.data)
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





const resetDateFilter = () => {
  problemListTable.value!.clearFilter(['date'])
}

const clearFilter = () => {
  problemListTable.value!.clearFilter()
}


const filterHandler = (
  value: string,
  row: Problem,
  column: TableColumnCtx<Problem>
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
  loadProblemList()
  checkLogin()
});

</script>