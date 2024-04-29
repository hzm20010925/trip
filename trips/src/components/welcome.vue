<template>
    <div id="sr">
        <div class="chart"></div>
        <div class="chartRight"></div>
        <div class="chartleft"></div>
        <div class="chartbottom"></div>
    </div>

</template>

<!--<script src="../assets/fonts/echarts.min.js"></script-->

<!--<script src="../assets/fonts/echarts.min.js"></script>-->
<script >
    import  echarts from 'echarts'
    import $ from "jquery";
    export default {
        name: 'Welcome',
        el: "#sr",
        data() {
            return {
                bookName: null,
                cityName: [],
                commentNum: [],
                piecedata: [{value: 0, name: '100以下'},
                    {value: 0, name: '100-300'},
                    {value: 0, name: '300-500'},
                    {value: 0, name: '500以上'}],
                sortCityName:[],
                sortCitySum:[],
                spotsale:[],
                spotdate:[]


            }
        },
        methods: {
            getCharBottom() {

                // console.log(this.piecedata)
                var myChartBottom = this.$echarts.init(document.querySelector('.chartbottom'))
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
                myChartBottom.setOption(option)
            },
            getCharPie() {

                // console.log(this.piecedata)
                var myChartRight = this.$echarts.init(document.querySelector('.chartRight'))
                var options = {
                    tooltip: {
                        trigger: 'item'
                    },
                    color:['#2f89cf','gray','yellow','orange'],
                    legend: {
                        top: '7%',
                        left: 'center',

                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
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

                myChartRight.setOption(options)
                // console.log(3333)
                // location.reload();
            },
            getCharLeft() {
                var myChartLeft = this.$echarts.init(document.querySelector('.chartleft'))
                var optionLeft = {
                    title: {
                        text: '销售额排名',
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
                        data: this.sortCityName
                    },
                    series: [
                        {
                            name: '销售总额',
                            type: 'bar',
                            data: this.sortCitySum,
                            itemStyle: {
                                barBorderRadius: 5
                            }
                        }
                    ]
                };
                myChartLeft.setOption(optionLeft)
            },
            getChar() {


                var myChart = this.$echarts.init(document.querySelector('.chart'))
                var option = {
                    xAxis: {
                        data: this.cityName,
                        axisLabel: {
                            color: "blacck",
                            interval: 0
                        }
                    },
                    color: "#2f89cf",
                    title: {
                        text: "十大热门城市",
                        textStyle:{
                            fontFamily:'宋体'
                        }
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: "shadow"
                        }
                    },

                    legend: {
                        data: ["评论总数"]
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    yAxis: {},
                    series: [
                        {
                            type: 'bar',
                            name: '评论总数',
                            data: this.commentNum,
                            // barWidth:"35%",
                            itemStyle: {
                                barBorderRadius: 5
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
                // this.pieData();
                myChart.setOption(option)

                // this.getCharPie()
            },
            pieData() {
                var that = this;
                $.ajax({
                    url: "http://localhost:9999/Trip/cityspotInfo",
                    type: "Get",
                    ContentType: "application/json;charset=utf-8",
                    dataType: "json",
                    async: "false",
                    success: function (res) {

                        var one = 0, two = 0, three = 0, four = 0
                        for (var i = 0; i < res['data']['length']; i++) {
                            // that.title[i]=res['data'][i]['标题']
                            // S="{ value:"+res['data'][i]['评论数']+", name:'"+res['data'][i]['标题']+"'}"
                            // that.piedata[i]=eval("("+S+")")
                            if (res['data'][i]['价格'] <= 100) {
                                one = one + 1
                            }
                            if (res['data'][i]['价格'] <= 300 && 100 < res['data'][i]['价格']) (
                                two = two + 1
                            )
                            if (res['data'][i]['价格'] <= 500 && 300 < res['data'][i]['价格']) (
                                three = three + 1
                            )
                            if (res['data'][i]['价格'] > 500) (
                                four = four + 1
                            )

                            // console.log(one)

                        }
                        // console.log(res['data']['length'])
                        that.piecedata[0]['value'] = one
                        that.piecedata[1]['value'] = two
                        that.piecedata[2]['value'] = three
                        that.piecedata[3]['value'] = four
                        // console.log(that.piecedata[0]['value'])

                    }
                })



            }

            },
        created() {
            var that = this;
            $.ajax({
                url: "http://localhost:9999/Trip/cityInfo",
                type: "Get",
                ContentType: "application/json;charset=utf-8",
                dataType: "json",
                async: "false",
                success: function (res) {
                    // console.log(that.piecedata)
                    for (var j = 0; j < res['data']['length']; j++) {
                        that.cityName[j] = res['data'][j]['城市']
                        that.commentNum[j] = res['data'][j]['评论总数']
                    }
                    that.getChar();
                }
            })
            $.ajax({
                url: "http://localhost:9999/Trip/spotInfobydate",
                type: "Get",
                ContentType: "application/json;charset=utf-8",
                dataType: "json",
                async: "false",
                success: function (res) {
                    // console.log(that.piecedata)
                    for (var j = 0; j < 5; j++) {
                        that.spotdate[j] = res['data'][j]['日期']
                        that.spotsale[j] = res['data'][j]['销售总额']
                    }
                    that.getCharBottom();
                }
            })
            $.ajax({
                url: "http://localhost:9999/Trip/cityspotInfo",
                type: "Get",
                ContentType: "application/json;charset=utf-8",
                dataType: "json",
                async: "false",
                success: function (res) {

                    var one = 0, two = 0, three = 0, four = 0
                    for (var i = 0; i < res['data']['length']; i++) {
                        // that.title[i]=res['data'][i]['标题']
                        // S="{ value:"+res['data'][i]['评论数']+", name:'"+res['data'][i]['标题']+"'}"
                        // that.piedata[i]=eval("("+S+")")
                        if (res['data'][i]['价格'] <= 100) {
                            one = one + 1
                        }
                        if (res['data'][i]['价格'] <= 300 && 100 < res['data'][i]['价格']) (
                            two = two + 1
                        )
                        if (res['data'][i]['价格'] <= 500 && 300 < res['data'][i]['价格']) (
                            three = three + 1
                        )
                        if (res['data'][i]['价格'] > 500) (
                            four = four + 1
                        )

                    }
                    // console.log(res['data']['length'])
                    that.piecedata[0]['value'] = one
                    that.piecedata[1]['value'] = two
                    that.piecedata[2]['value'] = three
                    that.piecedata[3]['value'] = four
                    // console.log(that.piecedata[0]['value'])
                    that.getCharPie();
                }
            })
            $.ajax({
                url: "http://localhost:9999/Trip/spotInfobydata",
                type: "Get",
                ContentType: "application/json;charset=utf-8",
                dataType: "json",
                async: "false",
                success: function (res) {
                    // console.log(that.piecedata)
                    for (var j = 0; j < 5; j++) {
                        that.sortCityName[4-j] = res['data'][j]['城市']
                        that.sortCitySum[4-j] = res['data'][j]['销售总额']
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
    }
    .chart{
        width: 745px;
        height: 300px;
        border: 1px solid black;
        float: left;
    }
    .chartleft{
        width: 745px;
        height: 300px;
        border: 1px solid black;
        float: left;
    }
    .chartRight{
        width: 600px;
        height: 300px;
        border: 1px solid black;
        float: left;
    }
    .chartbottom{
        width: 600px;
        height: 300px;
        border: 1px solid black;
        float: left;
    }

    h1{
        font-size: 60px;
        text-align: center;
        margin-top: -10px;
        font-family: "华文楷体";
    }
    .el-carousel{
        margin-top: -30px;
    }

    .el-carousel__item h3 {
        /*color: #475669;*/
        font-size: 18px;
        opacity: 0.75;
        line-height: 200px;
        margin: 0;
    }

    .el-carousel__item:nth-child(6) {
        background: url("../assets/t2.jpeg");
        background-repeat:no-repeat;
        background-size: 500px;
    }

    .el-carousel__item:nth-child(5) {
        background: url("../assets/t3.jpeg");
        background-repeat:no-repeat;
        background-size: 500px;
    }
    .el-carousel__item:nth-child(4) {
        background: url("../assets/t4.jpeg");
        background-repeat:no-repeat;
        background-size: 500px;
    }
    .el-carousel__item:nth-child(3) {
        background: url("../assets/t6.jpg");
        background-repeat:no-repeat;
        background-size: 500px;
    }
</style>