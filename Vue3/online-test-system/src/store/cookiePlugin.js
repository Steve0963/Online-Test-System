
export const cookiePlugin= store => {
    // 监听 mutation 的变化
    store.subscribe((mutation, state) => {
      // 在 mutation 中存储数据到 Cookie 中
      switch (mutation.type) {
        case 'login':
          // 设置登录状态到 Cookie,过期时间为 7 天
          setCookie('isLoggedIn', state.isLoggedIn, 7); 
          setCookie('account', state.account, 7); 
          setCookie('name', state.name, 7); 
          setCookie('role', state.role, 7);
          setCookie('id', state.id, 7);
          break;
        case 'logout':
            setCookie('isLoggedIn', state.isLoggedIn, 7); 
            setCookie('account', state.account, 7); 
            setCookie('name', state.name, 7); 
            setCookie('role', state.role, 7);
            setCookie('id', state.id, 7);// 设置用户账号在 Cookie 中的过期时间为 7 天
            break;
        
      }
    });
  };
  
  // 设置 Cookie 的函数
  function setCookie(name, value, days) {
    const date = new Date();
    date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
    const expires = "expires=" + date.toUTCString();
    document.cookie = name + "=" + value + ";" + expires + ";path=/";
  }