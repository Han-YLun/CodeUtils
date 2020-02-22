package cn.arvinyl.generate.utils;

import cn.arvinyl.generate.entity.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author: hyl
 * @date: 2020/02/22
 **/
public class DataBaseUtils {
    
    //获取数据库连接
    public static Connection getConnection(DataBase db) throws Exception {
        Properties props = new Properties();
        props.put("remarksReporting" , "true");
        props.put("user" , db.getUserName());
        props.put("password" , db.getPassWord());
        //获取连接
        Class.forName(db.getDriver());
        return DriverManager.getConnection(db.getUrl() , props);
    }

    //获取数据库列表
    public static List<String> getSchemas(DataBase db) throws Exception {
        //1.获取元数据
        Connection conn = getConnection(db);
        DatabaseMetaData metaData = conn.getMetaData();
        //2.获取所有数据库列表
        ResultSet rs = metaData.getCatalogs();
        List<String> list = new ArrayList<>();
        while (rs.next()){
            list.add(rs.getString(1));
        }
        rs.close();
        conn.close();

        return list;
    }
}
