<template>

    <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login_form">
        <!-- 用户名 -->
        <!-- prop="username"指定特定的校验规则 -->
        <i class="el-icon-circle-close" @click="returns()"></i>
        <el-form-item prop="username" class="formitem">
            <!-- 这里使用prefix-icon添加前置图标,如果官方的图标库没有,可以使用阿里图标库 -->
            用户名：<el-input v-model="loginForm.username" ></el-input>

        </el-form-item>
        <el-form-item prop="account" class="formitem">
            账户:<el-input v-model="loginForm.account" ></el-input>
        </el-form-item>
        <el-form-item prop="password" class="formitem">
            密码:<el-input v-model="loginForm.password"  type="password"></el-input>
        </el-form-item>
        <el-form-item prop="passwordagain" class="formitem">
            再次输入密码:<el-input v-model="loginForm.passwordagain" type="password"></el-input>
        </el-form-item>
        <el-form-item prop="telephone" class="formitem">
            电话:<el-input v-model="loginForm.telephone"  type="text"></el-input>
        </el-form-item>
        <el-form-item prop="birthday" class="formitem">
            出生日期:<el-input v-model="loginForm.birthday"  type="date"></el-input>
        </el-form-item>
        <el-form-item prop="email" class="formitem">
            邮箱:<el-input v-model="loginForm.email"  type="text"></el-input>
        </el-form-item>
        <!-- 按钮区域 -->
        <el-form-item class="btns">
            <el-button type="primary" @click="regis()">注册</el-button>
            <el-button type="info" @click="cancel()">重置</el-button>
        </el-form-item>

    </el-form>
</template>

<script>
    import  echarts from 'echarts'
    import $ from "jquery";
    export default {
        name: "Register",
        data() {
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.loginForm.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                loginForm: {
                    username: '',
                    password: '',
                    account:'',
                    passwordagain:'',
                    telephone:'',
                    birthday:'',
                    email:''
                },
                // 表单验证规则对象
                loginFormRules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 3, max: 10, message: '用户名长度在 3 到 10 个字符', trigger: 'blur' }
                    ],
                    account: [
                        { required: true, message: '请输入账户', trigger: 'blur' },
                        { min: 3, max: 10, message: '账户长度在 3 到 10 个字符', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min: 6, max: 15, message: '密码长度在 6 到 15 个字符', trigger: 'blur' }
                    ],
                    passwordagain: [
                        // { required: true, message: '请再度输入密码', trigger: 'blur' },
                        { min: 6, max: 15, message: '再度输入密码长度在 6 到 15 个字符', trigger: 'blur' },
                        { validator: validatePass2, trigger: 'blur' }
                    ]
                }
            }
        },
        methods:{
            returns(){
                this.$router.push('/')
            },
            cancel(){
                this.loginForm.username=''
                this.loginForm.account=''
                this.loginForm.password=''
                this.loginForm.passwordagain=''
                this.loginForm.telephone=''
                this.loginForm.birthday=''
                this.loginForm.email=''
            },
            regis(){
                let that=this;
                if(this.loginForm.username==''){
                    that.$message.error('用户名不能为空')
                    return;
                }
                if(this.loginForm.account==''){
                    that.$message.error('账户不能为空')
                    return;
                }
                if(this.loginForm.password==''){
                    that.$message.error('密码不能为空')
                    return;
                }
                if(this.loginForm.passwordagain==''){
                    that.$message.error('再次输入密码不能为空')
                    return;
                }

                $.ajax({
                    url:"http://localhost:9999/Trip/adduserinfo?username="+this.loginForm.username+"&account="+this.loginForm.account
                        +"&password="+this.loginForm.password+"&telephone="+this.loginForm.telephone+"&birthday="+this.loginForm.birthday+"&email="+this.loginForm.email,
                    type:"Post",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {
                        that.$message.success('添加成功')
                        //location.reload();
                        that.$router.push('/')
                    },
                })
            }
        }
    }
</script>

<style scoped>

    .formitem{
        width: 400px;
        height: 65px;
        margin-left: 40px;
        /*margin-top: 10px;*/
    }
    .el-icon-circle-close{
        float: right;
        /*width: 50px;*/
        /*height: 50px;*/
        font-size: 35px;
        margin-top: 5px;
        margin-right: 5px;
    }
    .btns {
        margin-top: 30px;
        text-align: center;
    }
    .login_form {
        width: 500px;
        height: 670px;
        background-color: skyblue;
        /*border-radius: 3px;*/
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%,-50%);
        border: 1px solid black;
        border-radius: 10px;
        opacity: .9;
    }
</style>