<script lang="ts" setup>
import { Ref, reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import type { TabsPaneContext } from 'element-plus'
import { User, Lock, MessageBox } from '@element-plus/icons-vue'
import Authenticator from '../components/vertify/Authenticator.vue'

import { login } from '../requests/api'
const identifyCode = ref();
const identifyCodes = ref("1234567890abcdefjhijklinopqrsduvwxyz");


// 重置验证码
const refreshCode = () => {
    identifyCode.value = "";
    makeCode(identifyCode, 4);
};

const makeCode = (o: Ref<any>, l: number) => {
    for (let i = 0; i < l; i++) {
        identifyCode.value += identifyCodes.value[randomNum(0, identifyCodes.value.length)];
    }
};


const randomNum = (min: number, max: number) => {
    return Math.floor(Math.random() * (max - min) + min);
};

const regrefreshCode = () => {
    identifyCode.value = "";
    makeCode(identifyCode, 4);
};


const activeName = ref('login')

const handleClick = (tab: TabsPaneContext, event: Event) => {
    console.log(tab, event)
}
const loginFormRef = ref<FormInstance>()

const validateAccount = (rule: any, value: any, callback: any) => {
    console.log(value)
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
const validateCode = (rule: any, value: any, callback: any) => {
    console.log(identifyCode.value)
    console.log(value)
    if (!value) {
        callback(new Error('请输入验证码'));
    } else {
        if (value!==identifyCode.value) {
            callback(new Error('验证码错误！'));
        }  else {
            callback();
        }
    }
    
}

const loginForm = reactive({
    account: '',
    password: '',
    code: '',
})

const rules = reactive<FormRules<typeof loginForm>>({
    account: [{ validator: validateAccount, trigger: 'blur' }],
    password: [{ validator: validatePassword, trigger: 'change' }],
    code: [{ validator: validateCode, trigger: 'blur' }],
})

const submitForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate((valid) => {
        if (valid) {
            console.log('submit!')
           login(loginForm)
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
                            <el-form ref="loginFormRef" :model="loginForm" status-icon :rules="rules" class="loginForm">

                                <el-form-item prop="account">
                                    <el-input v-model="loginForm.account" placeholder="请输入账号" :prefix-icon="User" />
                                </el-form-item>

                                <el-form-item prop="password">
                                    <el-input v-model="loginForm.password" type="password" placeholder="输入密码"
                                        :prefix-icon="Lock" show-password />
                                </el-form-item>

                                <el-form-item prop="code">
                                    <el-input v-model="loginForm.code" placeholder="输入验证码" :prefix-icon="MessageBox"
                                        autocomplete="off">
                                        <template v-slot:append>
                                            <Authenticator :identifyCode="identifyCode" @click="refreshCode" />
                                        </template>
                                    </el-input>
                                </el-form-item>

                                <el-form-item>
                                    <el-button type="primary" @click="submitForm(loginFormRef)">登录</el-button>
                                </el-form-item>
                            </el-form>
                        </el-tab-pane>

                        <el-tab-pane label="注册" name="register" class="custom-tab">
                            <el-form ref="loginFormRef" :model="loginForm" status-icon :rules="rules" label-width="auto"
                                class="demo-loginForm">

                                <el-form-item prop="age">
                                    <el-input v-model.number="loginForm.password" placeholder="请输入账号"
                                        :prefix-icon="User" />
                                </el-form-item>

                                <el-form-item prop="pass">
                                    <el-input v-model="loginForm.password" type="password" placeholder="输入密码"
                                        autocomplete="off" :prefix-icon="Lock" />
                                </el-form-item>

                                <el-form-item prop="checkPass">
                                    <el-input v-model="loginForm.password" type="password" placeholder="再次输入"
                                        autocomplete="off" :prefix-icon="Lock" />
                                </el-form-item>

                                <el-form-item prop="checkPass">
                                    <el-input v-model="loginForm.code" placeholder="输入验证码" autocomplete="off"
                                        :prefix-icon="MessageBox">

                                        <template v-slot:append>
                                            <Authenticator :identifyCode="identifyCode" @click="regrefreshCode" />
                                        </template>
                                    </el-input>
                                </el-form-item>

                                <el-form-item>
                                    <el-button type="primary" @click="submitForm(loginFormRef)">注册</el-button>
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
</style>../components/vertify/Authenticator.vue