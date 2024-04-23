

import { createStore } from 'vuex';
import { cookiePlugin } from './cookiePlugin';
export default createStore({
  state: {
    isLoggedIn: false,
    token: '',
    account: null,
    name: null,
    pic: null,
    role: null,
    id:null,
  },
  mutations: {
    login(state, { role, account, name,id }) {
      state.isLoggedIn = true;
      state.role = role;
      state.account = account;
      state.name = name;
      state.id = id;
    },
    logout(state) {
      state.isLoggedIn = false;
      //state.token = '';
      state.account = null;
      state.name = null;
      state.role = null;
      state.id = null;

      //state.pic=null;
    },
  },

  actions: {
    login({ commit }, { role, account, name,id }) {
      commit('login', { role, account, name ,id});
    },
    logout({ commit }) {
      commit('logout');
    },
  },
  modules: {
  },
  plugins: [cookiePlugin],
  getters: {
    // 获取数据
  },


});