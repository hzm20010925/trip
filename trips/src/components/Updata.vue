<template>
    <div id="sr">
        <div v-if="!showEdit">
            书名：<input type="text" v-model="bookName" placeholder="请输入书名">
            作者：<input type="text" v-model="writer" placeholder="请输入作者">&nbsp
            <!--        <input type="button" @click="search" value="查询" >-->
            <el-button type="primary" icon="el-icon-search" size="mini" @click="search"></el-button>&nbsp
            <!--        <input type="button" @click="reSearch" value="取消">-->
            <el-button type="primary" @click="reSearch" size="mini" icon="el-icon-delete"></el-button>

        </div>
        <p v-if="showEdit">

            <label>书籍名称: <input type="text" placeholder="请输入书籍名称"  v-model="bookName"> </label>&nbsp;
            <label>作者: <input type="text" placeholder="请输入作者" v-model="writer"> </label>&nbsp;
            <label>出版社: <input type="text" placeholder="请输入出版社" v-model="cbs"> </label>&nbsp;
            <label>库存数: <input type="text" placeholder="请输入库存数" v-model="sum"> </label>&nbsp;
            <el-button type="primary" @click="sureUpdata()"  style="margin-left: 100px" size="mini">提交</el-button>
            <el-button type="info" size="mini" @click="returns()">取消</el-button>
        </p>


        <el-table :data="totalBook.slice((currentPage-1)*PageSize,currentPage*PageSize)" style="width: 100%">
            <el-table-column prop="书籍编号" label="书籍编号" @click="yyyy(1)" width="180"></el-table-column>
            <el-table-column prop="书籍名" label="书籍名" width="180"></el-table-column>
            <el-table-column prop="书籍类型" label="书籍类型"></el-table-column>
            <el-table-column prop="库存数" label="库存数" width="180"></el-table-column>
            <el-table-column prop="作者" label="作者" width="180"></el-table-column>
            <el-table-column prop="出版社" label="出版社" width="180"></el-table-column>
            <el-table-column prop="借阅时间" label="借阅时间"></el-table-column>
            <el-table-column prop="最大归还时间" label="最大归还时间"></el-table-column>


            <el-table-column
                    fixed="right"
                    label="操作"
                    width="100">
                <template slot-scope="scope">
                    <el-button @click="updata(scope.$index)" type="text" size="small">修改</el-button>

                </template>
            </el-table-column>
        </el-table>
        <div class="tabListPage">
            <el-pagination @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
                           :current-page="currentPage"
                           :page-sizes="pageSizes"
                           :page-size="PageSize" layout="total, sizes, prev, pager, next, jumper"
                           :total="totalBook.length">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import $ from 'jquery'
    import vue from 'vue'
    export default {
        name: 'Updata',
        el: "#sr",
        data(){
            return{
                bookName: null,
                writer: null,
                type: "",
                cbs:'',
                sum:'',
                totalBook:[],
                level:'',
                showEdit:false,
                bookCode:'',
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
        methods:{
            search(){
                let that=this;
                if(this.writer==null){
                    $.ajax({
                        url:"http://localhost:8888/Library/bookName?图书名="+this.bookName,
                        type:"Get",
                        ContentType:"application/json;charset=utf-8",
                        dataType:"json",
                        async:"false",
                        success:function (res) {
                            that.totalBook=res.data

                        },
                    })
                }else if(this.bookName==null){
                    $.ajax({
                        url:"http://localhost:8888/Library/Writer?作者="+this.writer,
                        type:"Get",
                        ContentType:"application/json;charset=utf-8",
                        dataType:"json",
                        async:"false",
                        success:function (res) {
                            that.totalBook=res.data

                        },
                    })
                }else{
                    $.ajax({
                        url:"http://localhost:8888/Library//NameWriter?作者="+this.writer+"&书名="+this.bookName,
                        type:"Get",
                        ContentType:"application/json;charset=utf-8",
                        dataType:"json",
                        async:"false",
                        success:function (res) {
                            that.totalBook=res.data

                        },
                    })
                }

            },
            reSearch(){
                let that=this;
                $.ajax({
                    url:"http://localhost:9999/Trip/select2",
                    type:"Get",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {
                        that.totalBook=res.data

                    },
                })
            },
            updata(index){
                this.showEdit=true
                this.bookName=this.totalBook[index].书籍名
                this.writer=this.totalBook[index].作者
                this.cbs=this.totalBook[index].出版社
                this.sum=this.totalBook[index].库存数
                this.bookCode=this.totalBook[index].书籍编号

            },
            returns(){
                this.showEdit=false
            },
            sureUpdata(){
                let that=this;
                $.ajax({
                    url:"http://localhost:8888/Library/UpdateBook?bookname="+this.bookName+"&cbs="+this.cbs
                        +"&indexs="+this.bookCode+"&sum="+this.sum+"&writer="+this.writer,
                    type:"Post",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {
                        that.$message.success('修改成功')
                        that.bookName=''
                        that.writer=''
                        that.cbs=''
                        that.sum=''
                        that.bookCode=''
                        location.reload();
                    },
                })
            },
            // 分页
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
            },
            sleep(ms) { //sleep延迟方法2
                var unixtime_ms = new Date().getTime();
                while(new Date().getTime() < unixtime_ms + ms) {}
            }
        },
        created() {
            let that=this;
            $.ajax({
                url:"http://localhost:9999/Trip/select2",
                type:"Get",
                ContentType:"application/json;charset=utf-8",
                dataType:"json",
                async:"false",
                success:function (res) {
                    that.totalBook=res.data
                    that.level=window.sessionStorage.getItem('level')
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