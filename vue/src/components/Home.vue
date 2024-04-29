<template>
<!-- 总区域 -->
  <el-container class="home-container">
    <!-- 头部区域 -->
    <el-header>
      <div>
        <img src="../assets/icon/数据分析.png" style="width: 63px;height: 60px" @click="welcome()" alt="" >
        <span>旅游数据可视化系统</span>
      </div>
      <el-button type="info" @click="logout">退出</el-button>
    </el-header>
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="isCollapse ? '64px' : '140px'">
        <div class="toggle-button" @click="toggleCollapse">|||</div>
        <!-- 侧边栏菜单区域 -->
        <!-- 对于数值和布尔值，如果加上冒号就是v-bind绑定，会保留原来的类型（数值或者布尔值），如果不加冒号就是字符串 -->
        <!-- 所以下面可以写成:unique-opened="true",也可以简写成unique-opened -->
        <!-- 如果写成unique-opened="aaa",因为不是属性绑定,那么就是字符串"aaa",字符串的bool值也是真,所以这样写也生效,但是是错误的 -->
        <!-- :collapse-transition="false"关闭折叠动画 -->
        <!-- router开启路由模式 -->
        <!-- :default-active="activePath"被激活的地址 -->
        <el-menu background-color="#333744"
          text-color="#fff"
          active-text-color="#409EFF" unique-opened :collapse="isCollapse" :collapse-transition="false" router :default-active="activePath">
          <!-- 一级菜单 -->
          <!-- 绑定唯一的index,是字符串,index留着展开和折叠使用 -->
          <el-submenu :index="item.path + ''" v-for="item in menulist" :key="item.id">
            <!-- 一级菜单模板区域 -->
            <template #title>
              <!-- 一级图标 -->
              <i :class="iconsObj[item.id]"></i>
              <!-- 一级文本 -->
              <span>{{item.authName}}</span>
            </template>
            <!-- 二级菜单 -->
            <!-- 如果开启了路由模式,index可以当做跳转的地址 -->
            <el-menu-item :index="subItem.path"  v-for="subItem in item.children" :key="subItem.id" @click="saveNavState(subItem.path)">
              <!-- 二级菜单模板区域 -->
              <template #title >
                <!-- 二级图标 -->
                <i class="el-icon-menu"></i>
                <!-- 二级文本 -->
                <span>{{subItem.authName}}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!-- 正文区域 -->
      <el-main>
        <!-- 路由占位符 -->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import $ from "jquery";

export default {
  data() {
    return {
      menulist: [],
      // 因为我们在main.js里面导入了字体图标和全局样式表了,所以在这里可以直接使用
      iconsObj: {
        125: 'iconfont icon-user',
        103: 'iconfont icon-tijikongjian',
        101: 'iconfont icon-shangpin',
        102: 'iconfont icon-danju',
        145: 'iconfont icon-baobiao'
      },
      // 是否折叠
      isCollapse: false,
      // 被激活的链接地址
      activePath: ''
    }
  },
  // 在实例创建完成后被立即调用，此时data 和 methods已经可以使用，但是页面还没有渲染出来
  created() {
    let that=this;
    $.ajax({
      url:"http://localhost:9999/Trip/home?password="+window.sessionStorage.getItem('password')+"&username="+window.sessionStorage.getItem('username'),
      type:"Get",
      ContentType:"application/json;charset=utf-8",
      dataType:"json",
      async:"false",
      success:function (res) {

        var res2={
          "data":[]
        }

        const rest= res.data.filter(item=>item.权限归属层级===1);

        rest.forEach(
                (item,index)=>{
                  var second=[]
                  const rest2= res.data.filter(item2=>item2.权限归属层级===2 && item2.父权限编号===item.权限编号);

                  rest2.forEach(
                          (sitem,sindex)=>{
                            var obj2={

                              'id':sitem.权限编号,
                              'authName':sitem.权限显示名称,
                              'path':sitem.Url地址,
                              'children':[],
                              'order':sitem.显示位置排序
                            }
                            second.push(obj2)
                          },

                  )


                  var obj={
                    'id':item.权限编号,
                    'authName':item.权限显示名称,
                    'path':item.显示位置排序,
                    'children':[],
                    'order':item.显示位置排序
                  }
                  obj.children=second
                  res2.data.push(obj)
                }

        )

        that.menulist=res2.data
      }
    })
  },
  methods: {
    logout () {
      window.sessionStorage.clear()
      this.$router.push('/')
      this.$message.success('退出登录成功')
    },

    // 点击按钮,折叠和展开
    toggleCollapse() {
      this.isCollapse = !this.isCollapse
    },
    // 点击二级菜单,保存点击的地址值,然后在组件被创建的时候再取出来
    saveNavState(activePath) {
      window.sessionStorage.setItem('activePath', activePath)
      this.activePath = activePath
    },
    welcome(){
      this.$router.push('/welcome')
    }
  }
}
</script>

<style lang="less" scoped>
// element-ui里面元素的名字同时也是类名,所以可以使用.
.el-header {
  background-color: #373d41;
  display: flex;
  // 左右贴边对齐
  justify-content: space-between;
  padding-left: 0;
  // 设置侧轴上的子元素排列方式（单行）
  align-items: center;
  color: #fff;
  font-size: 20px;
  > div { //亲儿子选择器
    display: flex;
    align-items: center;
    span {
      margin-left: 15px;
    }
  }
}

.el-aside {
  background-color: #333744;
  .el-menu {
    border-right: none;
  }
}

.el-main {
  background-color: #EAEDF1;
}

.home-container {
  height: 100%;
}

.iconfont {
  margin-right: 10px;
}

.toggle-button {
  background-color: #4A5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.5em;
  cursor: pointer;
}

</style>
