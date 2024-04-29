<template>

    <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login_form">
        <!-- 用户名 -->
        <!-- prop="username"指定特定的校验规则 -->
        <el-form-item prop="username" class="formitem">
            <!-- 这里使用prefix-icon添加前置图标,如果官方的图标库没有,可以使用阿里图标库 -->
            用户名：<el-input v-model="loginForm.username" ></el-input>
        </el-form-item>
        <el-form-item prop="account" class="formitem">
            账户:<el-input v-model="loginForm.account"  :disabled="true"></el-input>
        </el-form-item>
        <el-form-item prop="password" class="formitem">
            密码:<el-input v-model="loginForm.password"  type="text"></el-input>
        </el-form-item>
        <el-form-item prop="telephone" class="formitem">
            电话:<el-input v-model="loginForm.telephone"  type="text"></el-input>
        </el-form-item>
        <el-form-item prop="birthday" class="formitem">
            出生日期:<el-input v-model="loginForm.birthday"  type="date"></el-input>
        </el-form-item>
        <el-form-item prop="email" class="formitem">
            邮箱:<el-input v-model="loginForm.email"  type="text"></el-input>
        </el-form-item><br><br>
        <!-- 按钮区域 -->
        <el-form-item class="btns">
            <el-button type="primary" @click="changes()">修改</el-button>
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
                    email:'',
                    userid:''
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
            cancel(){
                this.loginForm.username=window.sessionStorage.getItem('nickname')
                this.loginForm.account=window.sessionStorage.getItem('username')
                this.loginForm.password=window.sessionStorage.getItem('password')
                this.loginForm.telephone=window.sessionStorage.getItem('telphone')
                this.loginForm.birthday=window.sessionStorage.getItem('birthday')
                this.loginForm.email=window.sessionStorage.getItem('email')
            },
            changes(){
                let that=this;

                that.loginForm.userid=window.sessionStorage.getItem('userid')
                $.ajax({
                    url:"http://localhost:9999/Trip/updateuserinfo?username="+this.loginForm.username+"&userid="+this.loginForm.userid
                        +"&password="+this.loginForm.password+"&telephone="+this.loginForm.telephone+"&birthday="+this.loginForm.birthday+"&email="+this.loginForm.email,
                    type:"Post",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {
                        that.$message.success('修改成功')

                        location.reload();
                        //that.$router.push('/')
                    },
                })
            },
            regis(){
                let that=this;

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
        },
        created() {
            var that = this;
            this.loginForm.userid=window.sessionStorage.getItem('userid')
            $.ajax({
                url: "http://localhost:9999/Trip/userInfo?用户id=" + that.loginForm.userid,
                type: "Get",
                ContentType: "application/json;charset=utf-8",
                dataType: "json",
                async: "false",
                success: function (res) {
                    // console.log(res.data[0]['密码'])

                    that.loginForm.password=res.data[0]['密码']
                    that.loginForm.birthday=res.data[0]['出生日期']
                    that.loginForm.telephone=res.data[0]['电话']
                    that.loginForm.email=res.data[0]['邮箱']
                    that.loginForm.username=res.data[0]['用户名']
                    that.loginForm.account=res.data[0]['账户']
                },
            })
        }
    }
</script>

<style scoped>

    .formitem{
        width: 370px;
        height: 65px;
        margin-left: 40px;
        float:left;
        /*margin-top: 10px;*/
    }
    .btns {
        margin-top: 20px;
        margin-left: 340px;
        text-align: center;
        float: left;
    }
    .login_form {
        width: 870px;
        height: 370px;
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