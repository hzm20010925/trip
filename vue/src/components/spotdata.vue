<template>
    <div id="sr">

        <div>
            景点编号：<el-input type="text" v-model="cityId" placeholder="请输入城市编号" style="width: 200px"></el-input>&nbsp

            <el-button type="primary" icon="el-icon-search" size="mini" @click="research()"></el-button>&nbsp
            <!--        <input type="button" @click="reSearch" value="取消">-->
            <el-button type="primary" @click="returns()" size="mini" icon="el-icon-delete"></el-button>
        </div>

        <div class="chart"></div>
        <div class="chartbottom">
            <p class="spottitle">当前景点评分</p>
            <p class="spotsco">{{spotscore}}</p>
        </div>

        <div class="chartleft"></div>
        <div class="chartRight"></div>
    </div>
</template>

<script>
    import $ from 'jquery'
    import vue from 'vue'
    export default {
        name: 'citydetail',
        el: "#sr",
        data() {
            return {
                cityId:'314',
                cityName:'西安',
                commentNum:[],
                spotscore:'',
                name:[],
                spotdate:[],
                spotsale:[],
                spotsaleSum:[],
                piedata:[],
                piecedata:[{value:0,name:'1分'},
                    {value:0,name:'2分'},
                    {value:0,name:'3分'},
                    {value:0,name:'4分'},
                    {value:0,name:'5分'}]


            }

        },
        methods: {
            returns(){
                this.cityId=''
                this.cityName=''
            },
            getCharBottom() {

                // console.log(this.piecedata)
                var myChartBottom = this.$echarts.init(document.querySelector('.chart'))
                var option = {
                    xAxis: {
                        type: 'category',
                        data: this.spotdate
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    color: "#2f89cf",
                    yAxis: {
                        type: 'value',
                        name:'价格(元)'
                    },
                    title: {
                        text: "近期景点价格变化",
                        // left: '40%',
                        // top: '20px'
                        textStyle:{
                            fontFamily:'宋体'
                        }
                    },
                    series: [
                        {
                            data: this.spotsale,
                            type: 'line'

                        }
                    ]
                };
                myChartBottom.setOption(option)
            },
            getCharLeft() {

                // console.log(this.piecedata)
                var myChartBottom = this.$echarts.init(document.querySelector('.chartleft'))
                var option = {
                    xAxis: {
                        type: 'category',
                        data: this.spotdate
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    color: "#2f89cf",
                    yAxis: {
                        type: 'value',
                        name:'销量(元)'
                    },
                    title: {
                        text: "近期景点销量变化",
                        // left: '40%',
                        // top: '20px'
                        textStyle:{
                            fontFamily:'宋体'
                        }
                    },
                    series: [
                        {
                            data: this.spotsaleSum,
                            type: 'line'

                        }
                    ]
                };
                myChartBottom.setOption(option)
            },
            research() {
                let that=this;
                $.ajax({
                    url:"http://localhost:9999/Trip/spotsaleInfo?景点id="+that.cityId,
                    type:"Get",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {
                        var S=''
                        var one=0,two=0,three=0,four=0
                        that.spotscore=res['data'][0]['评分']
                        for (var i=0;i<res['data']['length'];i++){
                            that.spotdate[i]=res['data'][i]['日期']
                            that.spotsale[i]=res['data'][i]['价格']
                            that.spotsaleSum[i]=res['data'][i]['销售量']


                        }

                        that.getCharBottom();
                        that.getCharLeft()

                    },
                })

                $.ajax({
                    url:"http://localhost:9999/Trip/getCom?景点id="+that.cityId,
                    type:"Get",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {
                        var S=''
                        var one=0,two=0,three=0,four=0,five=0
                        for (var i=0;i<res['data']['length'];i++){

                            if (res['data'][i]['评分']==1){
                                one=one+1
                            }
                            if(res['data'][i]['评分']==2)(
                                two=two+1
                            )
                            if(res['data'][i]['评分']==3)(
                                three=three+1
                            )
                            if(res['data'][i]['评分']==4)(
                                four=four+1
                            )
                            if(res['data'][i]['评分']==5)(
                                five=five+1
                            )

                        }
                        that.piecedata[0]['value']=one
                        that.piecedata[1]['value']=two
                        that.piecedata[2]['value']=three
                        that.piecedata[3]['value']=four
                        that.piecedata[4]['value']=five
                        that.getCharPie()

                    },
                })
            },
            getPieData(){
                let that = this;
                $.ajax({
                    url: "http://localhost:9999/Trip/getCityName",
                    type: "Get",
                    ContentType: "application/json;charset=utf-8",
                    dataType: "json",
                    async: "false",
                    success: function (res) {

                        location.reload();

                    }

                })

            },
            getCharPie() {


                let myChartRight = this.$echarts.init(document.querySelector('.chartRight'))
                let option = {
                    tooltip: {
                        trigger: 'item'
                    },
                    color:['yellow','gray','#99ff33','orange','#2f89cf'],
                    legend: {
                        top: '7%',
                        left: 'center',

                    },
                    title: {
                        text: "系统用户评分分布",
                        // left: '40%',
                        // top: '20px'
                        textStyle:{
                            fontFamily:'宋体'
                        }
                    },
                    series: [
                        {
                            name: '评分区间',
                            type: 'pie',
                            radius: ['40%', '70%'],
                            avoidLabelOverlap: false,
                            itemStyle: {
                                borderRadius: 10,
                                borderColor: '#fff',
                                borderWidth: 2
                            },

                            label: {
                                show: false,

                            },
                            // emphasis: {
                            //     label: {
                            //         show: true,
                            //         fontSize: 40,
                            //         fontWeight: 'bold'
                            //     }
                            // },
                            labelLine: {
                                show: false
                            },
                            data:this.piecedata
                        }
                    ]
                };
                myChartRight.setOption(option)
            },
            getChar() {


                let myChart = this.$echarts.init(document.querySelector('.chart'))
                let option = {
                    xAxis: {
                        data: this.name,
                        axisLabel: {
                            color: "black",

                            interval:0,
                            rotate: 30,
                            //多出字可以省略显示成点
                            formatter: function (params){
                                var index = 6; //字数为6个超出就显示成点
                                var newstr = '';
                                for(var i = 0; i< params.length; i += index){
                                    var tmp = params.substring(i, i+index);
                                    newstr += tmp +'';
                                }
                                if( newstr.length > 6)
                                    return newstr.substring(0, 6) + '...';
                                else
                                    return '\n'+newstr;
                            }
                        }
                    },
                    color: "#2f89cf",
                    title: {
                        text: "十大热门景点",
                        // left: '40%',
                        // top: '20px'
                        textStyle:{
                            fontFamily:'宋体'
                        }
                    },
                    tooltip:{
                        trigger:'axis',
                        axisPointer:{
                            type:"shadow"
                        }

                    },

                    legend:{
                        data:["销售量"]
                    },
                    yAxis: {

                    },
                    series: [
                        {
                            type: 'bar',
                            name:'销售量',
                            data: this.commentNum,
                            // barWidth:"35%",
                            itemStyle:{
                                barBorderRadius:5
                            },
                            // emphasis:{
                            //     label:{
                            //         show:true,
                            //         position:'top'
                            //     }
                            // }
                        }
                    ]
                };
                myChart.setOption(option)
            }


        },
        created() {
            let that=this;
            $.ajax({
                url:"http://localhost:9999/Trip/spotsaleInfo?景点id="+that.cityId,
                type:"Get",
                ContentType:"application/json;charset=utf-8",
                dataType:"json",
                async:"false",
                success:function (res) {
                    var S=''

                    that.spotscore=res['data'][0]['评分']
                    for (var i=0;i<res['data']['length'];i++){
                        that.spotdate[i]=res['data'][i]['日期']
                        that.spotsale[i]=res['data'][i]['价格']
                        that.spotsaleSum[i]=res['data'][i]['销售量']
                        // S="{ value:"+res['data'][i]['评论数']+", name:'"+res['data'][i]['标题']+"'}"
                        // that.piedata[i]=eval("("+S+")")



                    }

                    that.getCharBottom();

                    that.getCharLeft()
                },
            })

            $.ajax({
                url:"http://localhost:9999/Trip/getCom?景点id="+that.cityId,
                type:"Get",
                ContentType:"application/json;charset=utf-8",
                dataType:"json",
                async:"false",
                success:function (res) {
                    var S=''
                    var one=0,two=0,three=0,four=0,five=0
                    for (var i=0;i<res['data']['length'];i++){

                        if (res['data'][i]['评分']==1){
                            one=one+1
                        }
                        if(res['data'][i]['评分']==2)(
                            two=two+1
                        )
                        if(res['data'][i]['评分']==3)(
                            three=three+1
                        )
                        if(res['data'][i]['评分']==4)(
                            four=four+1
                        )
                        if(res['data'][i]['评分']==5)(
                            five=five+1
                        )

                    }
                    that.piecedata[0]['value']=one
                    that.piecedata[1]['value']=two
                    that.piecedata[2]['value']=three
                    that.piecedata[3]['value']=four
                    that.piecedata[4]['value']=five
                    that.getCharPie()

                },
            })
        }
    }
</script>

<style scoped>
    #sr{
        width: 1350px;
        height: 560px;
        /*border: 1px solid black;*/
        font-family: 宋体;
    }
    .spottitle{
        font-family: 宋体;
        font-size: 20px;
        font-weight: bold;
    }
    .spotsco{
        font-family: 宋体;
        font-size: 80px;
        text-align: center;
        margin-top: -1px;
    }
    .chartleft{
        width: 800px;
        height: 240px;
        /*border: 1px solid black;*/
        float: left;
    }
    .chart{
        width: 800px;
        height: 240px;
        /*border: 1px solid black;*/
        float: left;
    }
    .chartbottom{
        width: 490px;
        height: 230px;
        /*border: 1px solid black;*/
        float: left;
    }
    .chartRight{
        width: 500px;
        height: 300px;
        /*border: 1px solid black;*/
        float: left;
    }
    table{
        margin-top: 30px;

    }
    th {

        font: bold 11px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;

        color: gray;

        /*border-right: 1px  #C1DAD7;*/

        /*border-bottom: 1px  #C1DAD7;*/

        /*border-top: 1px  #C1DAD7;*/

        /*letter-spacing: 2px;*/

        text-transform: uppercase;

        text-align: left;

        padding: 6px 6px 6px 12px;

        background: white no-repeat;

        width: 150px;
    }
    /*a {*/

    /*    color: #c75f3e;*/

    /*}*/
</style>