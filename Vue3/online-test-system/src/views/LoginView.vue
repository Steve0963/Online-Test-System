<script lang="ts" setup>
import { useRouter} from 'vue-router';
import { Ref, reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import type { TabsPaneContext } from 'element-plus'
import { User, Lock} from '@element-plus/icons-vue'
import { login,register } from '../requests/api'
import { useStore } from 'vuex';

const activeName = ref('login')
const loginFormRef = ref<FormInstance>()
const RegisterFormRef = ref<FormInstance>()
const router = useRouter();
const store = useStore();

const loginForm = reactive({
    account: '',
    password: '',
})

const RegisterForm = reactive({
    account: '',
    password: '',
    crt_pass:'',
})

const handleClick = (tab: TabsPaneContext, event: Event) => {
    console.log(tab, event)
}

const validateAccount = (rule: any, value: any, callback: any) => {
    if (!value) {
        return callback(new Error('请输入账号'));
    }

    const regExp = /^[a-zA-Z0-9]{6,16}$/;

    if (!regExp.test(value)) {
        callback(new Error('账号需为6到16位的数字和字母组合'));
    } else {
        callback();
    }
}

const validatePassword = (rule: any, value: any, callback: any) => {
    if (!value) {
        callback(new Error('请输入密码'));
    } else {
        const Exp = /^[a-zA-Z0-9!@#$%^&*]+$/;

        if (value.length < 6 || value.length > 16) {
            callback(new Error('密码需为6到16位'));
        } else if (!Exp.test(value)) {
            callback(new Error('只能包含以下字符:!@#$%^&*'));
        } else {
            callback();
        }
    }
}

const validateCrt_pass = (rule: any, value: any, callback: any) => {
    if (RegisterForm.crt_pass!=RegisterForm.password){

        callback(new Error('两次输入不一致'));
    }
    else{callback();}
}




const login_rules = reactive<FormRules<typeof loginForm>>({
    account: [{ validator: validateAccount, trigger: 'blur' }],
    password: [{ validator: validatePassword, trigger: 'change' }],
})

const register_rules = reactive<FormRules<typeof RegisterForm>>({
    account: [{ validator: validateAccount, trigger: 'blur' }],
    password: [{ validator: validatePassword, trigger: 'change' }],
    crt_pass:[{ validator: validateCrt_pass, trigger: 'change' }]
})

const submitLogin = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            console.log('登录')
            const result=await (await login(loginForm)).data.data
            console.log(result)

            if(result != null) {
                store.dispatch('login', { role: result.role, account: result.account,name:result.name });
                console.log( store.state)
          switch(result.role) {
            case 0:  //管理员
            
              console.log("跳转")
              router.push('/home');
              
              break
            case 1: //教师
            
              console.log("跳转")
              router.push('/home/teacher/');
              break
            case 2: //学生
            
              
              console.log("跳转")
              router.push('/home/student/test');
              break
          }
        }

        if(result == null) { //错误提示
          
        }
            
        } else {
            console.log('error submit!')
            return false
        }
    })
}

const submitRegister = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate((valid) => {
        if (valid) {
            console.log('注册')
            register(RegisterForm)

        } else {
            console.log('error submit!')
            return false
        }
    })
}
</script>



<template>
    <div class="login-body">
        <el-container>

            <el-header style="height: 250px;">
                <img src="../assets/png/logo.png" style="width:350px">
            </el-header>

            <el-main>

                <el-card style="width: 380px;" shadow="hover">

                    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">

                        <el-tab-pane label="登录" name="login" class="custom-tab">
                            <el-form ref="loginFormRef" :model="loginForm" status-icon :rules="login_rules" class="loginForm">

                                <el-form-item prop="account">
                                    <el-input v-model="loginForm.account" placeholder="请输入账号" :prefix-icon="User" />
                                </el-form-item>

                                <el-form-item prop="password">
                                    <el-input v-model="loginForm.password" type="password" placeholder="输入密码"
                                        :prefix-icon="Lock" show-password />
                                </el-form-item>

                                <el-form-item>
                                    <el-button type="primary" @click="submitLogin(loginFormRef)">登录</el-button>
                                </el-form-item>
                            </el-form>
                        </el-tab-pane>

                        <el-tab-pane label="注册" name="register" class="custom-tab">
                            <el-form ref="RegisterFormRef" :model="RegisterForm" status-icon :rules="register_rules" label-width="auto"
                                class="demo-loginForm">

                                <el-form-item prop="account">
                                    <el-input v-model="RegisterForm.account" placeholder="请输入账号"
                                        :prefix-icon="User" />
                                </el-form-item>

                                <el-form-item prop="password">
                                    <el-input v-model="RegisterForm.password" type="password" placeholder="输入密码"
                                        autocomplete="off" :prefix-icon="Lock" show-password/>
                                </el-form-item>

                                <el-form-item prop="crt_pass">
                                    <el-input v-model="RegisterForm.crt_pass" type="password" placeholder="再次输入"
                                        autocomplete="off" :prefix-icon="Lock" show-password/>
                                </el-form-item>


                                <el-form-item>
                                    <el-button type="primary" @click="submitRegister(RegisterFormRef)">注册</el-button>
                                </el-form-item>
                            </el-form>
                        </el-tab-pane>
                    </el-tabs>
                </el-card>


            </el-main>
        </el-container>
    </div>

</template>



<style scoped>
.login-body {
    height: 100vh;
    background-image: url('src/assets/png/background.png');
}

.el-header {
    display: grid;
    place-items: center;
}

.el-container {
    display: grid;
    place-items: center;
}

:deep(.el-input-group__append, .el-input-group__append) {
    padding: 0;
}

:deep(.custom-tab) {
    font-size: xx-large;
    color: aliceblue;
}
</style>