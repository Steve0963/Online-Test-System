

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
    currentPage:null
  },
  mutations: {
    login(state, { role, account, name }) {
      state.isLoggedIn = true;
      state.role = role;
      state.account = account;
      state.name = name;
      //state.pic=pic;
    },
    logout(state) {
      state.isLoggedIn = false;
      //state.token = '';
      state.account = null;
      state.name = null;
      state.role = null;

      //state.pic=null;
    },
    setCurrentPage(state, page) {
      state.currentPage = page;
    },

  },

  actions: {
    login({ commit }, { role, account, name }) {
      commit('login', { role, account, name });
    },
    logout({ commit }) {
      commit('logout');
    }
  },
  modules: {
  },
  plugins: [cookiePlugin],
});