export function  tagFilterOptions () {
  const options=[
    { text: '优', value: '优' },
    { text: '良', value: '良' },
    { text: '中', value: '中' },
    { text: '差', value: '差' },
    { text: '不及格', value: '不及格' },
  ]
  return options
}

export function timeFilterOptions  ()  {
    const options = [
     { text: '2016-05-01', value: '2016-05-01' },
     { text: '2016-05-02', value: '2016-05-02' },
     { text: '2016-05-03', value: '2016-05-03' },
     { text: '2016-05-04', value: '2016-05-04' },
   ]
   return options
 }

 export function getTagType  (score:number): string  {
    if (score >= 90) return 'success'
    else if (score >= 80) return 'primary'
    else if (score >= 70) return 'info'
    else if (score >= 60) return 'warning'
    else return 'danger'
  }
  
  export function getTag  (score:number): string  {
    if (score >= 90) return '优'
    else if (score >= 80) return '良'
    else if (score >= 70) return '中'
    else if (score >= 60) return '差'
    else return '不及格'
  }