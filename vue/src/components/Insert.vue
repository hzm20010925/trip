<template>
    <div id="ins">
        <br><br> <br>
        <el-form  label-width="80px" style="color: #777777">
            <el-form-item label="城市名称" required="true">
                <el-input v-model="name"  style="width: 250px"></el-input>
            </el-form-item><br>
            <el-form-item label="URL">
                <el-input v-model="cityurl" style="width: 250px"></el-input>
            </el-form-item><br>

         <br>
            <el-button type="primary" @click="insert()" style="margin-left: 100px">提交</el-button>&nbsp;&nbsp;&nbsp;
            <el-button type="info" @click="reborn()">重置</el-button>
        </el-form>
    </div>
</template>

<script>
    import $ from "jquery";

    export default {
        name: "insert",
        el: "#ins",
        data(){
            return{
                name: "",
                sum: null,
                cityurl: "",
                cbs:'',
                level:[],
                type:''
            }

        },
        methods:{
            insert(){
                var that=this
                $.ajax({
                    url:"http://localhost:9999/Trip/addCity?cityurl="+this.cityurl+"&name="+this.name,
                    type:"Post",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {
                        that.$message.success('添加成功')
                        that.name=''
                        that.cityurl=''


                    },
                })
            },
            reborn(){
                this.name=''
                this.sum=''
                this.writer=''
                this.cbs=''

                this.type=''
            }
        },
        created() {
            var that=this
            $.ajax({
                url:"http://localhost:8888/Library/type",
                type:"Get",
                ContentType:"application/json;charset=utf-8",
                dataType:"json",
                async:"false",
                success:function (res) {
                    that.level=res.data

                },
            })
        }
    }
</script>

<style scoped>
#ins{
    margin-left: 350px;
}
</style>