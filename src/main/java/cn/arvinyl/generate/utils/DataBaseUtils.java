package cn.arvinyl.generate.utils;

import cn.arvinyl.generate.entity.Column;
import cn.arvinyl.generate.entity.DataBase;
import cn.arvinyl.generate.entity.Table;
import cn.arvinyl.generate.ui.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.XMLFormatter;

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

    //获取数据库中的表和字段,构造实体类
    public static List<Table> getDbInfo(DataBase db) throws Exception {
        //1.获取连接
        Connection conn = getConnection(db);
        //2.获取元数据
        DatabaseMetaData metaData = conn.getMetaData();
        //3.获取当前数据库的所有表
        ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});

        List<Table> list = new ArrayList<>();
        while (tables.next()){
            Table table = new Table();
            //表名
            String tableName = tables.getString("TABLE_NAME");
            //类名
            String className = removePrefix(tableName);
            //描述
            String remarks = tables.getString("REMARKS");
            //主键
            ResultSet primaryKeys =  metaData.getPrimaryKeys(null, null , tableName);

            String keys = "";
            while (primaryKeys.next()){
                String keyName = primaryKeys.getString("COLUMN_NAME");
                keys += keyName+",";
            }

            table.setName(tableName);
            table.setName2(className);
            table.setComment(remarks);
            table.setKey(keys);
            //处理表中的所有字段
            ResultSet columns = metaData.getColumns(null, null, tableName, null);
            List<Column> columnList = new ArrayList<>();
            while (columns.next()){
                Column cn = new Column();
                //构造column对象
                //列名称
                String columnName = columns.getString("COLUMN_NAME");
                cn.setColumnName(columnName);
                //属性名
                String attrName = StringUtils.toJavaVariableName(columnName);
                cn.setColumnName2(attrName);
                //java类型和数据库类型
                String dbType = columns.getString("TYPE_NAME");
                String javaType = PropertiesUtils.customMap.get(dbType);
                cn.setColumnDbType(dbType);
                cn.setColumnType(javaType);
                //备注
                String columnRemark = columns.getString("REMARKS");
                cn.setColumnComment(columnRemark);
                //是否主键
                String pri = null;
                if (StringUtils.contains(columnName , keys.split(","))){
                    pri = "PRI";
                }
                cn.setColumnKey(pri);
                columnList.add(cn);
            }
            columns.close();
            table.setColumns(columnList);
            list.add(table);
        }

        tables.close();
        conn.close();
        return list;
    }

    public static String removePrefix(String tableName){
        String prefixes = PropertiesUtils.customMap.get("tableRemovePrefixes");
        String temp = tableName;
        for (String pf : prefixes.split(",")){
            temp = StringUtils.removePrefix(temp , pf , true);
        }
        return StringUtils.makeAllWordFirstLetterUpperCase(temp);
    }

    public static void main(String[] args) throws Exception {
        DataBase db = new DataBase("MYSQL" , "ihrm");
        db.setUserName("root");
        db.setPassWord("123456");

        List<Table> dbInfo = DataBaseUtils.getDbInfo(db);
        for (Table table : dbInfo) {
            System.out.println(table);
        }
    }
}
