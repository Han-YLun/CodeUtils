package cn.arvinyl.generate.core;

import cn.arvinyl.generate.entity.DataBase;
import cn.arvinyl.generate.entity.Settings;
import cn.arvinyl.generate.entity.Table;
import cn.arvinyl.generate.ui.DatabaseUtil;
import cn.arvinyl.generate.utils.DataBaseUtils;
import cn.arvinyl.generate.utils.PropertiesUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: hyl
 * @date: 2020/02/23
 **/
public class GeneratorFacade {

    private String templatePath;
    private String outPath;
    private Settings settings;
    private DataBase db;
    private Generator generator;

    public GeneratorFacade(String templatePath, String outPath, Settings settings, DataBase db) throws IOException {
        this.templatePath = templatePath;
        this.outPath = outPath;
        this.settings = settings;
        this.db = db;
        this.generator = new Generator(templatePath , outPath);
    }

    
    public void generatorByDataBase() throws Exception {
        List<Table> tables = DataBaseUtils.getDbInfo(db);
        for (Table table : tables) {
            //对每一个Table对象进行代码生成
            Map<String,Object> dataModel = getDataModel(table);
            generator.scanAndGenerator(dataModel);
        }
    }

    /**
     * 根据table对象生成数据模型
     * @param table
     * @return
     */
    private Map<String, Object> getDataModel(Table table) {
        Map<String,Object> dataModel = new HashMap<>();
        //1.自定义配置
        dataModel.putAll(PropertiesUtils.customMap);
        //2.元数据
        dataModel.put("table" , table);
        //3.setting
        dataModel.putAll(this.settings.getSettingMap());
        //4.类型
        dataModel.put("ClassName" , table.getName2());

        return dataModel;
    }
}
