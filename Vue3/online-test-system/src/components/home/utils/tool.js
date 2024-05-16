// utils/cookieUtils.js
var currentPage=null
export function getCookie(name) {
    const cookieString = document.cookie;
    const cookies = cookieString.split(';');
    for (let cookie of cookies) {
      const [cookieName, cookieValue] = cookie.trim().split('=');
      if (cookieName === name) {
        return cookieValue;
      }
    }
    return null;
  }


export function setCurrentPage(path){
  localStorage.setItem('page',path)
}
export function getCurrentPage(){
  return localStorage.getItem('page')
  }


 export function getExamType (type){
    if(type==1) return "线上"
    return "线下"
  }

  export function getTagType (score){
    if (score >= 90) return 'success'
    else if (score >= 80) return 'primary'
    else if (score >= 70) return 'info'
    else if (score >= 60) return 'warning'
    else return 'danger'
  }

  export function getTag (score){
    if (score>=90) return '优'
    else if(score>=80) return '良'
    else if(score>=70) return '中'
    else if(score>=60) return '差'
    else return '不及格'
  }


  export function getProblemTagType (type){
    if (type == 0) return 'success'
    else if (type == 1) return 'primary'
    else if (type == 2) return 'info'
    else if (type == 3) return 'warning'
    else return 'danger'
  }

  export function getProblemTag (type){
    if (type==0) return '选择题'
    else if(type==1) return '判断题'
    else if(type==2) return '填空题'
    else if(type==3) return '简答题'
    else return '未知类型'
  }

  export function addIndex(str) {
    const parts = str.split(';');
  let result = '';
  let index = 65; // ASCII码中65对应字母'A'

  for (let i = 0; i < parts.length - 1; i++) {
    result += String.fromCharCode(index) + ': ' + parts[i] + ' ';
    index++;
  }


  // 去除最后一个空格
  result = result.trim();

  return result;
  }
  

  export function addBlank(str) {
    if (!str.includes(';')) {
      return str;
    }
    const parts = str.split(';');
    const result = parts.join('____');
    return result;
  }


  export function calculateExamDuration(startTime, endTime){
    const start = new Date(startTime);
    const end = new Date(endTime);
    const duration = (end.getTime() - start.getTime()) / (1000 * 60); // 转换为分钟
    const hours = Math.floor(duration / 60);
    const minutes = duration % 60;
    return `${hours}小时 ${minutes}分钟`;
  };


  export function formatExamTime (startTime){

    const formatDateTime = (dateTime) => {
      const date = new Date(dateTime);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}`;
    };
  
    const formattedStart = formatDateTime(startTime);
    return formattedStart
  };


  export function formatDateTime(dateString) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}`;
  }


  export function formatOptions(optionsStr) {
    const optionLetters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    const optionsArray = optionsStr.split(';').filter(option => option.trim() !== '');
  
    return optionsArray.map((option, index) => {
      return `${optionLetters[index]}. ${option}`;
    });
  }

  export function getAnswerOption(optionsStr, answer) {
    const optionLetters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
  
  const formattedOptions = formatOptions(optionsStr)
  const answerArray= answer.split(';').filter(option => option.trim() !== '')
  console.log(answerArray)
  // 取得答案对应的选项

  for (let i = 0; i < answerArray.length ; i++) {
    answerArray[i]=optionLetters.indexOf(answerArray[i])
  }

  const answerOptions = [];
    for (let i = 0; i < answerArray.length; i++) {
        const index = answerArray[i];

        if (index !== -1 && index < formattedOptions.length) {
            answerOptions.push(formattedOptions[index]);
        }
    }
  return answerOptions.join("");
  }
  

  export function getBoolAnswer(answer) {
    if (answer=='1')return "正确"
    return "错误"
  }