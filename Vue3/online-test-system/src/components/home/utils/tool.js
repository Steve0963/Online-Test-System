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