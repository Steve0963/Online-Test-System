import axios from 'axios';
const baseUrl = 'http://localhost:8080';

export const login = (formData) => {
  const url = `${baseUrl}/login`;
console.log(formData)
  return axios.post(url, formData)
    .then(response => {
      return response
    })
    .catch(error => {
      console.error('Error submitting form:', error);
      throw error;
    });
};

export const register = (formData) => {
    const url = `${baseUrl}/register`;
  
    return axios.post(url, formData)
      .then(response => response.data)
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };


  export const creatClass = (formData) => {
    const url = `${baseUrl}/teacher/creatClass`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };

  export const loadClass = (formData) => {
    const url = `${baseUrl}/teacher/loadClass`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };


  export const joinClass = async (formData) => {
    const url = `${baseUrl}/joinClass`;
  console.log(formData)
    try {
      const response = await axios.post(url, formData);
      return response;
    } catch (error) {
      console.error('Error submitting form:', error);
      throw error;
    }
  };

  export const myClass = (formData) => {
    const url = `${baseUrl}/myClass`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };

  export const myExam = (formData) => {
    const url = `${baseUrl}/myExam`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };


  export const myScore = (formData) => {
    const url = `${baseUrl}/myScore`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };

  export const examList = (formData) => {
    const url = `${baseUrl}/teacher/examList`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };


  export const paperList = (formData) => {
    const url = `${baseUrl}/teacher/paperList`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };

  export const studentList = (formData) => {
    const url = `${baseUrl}/teacher/studentList`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };

  export const problemList = (formData) => {
    const url = `${baseUrl}/teacher/problemList`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };

  export const paperProblem = (formData) => {
    const url = `${baseUrl}/teacher/paperProblem`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };

  export const studentPaperProblem = (formData) => {
    const url = `${baseUrl}/myPaperProblem`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };


  export const savePaperProblem = (formData) => {
    const url = `${baseUrl}/teacher/savePaperProblem`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };


  export const removeStudent = (formData) => {
    const url = `${baseUrl}/teacher/removeStudent`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };


  
  export const deleteClassById = (formData) => {
    const url = `${baseUrl}/teacher/deleteClass`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };


  export const saveExam= (formData) => {
    const url = `${baseUrl}/teacher/saveExam`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };

  export const deleteExam= (formData) => {
    const url = `${baseUrl}/teacher/deleteExam`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };


  export const deletePaper= (formData) => {
    const url = `${baseUrl}/teacher/deletePaper`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };


  export const submitPaperAnsewr= (formData) => {
    const url = `${baseUrl}/submitPaperAnswer`;
  console.log(formData)
    return axios.post(url, formData)
      .then(response => {
        return response
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        throw error;
      });
  };