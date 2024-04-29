<template>
    <div id="sr">
        <div v-if="!showEdit">
            账户：<el-input type="text" v-model="userbyname" placeholder="请输入账户" style="width: 200px"></el-input>
            用户名:<el-input type="text" v-model="nickname" placeholder="请输入用户名" style="width: 200px"></el-input>&nbsp
            <el-button type="primary" icon="el-icon-search" size="mini" @click="searchs()"></el-button>&nbsp
            <!--        <input type="button" @click="reSearch" value="取消">-->
            <el-button type="primary" @click="reSearch()" size="mini" icon="el-icon-delete"></el-button>

        </div>
        <p v-if="showEdit">

            身份编号：<el-input type="text" v-model="identitys" placeholder="请输入身份编号" style="width: 200px"></el-input>&nbsp
            用户名: <el-input type="text" v-model="nickname" placeholder="请输入用户名" style="width: 200px"></el-input>
            账户： <el-input type="text" v-model="username" placeholder="请输入账户" style="width: 200px"></el-input>
            密码：<el-input type="text" v-model="pwd" placeholder="请输入密码" style="width: 200px"></el-input><br><br>
            &nbsp&nbsp&nbsp&nbsp&nbsp电话：<el-input type="text" v-model="tel" placeholder="请输入电话" style="width: 200px"></el-input>
            出生日期: <el-input type="date" v-model="birth" placeholder="请输入出生日期" style="width: 200px"></el-input>
            邮箱：<el-input type="text" v-model="emails" placeholder="请输入邮箱" style="width: 200px"></el-input>
            <el-button type="primary" @click="sureUpdata()"  style="margin-left: 100px" size="mini">提交</el-button>
            <el-button type="info" size="mini" @click="returns()">取消</el-button>
        </p>
<!--        &lt;!&ndash; 将获取到的数据进行计算 &ndash;&gt;-->
        <el-table :data="FilteDate.slice((currentPage-1)*PageSize,currentPage*PageSize)"   style="width: 100%">

            <el-table-column prop="用户id" label="用户id" ></el-table-column>
            <el-table-column prop="身份编号" label="身份编号" ></el-table-column>
            <el-table-column prop="用户名" label="用户名"></el-table-column>
            <el-table-column prop="账户" label="账户"></el-table-column>
            <el-table-column prop="密码" label="密码"></el-table-column>
            <el-table-column prop="电话" label="电话"></el-table-column>
            <el-table-column prop="出生日期" label="出生日期"></el-table-column>
            <el-table-column prop="邮箱" label="邮箱"></el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    width="100">
                <template slot-scope="scope">
                    <el-button @click="updatas(scope.row)" type="text" size="small">修改</el-button>
                    <el-button @click="open(scope.row)" type="text" size="small">删除</el-button>
                </template>
            </el-table-column>

        </el-table>
        <div class="tabListPage">
            <el-pagination @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
                           :current-page="currentPage"
                           :page-sizes="pageSizes"
                           :page-size="PageSize" layout="total, sizes, prev, pager, next, jumper"
                           :total="total.length">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import $ from 'jquery'
    import vue from 'vue'
    export default {
        name: "usersearch",
        data(){
            return{
                userbyname: null,
                nickname: null,
                username:'',
                identitys: "",
                emails:'',
                pwd:"",
                tel:"",
                userid:"",
                birth:"",
                showEdit:false,
                total:[],
                options:[""],
                level:'',
                // 默认显示第几页
                currentPage:1,
                // 总条数，根据接口获取数据长度(注意：这里不能为空)
                totalCount:50,
                // 个数选择器（可修改）
                pageSizes:[5,10,15,25],
                // 默认每页显示的条数（可修改）
                PageSize:5

            }

        },
        computed:{
            FilteDate:function () {

                var that=this
                return this.total.filter(function (item) {

                    return item
                })
            }
        },
        methods:{
// 分页
            sureUpdata(){
                var that=this
                $.ajax({
                    url:"http://localhost:9999/Trip/updateuserinfo2?username="+this.username+"&userid="+this.userid
                        +"&password="+this.pwd+"&telephone="+that.tel+"&birthday="+this.birth+"&email="+this.emails+"&indentity="+this.identitys,
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

            reSearch(){
                let that=this;
                $.ajax({
                    url:"http://localhost:9999/Trip/userinfo",
                    type:"Get",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {

                        console.log(res.data)
                        that.total=res.data
                        that.totalCount=res.data.length
                        that.userbyname=null
                        that.nickname=null
                        // that.level=window.sessionStorage.getItem('level')
                    },
                })
            },
            searchs(){
                let that=this;
                if (that.userbyname==null){
                    $.ajax({
                        url:"http://localhost:9999/Trip/userinfobynickname?nickname="+that.nickname,
                        type:"Get",
                        ContentType:"application/json;charset=utf-8",
                        dataType:"json",
                        async:"false",
                        success:function (res) {


                            that.total=res.data
                            that.totalCount=res.data.length
                            // that.level=window.sessionStorage.getItem('level')
                        },
                    })
                }else if (that.nickname==null){
                    $.ajax({
                        url:"http://localhost:9999/Trip/userinfobyusername?username="+that.userbyname,
                        type:"Get",
                        ContentType:"application/json;charset=utf-8",
                        dataType:"json",
                        async:"false",
                        success:function (res) {


                            that.total=res.data
                            that.totalCount=res.data.length
                            // that.level=window.sessionStorage.getItem('level')
                        },
                    })
                }else{
                    $.ajax({
                        url:"http://localhost:9999/Trip/userinfobyusernameandnickname?username="+that.userbyname+"&nickname="+that.nickname,
                        type:"Get",
                        ContentType:"application/json;charset=utf-8",
                        dataType:"json",
                        async:"false",
                        success:function (res) {


                            that.total=res.data
                            that.totalCount=res.data.length
                            // that.level=window.sessionStorage.getItem('level')
                        },
                    })
                }


            },
            open(index) {
                this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {

                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.del(index)

                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            del(index){
                let that=this;
                $.ajax({
                    url:"http://localhost:9999/Trip/deleteUser?userid="+index['用户id'],
                    type:"Delete",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {
                        new Promise((resolve, reject) => {
                            setTimeout(() => {
                                resolve("Promise resolved!");
                            }, 1000);
                        }).then(result => {
                            location.reload();
                        });
                    },
                })
            },
            returns(){
                this.showEdit=false
            },
            updatas(index){
                var that=this
                this.showEdit=true
                this.userid=index.用户id
                this.nickname=index.用户名
                this.identitys=index.身份编号
                this.username=index.账户
                this.pwd=index.密码
                this.tel=index.电话
                this.emails=index.邮箱
                this.birth=index.出生日期

            },
            // 每页显示的条数
            handleSizeChange(val) {
                // 改变每页显示的条数
                this.PageSize=val
                // 注意：在改变每页显示的条数时，要将页码显示到第一页
                this.currentPage=1
            },
            // 显示第几页
            handleCurrentChange(val) {
                // 改变默认的页数
                this.currentPage=val
            }
        },
        created() {
            let that=this;
            $.ajax({
                url:"http://localhost:9999/Trip/userinfo",
                type:"Get",
                ContentType:"application/json;charset=utf-8",
                dataType:"json",
                async:"false",
                success:function (res) {

                    console.log(res.data)
                    that.total=res.data
                    that.totalCount=res.data.length
                    // that.level=window.sessionStorage.getItem('level')
                },
            })
        }

    }
</script>

<style scoped>
    table{
        margin-top: 30px;

    }
    th {

        font: bold 11px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;

        color: #4f6b72;

        border-right: 1px solid #C1DAD7;

        border-bottom: 1px solid #C1DAD7;

        border-top: 1px solid #C1DAD7;

        letter-spacing: 2px;

        text-transform: uppercase;

        text-align: left;

        padding: 6px 6px 6px 12px;

        background: #CAE8EA no-repeat;

        width: 150px;
    }
    /*a {*/

    /*    color: #c75f3e;*/

    /*}*/
</style>