<template>
    <div class="head"><img src="https://i.hnust.edu.cn/portal-minio/cms/%E5%AF%BC%E8%88%AA%E6%A0%8Flogo%E5%AE%BD%E5%BA%A6%E4%B8%8D%E9%99%90_40_%E7%99%BD%E8%89%B2_1693445517724.png" >
    
    
        <span class="manager-header-right">
            <el-dropdown trigger="click">
                <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" style="height: 40px;width: 40px;"/>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item>个人信息</el-dropdown-item>
                    <el-dropdown-item>修改密码</el-dropdown-item>
                    <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
        </span>

        </div>
    
    
</template>

<script setup lang="ts">
import { ArrowDown } from '@element-plus/icons-vue'
import { getCookie, formatDateTime} from '../utils/tool'
import { useStore } from 'vuex';
import{useRouter} from 'vue-router'
import { ElMessage } from 'element-plus';
const router = useRouter();
const store = useStore();
const logout = () => {
  store.dispatch('logout');
  router.push('/');
  ElMessage({
    showClose: true,
    message: '退出成功',
    type: 'success',
  })
}
</script>

<style>
.head {
  display: flex;
  align-items: center;
  padding-top: 17px;
  padding-bottom: 17px;
}

.manager-header-right {
  margin-left: auto;
}
</style>