package cn.arvinyl.generate.core;

import cn.arvinyl.generate.utils.FileUtils;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 代码生成器的核心处理类
 *      ：使用Freemarker完成文件生成(数据模型+模版)
 * @author: hyl
 * @date: 2020/02/23
 **/
public class Generator {

    private String templatePath;//模版路径
    private String outPath;//代码生成路径
    private Configuration cfg;

    public Generator(String templatePath, String outPath) throws IOException {
        this.templatePath = templatePath;
        this.outPath = outPath;
        //实例化Configuration对象
        cfg = new Configuration();
        //指定模版加载器
        FileTemplateLoader ftl = new FileTemplateLoader(new File(templatePath));
        cfg.setTemplateLoader(ftl);
    }

    /**
     * 代码生成
     *      1.扫描模版路径下的所有模版
     *      2.对每个模版进行文件生成
     */
    public void scanAndGenerator(Map<String,Object> dataModel) throws IOException {
        //1.根据模版路径找到此路径下的所有模版文件
        FileUtils.searchAllFile(new File(templatePath));
        
    }
}
