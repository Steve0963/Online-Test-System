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
    const url = `${baseUrl}/creatClass`;
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
    const url = `${baseUrl}/loadClass`;
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