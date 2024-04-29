package com.example.demo.config;

//import org.apache.hadoop.hive.ql.exec;
import org.json.JSONObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HIVE {


    private  static
    String url = "jdbc:hive2://192.168.206.121:10000/trip;user=hadoop;password=hadoop;useUnicode=true;characterEncoding=UTF-8";
//sa身份连接


    public static List GetResults(String   executesql  ) {

        Connection con = null;  //会话连接
        Statement stmt = null;  //用于执行静态SQL语句并返回其生成的结果的对象。
        ResultSet rs = null;  //数据库结果集的数据表
        List js =null;
        try {
            //1-注册驱动器,驱动管理器类加载SQLServerDriver类的静态方法，如果没有添加这个驱动，则创建这个驱动
            Class.forName("org.apache.hive.jdbc.HiveDriver");

            //2-与数据源获得连接
            con = DriverManager.getConnection(url);

            //3-创建一个Statement对象，用于将SQL语句发送到数据库
            stmt = con.createStatement();

            //4- SQL语句

            //5-执行SQL,返回数据
            rs = stmt.executeQuery(executesql);
            js = convertList(rs);

//            String str = JSON.toJSONString(js); // List转json
//            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            if (con != null)
                try {
                    con.close();
                } catch (Exception e) {
                }
        }
        return  js;
    }
//    public class JsonParse extends UDF {
//
//        public String evaluate(String jsonString) {
//            JSONObject jsonObject = new JSONObject(jsonString);
//            return jsonObject.toString();
//        }
    //ResultSet转为List
    private static List convertList(ResultSet rs) throws SQLException {

        List list = new ArrayList();

        ResultSetMetaData md = rs.getMetaData();

        int columnCount = md.getColumnCount(); //Map rowData;

        while (rs.next()) {

            Map rowData = new HashMap();

            for (int i = 1; i <= columnCount; i++) {

                rowData.put(md.getColumnName(i), rs.getObject(i));

            }

            list.add(rowData);

        }
        return list;

    }
}

