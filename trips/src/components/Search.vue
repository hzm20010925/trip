<template>
    <div id="sr">
        <div v-if="!showEdit">
        城市编号：<el-input type="text" v-model="cityId" placeholder="请输入城市编号" style="width: 200px"></el-input>
        城市名称：<el-input type="text" v-model="cityName" placeholder="请输入城市名称" style="width: 200px"></el-input>&nbsp
<!--        <input type="button" @click="search" value="查询" >-->
        <el-button type="primary" icon="el-icon-search" size="mini" @click="searchs()"></el-button>&nbsp
<!--        <input type="button" @click="reSearch" value="取消">-->
        <el-button type="primary" @click="reSearch()" size="mini" icon="el-icon-delete"></el-button>

        </div>
        <div v-if="showEdit">

            城市名称：<el-input type="text" v-model="cityName" placeholder="请输入城市名称" style="width: 200px"></el-input>&nbsp
            城市URL：<el-input type="text" v-model="cityurl" placeholder="请输入城市URL" style="width: 400px"></el-input>

            <el-button type="primary" @click="sureUpdata()"  style="margin-left: 100px" size="mini">提交</el-button>
            <el-button type="info" size="mini" @click="returns()">取消</el-button>
        </div>
        <!-- 将获取到的数据进行计算 -->
        <el-table :data="FilteDate.slice((currentPage-1)*PageSize,currentPage*PageSize)" style="width: 100%">

        <el-table-column prop="城市id" label="城市id" @click="yyyy(1)" width="180"></el-table-column>
            <el-table-column prop="城市名称" label="城市名称" width="180"></el-table-column>
        <el-table-column prop="城市URL" label="Url"></el-table-column>

            <el-table-column
                    fixed="right"
                    label="操作"
                    width="130"
                    v-if="identitys">
                <template slot-scope="scope">

                    <el-button  @click="updatas(scope.$index)" type="text" size="small">修改</el-button>
                    <el-button  @click="open(scope.row)" type="text" size="small">删除</el-button>

                </template>
            </el-table-column>
<!--            <el-table-column-->
<!--                    fixed="right"-->
<!--                    label="操作"-->
<!--                    width="100">-->
<!--                <template slot-scope="scope">-->
<!--                    <el-button @click="borrow(scope.row)" type="text" size="small">借阅</el-button>-->
<!--                    <el-button v-if="level==1" @click="deleteBook(scope.row.城市编号)" type="text" size="small">删除</el-button>-->
<!--                </template>-->
<!--            </el-table-column>-->
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
        name: 'SearchBook',
        el: "#sr",
        data(){
            return{
                updataId: '',
                writer: null,
                type: "",
                cityId:"",
                identitys:"",
                cityurl:"",
                cityName:"",
                showEdit:false,
                totalBook:[],
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
        methods:{
            searchs(){
                let that=this;
                if(this.cityId==null){
                    $.ajax({
                        url:"http://localhost:9999/Trip/cityName?城市名称="+this.cityName,
                        type:"Get",
                        ContentType:"application/json;charset=utf-8",
                        dataType:"json",
                        async:"false",
                        success:function (res) {
                            that.totalBook=res.data

                        },
                    })
                }else if(this.cityName==null){
                    $.ajax({
                        url:"http://localhost:9999/Trip/cityId?城市编号="+this.cityId,
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
                        url:"http://localhost:9999/Trip/NameId?城市编号="+this.cityId+"&城市名称="+this.cityName,
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

            updatas(index){
                this.showEdit=true
                this.cityName=this.totalBook[index].城市名称
                this.cityurl=this.totalBook[index].城市URL
                this.updataId=this.totalBook[index]['城市id']
                //console.log(this.totalBook[index])

            },
            sureUpdata(){
                let that=this;
                $.ajax({
                    url:"http://localhost:9999/Trip/UpdateCity?cityName="+this.cityName+"&cityurl="+this.cityurl+"&indexs="+this.updataId,
                    type:"post",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {
                        location.reload();
                        that.showEdit=true
                    },
                })
            },
            returns(){
                this.showEdit=false
                this.updataId=''
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
                        that.cityId=""
                        that.cityName=""

                    },
                })
            },
            del(index){
                let that=this;

                $.ajax({
                    url:"http://localhost:9999/Trip/deleteCity?cityIndex=" +index['城市id'],
                    type:"Delete",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {
                        new Promise((resolve, reject) => {
                            setTimeout(() => {
                                resolve("Promise resolved!");
                            }, 1500);
                        }).then(result => {
                            location.reload();
                        });


                    },
                    error:function () {
                        that.$message.error('删除失败')
                    }
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
            open(index) {
                this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
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
            }
        },

        // beforeUpdate(){
        //     let that=this;
        //     $.ajax({
        //         url:"http://localhost:8888/Library/select",
        //         type:"Get",
        //         ContentType:"application/json;charset=utf-8",
        //         dataType:"json",
        //         async:"false",
        //         success:function (res) {
        //             that.totalBook=res.data
        //         },
        //     })
        // },
        created() {
            let that=this;
            var isflag;
            $.ajax({
                url:"http://localhost:9999/Trip/select2",
                type:"Get",
                ContentType:"application/json;charset=utf-8",
                dataType:"json",
                async:"false",
                success:function (res) {
                    // for (var i=0;i<res.data.length;i++){
                    //     that.totalBook[i]=eval('('+res.data[i][["_c0"]]+')')
                    // }

                    // that.totalBook[0]=JSON.stringify(res.data[0][["_c0"]])
                    // console.log(eval('('+res.data[0][["_c0"]]+')'))
                    that.totalBook=res.data
                    isflag=window.sessionStorage.getItem('level')
                    if (isflag==1){
                        that.identitys=true
                    }
                    if (isflag==2) {
                        that.identitys=false
                    }
                    console.log(isflag)
                    console.log(that.identitys)
                    that.totalCount=res.data.length
                    that.level=window.sessionStorage.getItem('level')
                },
            })
        },
        computed:{
            FilteDate:function () {

                var that=this
                return this.totalBook.filter(function (item) {

                    return item
                })
            }
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