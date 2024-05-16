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
              <div style="padding-bottom: 10px;">{{ index }}.{{ getProblemTag(type) }}</div>
              <div v-for="row in splitIntoRows(generateButtonGroups(buttonNum)[index].buttons, 6)" :key="row.join('-')"
                style="margin-bottom: 10px;">
                <el-button v-for="button in row" :key="button" style="width: 39px; height: 39px;" color="#626aef"
                  plain @click="scrollToProblem(button, type)" :class="{ 'active-button': isAnswered(getProblemId(type, button)) }">{{ button }}</el-button>
              </div>
            </div>
          </el-scrollbar>
          <div style="text-align: center">
            <el-form ref="submitForm" @submit.native.prevent="handleSubmit">
              <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" icon-color="#626AEF"
                @confirm="handleSubmit" title="确定要交卷吗？">
                <template #reference>
                  <el-button type="danger" plain size="large" @click="submitForm">提交试卷</el-button>
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
                <el-radio v-model="myAnswerList[problem.id]" :label="option">{{ option }}</el-radio>
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
        <!-- {{ myAnswerList }} -->
      </el-card>
    </el-main>
  </el-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getProblemTag, getCookie, formatOptions,getIndexCharacter,addBlank } from '../utils/tools';
import { studentPaperProblem } from '../requests/api';

const problemTypeList = ref<string[]>([]);
const buttonNum = ref<number[]>([]);
const problemList = ref([]);
const problemPreviewList = ref([]);
const problemRefs = ref<{ [key: string]: HTMLElement[] }>({});
const problemSelectList = ref([]);
const problemJudgeList = ref([]);
const problemBlankList = ref([]);
const problemShortList = ref([]);

const myAnswerList = ref([]);
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

const initialDuration = 7200; // 设定初始时长（例如2小时=7200秒）
const countdown = ref(initialDuration);

const formatTime = (time: number) => {
  const hours = String(Math.floor(time / 3600)).padStart(2, '0');
  const minutes = String(Math.floor((time % 3600) / 60)).padStart(2, '0');
  const seconds = String(time % 60).padStart(2, '0');
  return `${hours}:${minutes}:${seconds}`;
};

const submitForm = () => {
  const form = submitFormRef.value;
  if (form) {
    form.submit();
  }
};

const handleSubmit = () => {
  alert('试卷提交成功');
};


const locateProblem = (id) => {
  const problem = ref(myAnswerList.value.find(item => item.problemId === id));
  return problem.value;

};

const loadMyExam = async () => {
  console.log('加载考试列表');
  try {
    await myExam({
      studentId: getCookie('id')
    }).then(data => {
      console.log('获取的数据：', data);
      tableData.value = data.data.data
      console.log('获取的数据：', tableData.value);
      // 在这里处理返回的数据
      return tableData.value
    })
  } catch (error) {
    console.error('加载考试列表出错：', error);
    throw error; // 抛出错误，交给调用方处理
  }
};


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

const submitFormRef = ref<InstanceType<typeof HTMLFormElement> | null>(null);

onMounted(() => {
  const interval = setInterval(() => {
    if (countdown.value > 0) {
      countdown.value--;
    } else {
      clearInterval(interval);
      submitForm();
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