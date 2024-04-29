<template>
    <div class="login_container" >
        <div class="login_box">
            <!-- 头像区域 -->
            <div class="avatar_box">
                <img class="photos" src="../assets/icon/橙韵晚霞.png" alt="">
            </div>
            <!-- 登录表单区域 -->
            <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login_form">
                <!-- 用户名 -->
                <!-- prop="username"指定特定的校验规则 -->
                <el-form-item prop="username">
                    <!-- 这里使用prefix-icon添加前置图标,如果官方的图标库没有,可以使用阿里图标库 -->
                    <el-input v-model="loginForm.username" prefix-icon="iconfont icon-user">
                        <i slot="prefix" class="el-input__icon el-icon-user"></i>
                    </el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="password">

                    <el-input v-model="loginForm.password" prefix-icon="" type="password">
                        <i slot="prefix" class="el-input__icon el-icon-lock"></i>
                    </el-input>
                </el-form-item>
                <!-- 按钮区域 -->
                <el-form-item class="btns">
                    <el-button type="primary" @click="login">登录</el-button>
                    <el-button type="info" @click="toregister">注册</el-button>
                </el-form-item>

            </el-form>
        </div>
    </div>
</template>

<script>
import $ from 'jquery'
export default {
  data() {
    return {
      loginForm: {
        username: 'admin001',
        password: 'admin001'
      },
      // 表单验证规则对象
      loginFormRules: {
        username: [
          { required: true, message: '请输入登录名称', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 2, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    resetLoginForm() {
      // 拿到登录表单的ref,然后调用它的resetFields方法
      this.$refs.loginFormRef.resetFields()
    },
      toregister(){
      this.$router.push('/register')
      },
      login(){
          let that=this;
          $.ajax({
              url:"http://localhost:9999/Trip/login?password="+this.loginForm.password+"&username="+this.loginForm.username.toString(),
              type:"Get",
              ContentType:"application/json;charset=utf-8",
              dataType:"json",
              async:"false",
              success:function (res) {
                  if(res.data.length!=0 && res.code!=500){

                          console.log(res.code)
                      console.log(res.data)
                          that.$message.success('登录成功')
                          window.sessionStorage.setItem('username',res.data[0]['账户'])
                          window.sessionStorage.setItem('password',res.data[0]['密码'])
                          window.sessionStorage.setItem('level',res.data[0]['身份编号'])
                          window.sessionStorage.setItem('nickname',res.data[0]['用户名'])
                            window.sessionStorage.setItem('userid',res.data[0]['用户id'])
                      window.sessionStorage.setItem('telphone',res.data[0]['电话'])
                      window.sessionStorage.setItem('birthday',res.data[0]['出生日期'])
                      window.sessionStorage.setItem('email',res.data[0]['邮箱'])
                          that.$router.push('/home')



                  }else{
                      that.$message.error('账号或密码错误')
                  }

              },
          })
      }
    }
  }
</script>

<style lang="less" scoped>
/*.login_container {*/
/*    background-color: #2b4b6b;*/
/*    height: 100%;*/
/*    position: relative;*/

/*}*/
.photos{
  background-size: auto;
}
.login_box {
    width: 450px;
    height: 300px;
    background-color: rgba(100,100,150,0.5);
    border-radius: 3px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%,-50%);

    .avatar_box {
        height: 130px;
        width: 130px;
        border: 1px solid #eee;
        border-radius: 50%;
        padding: 10px;
        box-shadow: 0 0 10px #ddd;
        position: absolute;
        left: 50%;
        transform: translate(-50%, -50%);
        background-color: #fff;
        img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
            background-color: #eee;
        }
    }
}

.login_form {
    position: absolute;
    bottom: 0;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
}

.btns {
    display: flex;
    // 设置主轴对齐方式:从尾部开始排列
    justify-content: flex-end;
}

</style>
