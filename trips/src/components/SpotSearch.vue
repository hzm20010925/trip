<template>
<!--    <el-input></el-input>-->
    <div id="sr">
        <div v-if="!showEdit">
        景点编号：<el-input type="text" v-model="spotId" placeholder="请输入景点编号" style="width: 200px"></el-input>
        景点标题：<el-input type="text" v-model="spotName" placeholder="请输入景点标题" style="width: 200px"></el-input>&nbsp
        <!--        <input type="button" @click="search" value="查询" >-->
        <el-button type="primary" icon="el-icon-search" size="mini" @click="searchs()"></el-button>&nbsp
        <!--        <input type="button" @click="reSearch" value="取消">-->
        <el-button type="primary" @click="reSearch()" size="mini" icon="el-icon-delete"></el-button>
        </div>
        <div v-if="showEdit" >
            评分：<el-input type="text" v-model="score" placeholder="请输入评分" style="width: 200px"></el-input>&nbsp
            评价: <el-input type="text" v-model="evaluate" placeholder="请输入评价" style="width: 200px"></el-input>
           <el-button type="primary" @click="sureInsert()"  style="margin-left: 100px" size="mini">提交</el-button>
            <el-button type="info" size="mini" @click="returns()">取消</el-button>
        </div>
        <!-- 将获取到的数据进行计算 -->
        <el-table :data="FilteDate.slice((currentPage-1)*PageSize,currentPage*PageSize)" style="width: 100%">

            <el-table-column prop="景点id" label="景点id" @click="yyyy(1)" width="180" ></el-table-column>
            <el-table-column prop="标题" label="标题" width="280"></el-table-column>
            <el-table-column prop="城市" label="城市"></el-table-column>
            <el-table-column prop="评分" label="评分"></el-table-column>
            <el-table-column prop="价格" label="价格"></el-table-column>
            <el-table-column prop="销售量" label="销售量"></el-table-column>
            <el-table-column prop="评论数" label="评论数"></el-table-column>

            <el-table-column
                    fixed="right"
                    label="操作"
                    width="100">
                <template slot-scope="scope">
                    <el-button @click="imports(scope.$index)" type="text" size="small">点评</el-button>
                    <el-button v-if="identitys" @click="open(scope.row)" type="text" size="small">删除</el-button>
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
        name: "SpotSearch",
        el: "#sr",
        data(){
            return{
                bookName: null,
                evaluate:'',
                userId:'',
                spotid:'',
                score:'',
                writer: null,
                type: "",
                identitys:"",
                spotId:null,
                showEdit:false,
                spotName:null,
                total:[],
                options:["点评"],
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
        methods: {

            sureInsert(){
                this.userId=window.sessionStorage.getItem('userid')
                let that=this
                $.ajax({
                    url:"http://localhost:9999/Trip/insertCom?evaluate=" +that.evaluate+ "&score="+that.score+"&spotid="+that.spotid+"&userid="+that.userId,
                    type:"Post",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {
                        that.$message.success('点评成功')
                        that.showEdit = false
                    }

                })


            },
            returns(){
                this.showEdit=false
                this.spotid=''
                this.spotName=''

            },
            imports(index){
                this.showEdit=true
                this.spotid=this.total[index]['景点id']


            },
            del(index){
                let that=this;


                $.ajax({
                    url:"http://localhost:9999/Trip/deleteSpot?spotIndex=" +index['景点id'],
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


                    }
                })
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
            },
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
            searchs(){
                let that=this;
                if(this.spotId==null){
                    $.ajax({
                        url:"http://localhost:9999/Trip/spotInfoByName?标题="+this.spotName,
                        type:"Get",
                        ContentType:"application/json;charset=utf-8",
                        dataType:"json",
                        async:"false",
                        success:function (res) {
                            that.total=res.data
                            that.totalCount=res.data.length
                        },
                    })
                }else if(this.spotName==null){
                    $.ajax({
                        url:"http://localhost:9999/Trip/spotInfoById?景点id="+this.spotId,
                        type:"Get",
                        ContentType:"application/json;charset=utf-8",
                        dataType:"json",
                        async:"false",
                        success:function (res) {
                            that.total=res.data
                            that.totalCount=res.data.length
                        },
                    })
                }else{
                    $.ajax({
                        url:"http://localhost:9999/Trip/titleandId?景点id="+this.spotId+"&标题="+this.spotName,
                        type:"Get",
                        ContentType:"application/json;charset=utf-8",
                        dataType:"json",
                        async:"false",
                        success:function (res) {
                            that.total=res.data
                            that.totalCount=res.data.length
                        },
                    })
                }

            },
            reSearch(){
                let that=this;
                $.ajax({
                    url:"http://localhost:9999/Trip/cityspotInfo",
                    type:"Get",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {
                        that.total=res.data
                        that.spotId=""
                        that.spotName=""
                        that.totalCount=res.data.length
                    },
                })
            }

        },
        created() {
            let that=this;
            var isflag;
            $.ajax({
                url:"http://localhost:9999/Trip/cityspotInfo",
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
                    that.total=res.data
                    isflag=window.sessionStorage.getItem('level')
                    if (isflag==1){
                        that.identitys=true
                    }
                    if (isflag==2) {
                        that.identitys=false
                    }
                    // console.log(that.totalBook)
                    that.totalCount=res.data.length
                    that.level=window.sessionStorage.getItem('level')
                },
            })
        },
        computed:{
            FilteDate:function () {

                var that=this
                return this.total.filter(function (item) {

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
</style>