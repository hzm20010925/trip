<template>
    <div id="sr">

<!--        城市编号：<el-input type="text" v-model="cityId" placeholder="请输入城市编号" style="width: 200px"></el-input>-->
        城市名称：<el-input type="text" v-model="cityName" placeholder="请输入城市名称" style="width: 400px"></el-input>&nbsp
        <!--        <input type="button" @click="search" value="查询" >-->
        <el-button type="primary" icon="el-icon-search" size="mini" @click="research()"></el-button>&nbsp
        <!--        <input type="button" @click="reSearch" value="取消">-->
        <el-button type="primary" @click="returns()" size="mini" icon="el-icon-delete"></el-button>
        <div class="chart"></div>
        <div class="chartRight"></div>
        <div class="chartleft"></div>
        <div class="chartbottom"></div>
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
                name:[],
                piedata:[],
                piecedata:[{value:0,name:'100以下'},
                    {value:0,name:'100-300'},
                    {value:0,name:'300-500'},
                    {value:0,name:'500以上'}],
                spotsale:[],
                spotdate:[],
                sortspotname:[],
                sortspotsale:[]



            }

        },
        methods: {
            getCharBottom() {

                // console.log(this.piecedata)
                var myChartBottom = this.$echarts.init(document.querySelector('.chartbottom'))
                var options = {
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
                        name:'业绩(元)'
                    },
                    title: {
                        text: "近5日旅游业绩变化",
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
                myChartBottom.setOption(options)
            },
            returns(){
                this.cityId=''
                this.cityName=''
            },
            research() {
                var that=this;
                that.piecedata[0]['value']=0
                that.piecedata[1]['value']=0
                that.piecedata[2]['value']=0
                that.piecedata[3]['value']=0
                $.ajax({
                    url:"http://localhost:9999/Trip/cityInfoByName?城市名称="+that.cityName,
                    type:"Get",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {
                        var S=''
                        var one=0,two=0,three=0,four=0
                        for (var i=0;i<res['data']['length'];i++){

                            // that.title[i]=res['data'][i]['标题']
                            // S="{ value:"+res['data'][i]['评论数']+", name:'"+res['data'][i]['标题']+"'}"
                            // that.piedata[i]=eval("("+S+")")
                            if (res['data'][i]['价格']<=100){
                                one=one+1
                            }
                            if(res['data'][i]['价格']<=300&&100<res['data'][i]['价格'])(
                                two=two+1
                            )
                            if(res['data'][i]['价格']<=500&&300<res['data'][i]['价格'])(
                                three=three+1
                            )
                            if(res['data'][i]['价格']>500)(
                                four=four+1
                            )


                        }
                        for (var j=0;j<10;j++){
                            that.name[j]=res['data'][j]['标题']
                            that.commentNum[j]=res['data'][j]['销售量']
                        }

                        that.piecedata[0]['value']=one
                        that.piecedata[1]['value']=two
                        that.piecedata[2]['value']=three
                        that.piecedata[3]['value']=four
                        // console.log(that.cityName)
                        // that.cityName=['西安', '北京', '成都', '杭州', '西双版纳', '桂林', '三亚', '上海', '丽江', '厦门']
                        // that.commentNum=['1','2','3','4','5','6','7','8','9','10']
                        that.getChar();
                        that.getCharPie();

                        // console.log(that.piecedata)
                    },
                })
                $.ajax({
                    url:"http://localhost:9999/Trip/cityInfobydate?城市名称="+that.cityName,
                    type:"Get",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {
                        for (var j = 0; j < 5; j++) {
                            that.spotdate[j] = res['data'][j]['日期']
                            that.spotsale[j] = res['data'][j]['销售总额']
                        }
                        that.getCharBottom();



                    }
                })
                $.ajax({
                    url:"http://localhost:9999/Trip/citysale?城市名称="+that.cityName,
                    type:"Get",
                    ContentType:"application/json;charset=utf-8",
                    dataType:"json",
                    async:"false",
                    success:function (res) {
                        for (var j = 0; j < 5; j++) {
                            that.sortspotname[4-j] = res['data'][j]['标题']
                            that.sortspotsale[4-j] = res['data'][j]['价格']
                        }
                        that.getCharLeft();


                    }
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
            getCharLeft() {
                var myChartLeft = this.$echarts.init(document.querySelector('.chartleft'))
                var optionLeft = {
                    title: {
                        text: '价格排名',
                        textStyle:{
                            fontFamily:'宋体'
                        }
                    },
                    color:'#2f89cf',
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {},
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'value',
                        boundaryGap: [0, 0.01]
                    },
                    yAxis: {
                        type: 'category',
                        data: this.sortspotname,
                        axisLabel: {
                            color: "black",

                            interval:0,
                            rotate: 30,
                            //多出字可以省略显示成点
                            formatter: function (params){
                                var index = 5; //字数为6个超出就显示成点
                                var newstr = '';
                                for(var i = 0; i< params.length; i += index){
                                    var tmp = params.substring(i, i+index);
                                    newstr += tmp +'';
                                }
                                if( newstr.length > 5)
                                    return newstr.substring(0, 5) + '...';
                                else
                                    return '\n'+newstr;
                            }
                        }
                    },
                    series: [
                        {
                            name: '价格',
                            type: 'bar',
                            data: this.sortspotsale,
                            itemStyle: {
                                barBorderRadius: 5
                            }
                        }
                    ]
                };
                myChartLeft.setOption(optionLeft)
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
                        text: "热门景点排行",
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
            },
            getCharPie() {


                let myChartRight = this.$echarts.init(document.querySelector('.chartRight'))
                let option = {
                    tooltip: {
                        trigger: 'item'
                    },
                    color:['#2f89cf','gray','yellow','orange'],
                    legend: {
                        top: '7%',
                        left: 'center',

                    },
                    title: {
                        text: "景点价格分布区间",
                        // left: '40%',
                        // top: '20px'
                        textStyle:{
                            fontFamily:'宋体'
                        }
                    },
                    series: [
                        {
                            name: '价格区间',
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
            }

        },
        created() {
            var that=this;

            $.ajax({
                url:"http://localhost:9999/Trip/cityInfoByName?城市名称="+that.cityName,
                type:"Get",
                ContentType:"application/json;charset=utf-8",
                dataType:"json",
                async:"false",
                success:function (res) {
                    var S=''
                    var one=0,two=0,three=0,four=0
                    for (var i=0;i<res['data']['length'];i++){
                        that.name[i]=res['data'][i]['标题']
                        that.commentNum[i]=res['data'][i]['销售量']
                        // that.title[i]=res['data'][i]['标题']
                        // S="{ value:"+res['data'][i]['评论数']+", name:'"+res['data'][i]['标题']+"'}"
                        // that.piedata[i]=eval("("+S+")")
                        if (res['data'][i]['价格']<=100){
                            one=one+1
                        }
                        if(res['data'][i]['价格']<=300&&100<res['data'][i]['价格'])(
                            two=two+1
                        )
                        if(res['data'][i]['价格']<=500&&300<res['data'][i]['价格'])(
                            three=three+1
                        )
                        if(res['data'][i]['价格']>500)(
                            four=four+1
                        )


                    }
                    that.piecedata[0]['value']=one
                    that.piecedata[1]['value']=two
                    that.piecedata[2]['value']=three
                    that.piecedata[3]['value']=four
                    // console.log(that.cityName)
                    // that.cityName=['西安', '北京', '成都', '杭州', '西双版纳', '桂林', '三亚', '上海', '丽江', '厦门']
                    // that.commentNum=['1','2','3','4','5','6','7','8','9','10']
                    that.getChar();
                    that.getCharPie();
                    console.log(that.piecedata)
                    // console.log(that.piecedata)
                },
            })
            $.ajax({
                url:"http://localhost:9999/Trip/cityInfobydate?城市名称="+that.cityName,
                type:"Get",
                ContentType:"application/json;charset=utf-8",
                dataType:"json",
                async:"false",
                success:function (res) {
                    for (var j = 0; j < 5; j++) {
                        that.spotdate[j] = res['data'][j]['日期']
                        that.spotsale[j] = res['data'][j]['销售总额']
                    }
                    that.getCharBottom();



                }
            })
            $.ajax({
                url:"http://localhost:9999/Trip/citysale?城市名称="+that.cityName,
                type:"Get",
                ContentType:"application/json;charset=utf-8",
                dataType:"json",
                async:"false",
                success:function (res) {
                    for (var j = 0; j < 5; j++) {
                        that.sortspotname[4-j] = res['data'][j]['标题']
                        that.sortspotsale[4-j] = res['data'][j]['价格']
                    }
                    that.getCharLeft();


                }
            })
        }
    }
</script>

<style scoped>
    #sr{
        width: 1350px;
        height: 600px;
        /*border: 1px solid black;*/
        font-family: 宋体;
    }
    .chart{
        width: 750px;
        height: 300px;
        /*border: 1px solid black;*/
        float: left;
    }
    .chartleft{
        width: 750px;
        height: 250px;
        /*border: 1px solid black;*/
        float: left;
    }
    .chartbottom{
        width: 590px;
        height: 250px;
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