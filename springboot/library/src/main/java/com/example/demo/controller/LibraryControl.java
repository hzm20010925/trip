package com.example.demo.controller;


import com.example.demo.config.R;
import com.example.demo.config.SQL;
import com.example.demo.form.HelloForm;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin   //开启支持跨域请求
@RequestMapping("/Library/")
@RestController
public class LibraryControl {


    @GetMapping(value="/{id}")
    public R GetHotel(         @PathVariable("id") String  id          )
    {
        List alist=
                SQL.GetResults("select   * from  书籍资产  " +
                        "where  书籍编号= "+id);

        return  R.ok().put("data",alist);
    }

    @GetMapping(value="/select")
    public R GetBookList(            )
    {
        List alist=
                SQL.GetResults("select   * from  书籍资产 ");

        return  R.ok().put("data",alist);
    }


    @GetMapping(value="/Writer")
    public R GetHotelByWriter(        @RequestParam("作者") String  id        )
    {
        System.out.println(id);
        String t="select   * from  书籍资产  " +
                "where  作者 like'%"+id+"%'";
        List alist=
                SQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/bookName")
    public R GetHotelBybookName(        @RequestParam("图书名") String  bookname        )
    {
        String t="select   * from  书籍资产  " +
                "where  书籍名 like '%"+bookname+"%'";
        List alist=
                SQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }
    @GetMapping(value="/NameWriter")
    public R GetHotelByNameWriter(        @RequestParam("书名") String  name  ,
                                    @RequestParam("作者") String  if_check )
    {
        System.out.println(name);

            String t="select   * from  书籍资产  " +
                    "where  书籍名 like '%"+name+"%'"+"and 作者 like'%"+if_check+"%'";
            List alist=
                    SQL.GetResults(t);
            System.out.println(t);
            return  R.ok().put("data",alist);


    }
    @GetMapping(value="/type")
    public R GetType(          )
    {
        String t="select   * from  书籍类型  " ;
                ;
        List alist=
                SQL.GetResults(t);
        System.out.println(t);
        return  R.ok().put("data",alist);
    }




    @PostMapping(value = "/UpdateBook")
    public R UpdateBook(@RequestParam("bookname") String bookname,
                        @RequestParam("writer") String writer,
                        @RequestParam("cbs") String cbs,
                        @RequestParam("sum") String sums,
                        @RequestParam("indexs") String indexs){
        String t="update 书籍资产 set 作者='"+writer+"',书籍名='"+bookname+"',出版社='"+
                cbs+"',库存数='"+sums+"'  where 书籍编号='"+indexs+"'";
        System.out.println(t);
        SQL.GetResults(t);
        return R.ok().put("message","修改书籍成功");
    }

    @PostMapping(value = "/borrowBook")
    public R borrowBook(@RequestParam("indexs") String indexs,
                        @RequestParam("bookName") String bookName,
                        @RequestParam("writer") String writer,
                        @RequestParam("borrowerIndex") String borrowerIndex,
                        @RequestParam("borrower") String borrower){

        Calendar borrowrDate=Calendar.getInstance();
        borrowrDate.add(Calendar.MONTH,1);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String t="update 书籍资产 set 借阅时间='"+format.format(Calendar.getInstance().getTime())
                +"',最大归还时间='"+format.format(borrowrDate.getTime())+"'  where 书籍编号='"+indexs+"'";
        System.out.println(t);
        String b="insert into 借阅记录(书籍编号,书籍名称,作者,借阅人编号,借阅人,借阅时间,最大归还时间,是否归还) values ('"+indexs+"','"+bookName+"','"
                +writer+"','"+borrowerIndex+"','"+borrower+"','"+
                format.format(Calendar.getInstance().getTime())+
                "','"+format.format(borrowrDate.getTime())+"','否')";
        SQL.GetResults(t);
        SQL.GetResults(b);

        return R.ok().put("message","借阅书籍成功");
    }

    @PostMapping(value = "/returnBook")
    public R returnBook(@RequestParam("bookName") String bookName,
                        @RequestParam("indexs") String indexs,
                        @RequestParam("Date") String borrowDate,
                        @RequestParam("borrowerIndex") String borrowerIndex,
                        @RequestParam("borrower") String borrower){

        Calendar borrowrDate=Calendar.getInstance();
        borrowrDate.add(Calendar.MONTH,1);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String t="update 借阅记录 set 归还时间='"+format.format(Calendar.getInstance().getTime())
                +"',是否归还='是'  where 书籍编号='"+indexs+"'";

        String b="insert into 归还记录(书籍名称,借阅编号,借阅人,借阅时间,归还时间,是否超期) values ('"+bookName+"','"+borrowerIndex+"','"
                +borrower+"','"+borrowDate+"','"+
                format.format(Calendar.getInstance().getTime())+ "','否')";
        String qq="update 书籍资产 set 最大归还时间=null,借阅时间=null  where 书籍编号='"+indexs+"'";
        System.out.println(qq);
        SQL.GetResults(qq);
        SQL.GetResults(t);
        SQL.GetResults(b);

        return R.ok().put("message","归还书籍成功");
    }


    @GetMapping(value = "/login" )
    public R loginPage(@RequestParam("username") String username,
                              @RequestParam("password")  String password){

        String t="select * from 用户 where 用户名='"+username+"' and 登录密码="+password;
        System.out.println(t);
        List alist=SQL.GetResults(t);
        return R.ok().put("data",alist);
    }

    @GetMapping(value = "/borrow" )
    public R borrow(){

        String t="select * from 借阅记录 ";

        List alist=SQL.GetResults(t);
        return R.ok().put("data",alist);
    }

    @GetMapping(value = "/return" )
    public R returnBook(){

        String t="select * from 归还记录 ";
        System.out.println(t);
        List alist= SQL.GetResults(t);
        return R.ok().put("data",alist);
    }
    @DeleteMapping(value = "/deleteBook" )
    public R deleteBook(@RequestParam("bookIndex") int bookIndex){

        String t="delete from 书籍资产 where 书籍编号= '"+bookIndex+"'";
        System.out.println(t);
        List alist=SQL.GetResults(t);
        return R.ok().put("message","删除成功");
    }

    @GetMapping(value = "/home")
    public R GetLoginInfo(@RequestParam("username") int username,
                               @RequestParam("password") String password){
        String t="select * from dbo.Business60('"+username+"','"+password+"')";

        System.out.println(t);
        List alist=SQL.GetResults(t);

        if(alist.size()>0){
            List userinfomation=SQL.GetResults("select * from 用户 " +
                    "where 用户名="+"'"+username+"'"+" and 登录密码='"+password+"'");
            R returnList=R.ok().put("data",alist);
            returnList.put("user",userinfomation);
            return returnList.put("token","asfdsdafsadf");
        }else{
            return R.error().put("message","用户名或密码错误");
        }


    }

    @PostMapping(value = "/addBook")
    public R addBook(@RequestParam("name") String name,
                      @RequestParam("writer") String writer,
                      @RequestParam("sum") String sum,
                     @RequestParam("cbs") String cbs,
                     @RequestParam("type") String type){
        String t="insert into 书籍资产(书籍名,库存数,作者,出版社,书籍类型) values ('"+name+"','"+sum+"','"
                +writer+"','"+cbs+"','"+type+"'"+")";
        System.out.println(t);
        List alist=SQL.GetResults(t);

        return R.ok().put("message",alist);
    }

    @PostMapping(value="/")
    public R AddHotel(@RequestParam Map<String, Object> params)
    {
        String  t="  '{0},''{1}'',''{2}'',''{3}'' ' ";

        t=t.replace("{0}",params.get("hotelid") .toString());
        t=t.replace("{1}",params.get("hotelname") .toString());
        t=t.replace("{2}",params.get("hoteladdr") .toString());
        t=t.replace("{3}",params.get("hotelpic") .toString());

        System.out.println(t);
//
        SQL.GetResults(" exec   CommonInsert '酒店表',\n" +
                "\t  '酒店Id,酒店名称,酒店地址,酒店图片', \n" +
                "\t  "+t);



        return  R.ok().put("message","添加酒店成功");
    }
}