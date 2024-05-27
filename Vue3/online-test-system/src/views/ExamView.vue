<template>
  <el-container style="height: 100vh">
    <el-aside style="padding-left: 15px; padding-top: 25px; margin-right: 15px; width: 360px;">
      <div class="left-card">
        <el-card style="height: 180px; text-align: center;background-color: #929d94;" shadow="always">
          <el-text>
            <el-icon>
              <Clock />
            </el-icon>
            考试倒计时
          </el-text>
          <div class="countdown" style="margin-top: 10px;">{{ formatTime(countdown) }}</div>
          <div style="font-weight: bold; font-size: 20px; margin-top: 25px;">{{ getCookie('name') }}</div>
        </el-card>

        <el-card style="height: 74vh; margin-top: 25px;background-color: #e5d8d8" shadow="always">
          <el-scrollbar height="65vh" style="width: 360px;">
            <div v-for="(type, index) in problemTypeList" :key="type">
              <div style="padding-bottom: 10px;">{{ index+1 }}.{{ getProblemTag(type) }}</div>
              <div v-for="row in splitIntoRows(generateButtonGroups(buttonNum)[index].buttons, 6)" :key="row.join('-')"
                style="margin-bottom: 10px;">
                <el-button v-for="button in row" :key="button" style="width: 39px; height: 39px;" color="#626aef"
                  plain @click="scrollToProblem(button, type)" :class="{ 'active-button': isAnswered(getProblemId(type, button)) }">{{ button }}</el-button>
              </div>
            </div>
          </el-scrollbar>
          <div style="text-align: center">
            <el-form>
              <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" icon-color="#626AEF"
                @confirm="submitPaperAnswer" title="确定要交卷吗？">
                <template #reference>
                  <el-button type="danger" plain size="large">提交试卷</el-button>
                </template>
              </el-popconfirm>
            </el-form>
          </div>
        </el-card>
      </div>
    </el-aside>
    <el-main style="padding-top: 25px;">
      <el-card style="height: 95vh;background-color: #e5e5e5">
        <el-scrollbar max-height="90vh" style="width: 77vw;">
        <div v-for="(type, index) in problemTypeList" :key="type">
          <h3>{{getIndexCharacter(index+1)}}{{ getProblemTag(type) }}</h3>
          <div v-if="type === '0'">
            <div v-for="(problem,index) in problemSelectList" :ref="setProblemRef(index, type)">
              <h5 style="margin-bottom: 10px;">{{ index+1 }}.{{ problem.desc }}</h5>
              <div v-for="(option, index) in formatOptions(problem.options)" :key="index">
                <el-radio v-model="myAnswerList[problem.id]" :label="formatAnswer(option)">{{ option }}</el-radio>
              </div>
            </div>
          </div>
          <div v-if="type === '1'">
            <div v-for="(problem,index) in problemJudgeList " :ref="setProblemRef(index, type)">
              <h5 style="margin-bottom: 10px;">{{ index+1 }}.{{ problem.desc }}</h5>
              <el-radio value="1" size="large" v-model="myAnswerList[problem.id]">正确</el-radio>
              <el-radio value="0" size="large" v-model="myAnswerList[problem.id]">错误</el-radio>
            </div>
          </div>

          <div v-if="type === '2'">
            <div v-for="(problem,index) in problemBlankList" :ref="setProblemRef(index, type)">
              <h5 style="margin-bottom: 10px;">{{ index+1 }}.{{ addBlank(problem.desc) }}</h5>
              <el-input v-model="myAnswerList[problem.id]" style="width: 240px" :autosize="{ minRows: 2, maxRows: 4 }"
                type="textarea" placeholder="输入答案" />
            </div>
          </div>

          <div v-if="type === '3'">
            <div v-for="(problem,index) in problemShortList" :ref="setProblemRef(index, type)">
              <h5 style="margin-bottom: 10px;">{{ index+1 }}.{{ problem.desc }}</h5>
              <el-input v-model="myAnswerList[problem.id]" style="width: 440px" :autosize="{ minRows: 2, maxRows: 4 }"
                type="textarea" placeholder="输入答案" />
            </div>
          </div>
          
        </div>
      </el-scrollbar>
        {{ myAnswerList }}
        <!-- {{ problemList }} -->
        {{ getVariable('examId') }}
      </el-card>
    </el-main>
  </el-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getProblemTag, getCookie, formatOptions,getIndexCharacter,addBlank,formatAnswer,getVariable,setCookie} from '../utils/tools';
import { studentPaperProblem, submitPaperAnsewr } from '../requests/api';
import { ElMessage } from 'element-plus';
import router from '../router';

const problemTypeList = ref<string[]>([]);
const buttonNum = ref<number[]>([]);
const problemList = ref([]);
const problemPreviewList = ref([]);
const problemRefs = ref<{ [key: string]: HTMLElement[] }>({});
const problemSelectList = ref([]);
const problemJudgeList = ref([]);
const problemBlankList = ref([]);
const problemShortList = ref([]);

const myAnswerList = ref({});
const getProblemId = (type, index) => {
  if (type === '0') {
    return problemSelectList.value[index - 1].id;
  } else if (type === '1') {
    return problemJudgeList.value[index - 1].id;
  } else if (type === '2') {
    return problemBlankList.value[index - 1].id;
  } else if (type === '3') {
    return problemShortList.value[index - 1].id;
  }
  return null;
};
const isAnswered = (problemId) => {
  return myAnswerList.value[problemId] !== undefined && myAnswerList.value[problemId] !== '';
};

const setProblemRef = (index, type) => (el) => {
  if (!problemRefs.value[type]) {
    problemRefs.value[type] = [];
  }
  problemRefs.value[type][index] = el;
};

const scrollToProblem = (index, type) => {
  const problem = problemRefs.value[type][index - 1];
  if (problem) {
    problem.scrollIntoView({ behavior: 'smooth' });
  }
};

const generateButtonGroups = (problemNum: number[]) => {
  const groups: { title: string, buttons: number[] }[] = [];
  for (let i = 0; i < problemNum.length; i++) {
    groups.push({
      title: `第${i + 1}组`,
      buttons: Array.from({ length: problemNum[i] }, (_, j) => j + 1)
    });
  }
  return groups;
};

const splitIntoRows = (arr: number[], rowSize: number) => {
  const rows = [];
  for (let i = 0; i < arr.length; i += rowSize) {
    rows.push(arr.slice(i, i + rowSize));
  }
  return rows;
};

const calculateCountdown = () => {
  const now = new Date(); // 获取当前时间
  const endTime = new Date(getVariable('end_time')); // 解析考试结束时间字符串为日期对象
  const diffInMilliseconds = endTime.getTime() - now.getTime(); // 计算时间差，单位为毫秒

  if (diffInMilliseconds > 0) {
    return Math.floor(diffInMilliseconds / 1000); // 将毫秒转换为秒
  }

};


const initialDuration = calculateCountdown(); // 设定初始时长（例如2小时=7200秒）
const countdown = ref(initialDuration);

const formatTime = (time: number) => {
  const hours = String(Math.floor(time / 3600)).padStart(2, '0');
  const minutes = String(Math.floor((time % 3600) / 60)).padStart(2, '0');
  const seconds = String(time % 60).padStart(2, '0');
  return `${hours}:${minutes}:${seconds}`;
};


const submitPaperAnswer  = async () => {
  console.log(myAnswerList.value)
  try {
    await submitPaperAnsewr({
      studentId: getCookie('id'),
      paperForm: myAnswerList.value,
      examId:getVariable('examId'),
      paperId:getVariable('paperId'),
    }).then(data => {
      if (data.data.code == 200) {
        ElMessage({
          showClose: true,
          message: data.data.message,
          type: 'success',
        })
        setCookie('examId', '')
        setCookie('paperId', '')
        setCookie('end_time', '')
        router.push('/home/student/test')
      }
      else {
        ElMessage({
          showClose: true,
          message: data.data.message,
          type: 'error',
        })
      }

    })

  } catch (error) {
    console.error('提交试卷出错！：', error);
    throw error;
  }


}
const checkLogin = () => {
  if (!getCookie('isLoggedIn') === 'true') router.push('/'); 
  else if (getCookie('role') !== '2'){
    ElMessage({
      showClose: true,
      message: '您没有权限访问该页面！',
      type: 'error',
    })
    router.push('/home/teacher/stulist')
    return false
  }
}


const loadPaperProblem = async () => {
  console.log('加载试卷列表');
  try {
    await studentPaperProblem({ paperId: 1 }).then(data => {
      problemList.value = data.data.data;
      console.log('获取的数据：', problemList.value);
      // 处理返回的数据，统计type的类型和数量
      const typeCount: { [key: string]: number } = {};
      problemList.value.forEach((problem: any) => {
        // myAnswerList.value.push({ problemId: problem.id, answer: '' })
        myAnswerList.value[problem.id]=''
        if (typeCount[problem.type]) {
          typeCount[problem.type]++;
        } else {
          typeCount[problem.type] = 1;
        }

        if (problem.type === '0') {
          problemSelectList.value.push(problem)
        } else if (problem.type === '1') {
          problemJudgeList.value.push(problem)
        } else if (problem.type === '2') {
          problemBlankList.value.push(problem)
        }
        else if (problem.type === '3') {
          problemShortList.value.push(problem)

        }
        // 将题干描述加入到problemPreviewList中
        problemPreviewList.value.push({ desc: problem.desc, type: problem.type });
      });
      problemTypeList.value = Object.keys(typeCount);
      buttonNum.value = Object.values(typeCount);

    });
  } catch (error) {
    console.error('加载考试列表出错：', error);
    throw error;
  }
};

onMounted(() => {
  if(!checkLogin()) return
  const interval = setInterval(() => {
    if (countdown.value > 0) {
      countdown.value--;
    } else {
      clearInterval(interval);
      submitPaperAnswer()
    }
  }, 1000);

  loadPaperProblem();
});
</script>

<style scoped>
.countdown {
  font-size: 24px;
  /* 调整字体大小 */
  color: pink;
  /* 调整字体颜色 */
}

.active-button {
  background-color: #626aef !important;
  color: white !important;
}
</style>