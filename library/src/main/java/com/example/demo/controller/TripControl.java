package com.example.demo.controller;
import com.example.demo.config.R;
import com.example.demo.config.HIVE;
import com.example.demo.config.MYSQL;
import com.example.demo.config.SQL;
import com.example.demo.form.HelloForm;
import org.apache.avro.data.Json;
import org.mortbay.util.ajax.JSON;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin   //开启支持跨域请求
@RequestMapping("/Trip/")
@RestController
public class TripControl {
    @GetMapping(value="/select")
    public R GetCityList()
    {
        List alist=
                HIVE.GetResults("\n" +
                        "SELECT CONCAT_WS(',',\n" +
                        "  CONCAT('{ \"城市id\": \"', `城市id`, '\"'),\n" +
                        "  CONCAT('\"城市名称\": \"',`城市名称` ,'\"'),\n" +
                        "  CONCAT('\"城市URL\": \"', `城市URL`, '\" }')\n" +
                        ")\n" +
                        "FROM t_city\n");
        return  R.ok().put("data", alist);
    }
    @GetMapping(value="/select2")
    public R GetCityList2()
    {
        List alist=
                MYSQL.GetResults("select * from t_city");
        return  R.ok().put("data", alist);
    }
    @GetMapping(value="/getCityName")
    public R getCityName()
    {
        List alist=
                MYSQL.GetResults("select `城市id` as value,`城市名称` as label from t_city");
        return  R.ok().put("data", alist);
    }
    @GetMapping(value = "/login" )
    public R loginPage(@RequestParam("username") String username,
                       @RequestParam("password")  String password){

        String t="select * from t_userinfo where `账户`='"+username+"' and `密码`='"+password+"'";
        System.out.println(t);
        List alist=MYSQL.GetResults(t);
        return R.ok().put("data",alist);
    }

    @GetMapping(value = "/home")
    public R GetLoginInfo(@RequestParam("username") String username,
                          @RequestParam("password") String password){

        String t="SELECT * from t_authority WHERE `权限编号` in (\n" +
                "SELECT 权限编号 from t_authset where `身份编号` IN (\n" +
                "SELECT 身份编号 FROM t_userinfo  where `账户`='"+username+"' and `密码`='"+password+"'\n" +
                "))";
        System.out.println(t);
        System.out.println("----------");
        List alist=MYSQL.GetResults(t);

        if(alist.size()>0){
            List userinfomation=MYSQL.GetResults("select * from t_userinfo " +
                    "where 账户="+"'"+username+"'"+" and 密码='"+password+"'");
            R returnList=R.ok().put("data",alist);
            returnList.put("user",userinfomation);
            return returnList.put("token","asfdsdafsadf");
        }else{
            return R.error().put("message","用户名或密码错误");
        }


    }
    @GetMapping(value="/cityName")
    public R GetDataByCityName(        @RequestParam("城市名称") String  cityname        )
    {
        String t="select   * from  t_city  " +
                "where  城市名称 like '%"+cityname+"%'";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/cityInfoByName")
    public R cityInfoByName(        @RequestParam("城市名称") String  cityname        )
    {
        String t=
                "SELECT  *  from t_spot where `日期` ='2024-02-17' and  `城市` like '%"+cityname+"%' order BY `销售量` desc ";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/commentDetail")
    public R commentDetail(        @RequestParam("城市名称") String  cityname        )
    {
        String t=
                "SELECT  *  from t_spot where `日期` ='2024-02-17' and  `城市` like '%"+cityname+"%' ";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/NameId")
    public R GetHotelByNameWriter(        @RequestParam("城市编号") String  cityid  ,
                                          @RequestParam("城市名称") String  cityname )
    {
//        System.out.println(name);

        String t="select   * from  t_city  " +
                "where  城市名称 like '%"+cityname+"%'"+"and 城市id like'%"+cityid+"%'";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);


    }
    @GetMapping(value="/cityId")
    public R GetHotelBybookName(        @RequestParam("城市编号") String  cityId        )
    {
        String t="select   * from  t_city  " +
                "where  城市id = "+cityId;
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/cityInfo")
    public R GetCityInfo()
    {

        String t="SELECT  `城市`,SUM(`评论数`) as `评论总数` from t_spot where `日期` ='2024-02-17' GROUP BY `城市` order BY `评论总数` desc LIMIT 10";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/userInfo")
    public R userInfo(@RequestParam("用户id") String  userid)
    {

        String t="SELECT * from t_userinfo where `用户id` ="+userid;
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/cityspotInfo")
    public R GetSpotInfo()
    {

        String t="SELECT  * from t_spot where `日期` ='2024-02-13' ";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/spotInfobydata")
    public R spotInfobydata()
    {

        String t="SELECT `城市`,SUM(`销售量`*`价格`) as 销售总额 from t_spot WHERE `日期`='2024-02-13' GROUP BY `城市` ORDER BY  销售总额 desc LIMIT 5 ";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/spotInfobydate")
    public R spotInfobydate()
    {

        String t="SELECT `日期`,SUM(`销售量`*`价格`) as 销售总额 from t_spot GROUP BY `日期` ORDER BY `日期` , 销售总额 desc ";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/spotsaleInfo")
    public R spotsaleInfo(@RequestParam("景点id") String  spotid)
    {

        String t="SELECT `日期`,`价格`,`销售量`,`评论数`,`评分` from t_spot where spuId in (\n" +
                "SELECT spuId from t_spot where `景点id`="+spotid+"\n" +
                ")";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/cityInfobydate")
    public R cityInfobydate(@RequestParam("城市名称") String  cityname)
    {

        String t="SELECT `日期`,SUM(`销售量`*`价格`) as 销售总额 from t_spot where `城市` like '%"+cityname+"%' GROUP BY `日期` ORDER BY `日期` , 销售总额 desc ";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/citysale")
    public R citysale(@RequestParam("城市名称") String  cityname)
    {

        String t="SELECT `标题`,`价格`  from t_spot where `城市` like '%"+cityname+"%' AND `日期`='2024-02-13' ORDER BY `价格` desc  ";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/spotInfoByName")
    public R spotInfoByName(        @RequestParam("标题") String  spotname        )
    {
        String t=
                "SELECT  *  from t_spot where `日期` ='2024-02-13' and  `标题` like '%"+spotname+"%' ";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/userinfo")
    public R userinfo()
    {
        String t=
                "SELECT  *  from t_userinfo  ";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/userinfobyusername")
    public R userinfobyusername(@RequestParam("username") String  spotname )
    {
        String t=
                "SELECT  *  from t_userinfo where 账户='"+spotname+"'";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/userinfobyusernameandnickname")
    public R userinfobyusernameandnickname(@RequestParam("username") String  spotname ,
                                           @RequestParam("nickname") String  nickname )
    {
        String t=
                "SELECT  *  from t_userinfo where 账户='"+spotname+"' and 用户名='"+nickname+"'";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/userinfobynickname")
    public R userinfobynickname(@RequestParam("nickname") String  spotname )
    {
        String t=
                "SELECT  *  from t_userinfo where 用户名 like '%"+spotname+"%'";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/spotInfoById")
    public R spotInfoById(        @RequestParam("景点id") String  spotId        )
    {
        String t="select   * from  t_spot  " +
                "where `日期` ='2024-02-13' and  景点id = "+spotId;
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/titleandId")
    public R titleandId(        @RequestParam("景点id") String  spotid  ,
                                          @RequestParam("标题") String  spotname )
    {
//        System.out.println(name);

        String t="select   * from  t_spot  " +
                "where `日期` ='2024-02-13' and  标题 like '%"+spotname+"%'"+"and 景点id like'%"+spotid+"%'";
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);


    }
    @PostMapping(value = "/addCity")
    public R addBook(@RequestParam("name") String name,
                     @RequestParam("cityurl") String cityurl
                     ){
        String t="insert into t_city(城市名称,城市URL) values ('"+name+"','"+cityurl+"')";
        System.out.println(t);
        MYSQL.executes(t);

        return R.ok().put("message","添加城市成功");
    }
    @PostMapping(value = "/adduserinfo")
    public R addBook(@RequestParam("username") String username,
                     @RequestParam("account") String account,
                     @RequestParam("password") String password,
                     @RequestParam("telephone") String telephone,
                     @RequestParam("birthday") String birthday,
                     @RequestParam("email") String email){
        //int years=2024- Year(birthday);
        SimpleDateFormat logindate=new SimpleDateFormat("yyyy-MM-dd");
        int years=2024-Integer.parseInt(birthday.substring(0,4));
        String t="insert into t_userinfo(身份编号,用户名,账户,密码,电话,出生日期,年龄,邮箱,注册日期) values ('2','"+username+"','"+account+"','"+password+"'," +
                "'"+telephone+"','"+birthday+"','"+years+"','"+email+"','"+logindate.format(new Date().getTime())+"')";
        System.out.println(t);

        boolean alist=MYSQL.executes(t);

        return R.ok().put("message",true);
    }
    @PostMapping(value = "/updateuserinfo")
    public R updateuserinfo(@RequestParam("username") String username,
                     @RequestParam("password") String password,
                     @RequestParam("telephone") String telephone,
                     @RequestParam("birthday") String birthday,
                     @RequestParam("email") String email,
                            @RequestParam("userid") String userid){
        //int years=2024- Year(birthday);
        SimpleDateFormat logindate=new SimpleDateFormat("yyyy-MM-dd");
        int years=2024-Integer.parseInt(birthday.substring(0,4));
        String t="update t_userinfo set 用户名='"+username+"',密码='"+password+"',电话="+telephone+",出生日期='"+birthday+"',邮箱='"+email+"'  where 用户id="+userid;
        System.out.println(t);

        boolean alist=MYSQL.executes(t);

        return R.ok().put("message",true);
    }
    @PostMapping(value = "/updateuserinfo2")
    public R updateuserinfo2(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam("telephone") String telephone,
                            @RequestParam("birthday") String birthday,
                            @RequestParam("email") String email,
                            @RequestParam("userid") String userid,
                             @RequestParam("indentity") String indentity){
        //int years=2024- Year(birthday);
        SimpleDateFormat logindate=new SimpleDateFormat("yyyy-MM-dd");
        int years=2024-Integer.parseInt(birthday.substring(0,4));
        String t="update t_userinfo set 用户名='"+username+"',密码='"+password+"',电话="+telephone+",出生日期='"+birthday+"',邮箱='"+email+"',身份编号="+indentity+"  where 用户id="+userid;
        System.out.println(t);

        boolean alist=MYSQL.executes(t);

        return R.ok().put("message",true);
    }
    @DeleteMapping(value = "/deleteCity" )
    public R deleteCity(@RequestParam("cityIndex") int cityIndex){

        String t="delete from t_city where 城市id= '"+cityIndex+"'";
        System.out.println(t);
        boolean alist=MYSQL.executes(t);
        return R.ok().put("message","删除成功");
    }
    @DeleteMapping(value = "/deleteSpot" )
    public R deleteSpot(@RequestParam("spotIndex") int spotIndex){

        String t="delete from t_spot where 景点id= '"+spotIndex+"'";
        System.out.println(t);
        boolean alist=MYSQL.executes(t);
        return R.ok().put("message","删除成功");
    }
    @PostMapping(value = "/UpdateCity")
    public R UpdateBook(@RequestParam("cityName") String cityName,
                        @RequestParam("cityurl") String cityurl,
                        @RequestParam("indexs") String indexs){
        String t="update t_city set 城市名称='"+cityName+"',城市URL='"+cityurl+"'  where 城市id='"+indexs+"'";
        System.out.println(t);
        MYSQL.executes(t);
        return R.ok().put("message","修改城市成功");
    }
    @PostMapping(value = "/insertCom")
    public R insertCom(@RequestParam("spotid") String spotid,
                        @RequestParam("userid") String userid,
                        @RequestParam("score") String score,
                       @RequestParam("evaluate") String evaluate){
        String t="insert into t_comment(景点id,用户id,评分,评论) values ('"+spotid+"','"+userid+"','"+score+"','"+evaluate+"')";
        System.out.println(t);
        MYSQL.executes(t);
        return R.ok().put("message","添加评论成功");
    }
    @GetMapping(value="/getCom")
    public R getCom(        @RequestParam("景点id") String  spotId        )
    {
        String t="select   * from  t_comment  " +
                "where  景点id = "+spotId;
        List alist=
                MYSQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @DeleteMapping(value = "/deleteUser" )
    public R deleteUser(@RequestParam("userid") int userid){

        String t="delete from t_userinfo where 用户id= '"+userid+"'";
        System.out.println(t);
        boolean alist=MYSQL.executes(t);
        return R.ok().put("message","删除成功");
    }
}
