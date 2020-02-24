package cn.arvinyl.generate.core;

import cn.arvinyl.generate.utils.FileUtils;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.List;
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
    public void scanAndGenerator(Map<String,Object> dataModel) throws Exception {
        //1.根据模版路径找到此路径下的所有模版文件
        List<File> fileList = FileUtils.searchAllFile(new File(templatePath));
        //2.对每个模版进行文件生成
        for (File file : fileList) {
            executeGenertor(dataModel , file);
        }

    }

    /**
     * 对模版进行文件生成
     * @param dataModel 数据模型
     * @param file      模版文件
     */
    private void executeGenertor(Map<String, Object> dataModel, File file) throws Exception {
        //1.文件路径处理
        String templateFileName = file.getAbsolutePath().replace(this.templatePath , "");
        String outFilePath = processTemplateString(templateFileName , dataModel);
        //2.读取文件模版
        Template template = cfg.getTemplate(templateFileName);
        template.setOutputEncoding("utf-8");//指定生成文件的字符集编码
        //3.创建文件
        File mkFile = FileUtils.mkdir(outPath, outFilePath);
        //4.模版处理(文件生成)
        FileWriter fw = new FileWriter(mkFile);
        template.process(dataModel , fw);
    }


    public String processTemplateString(String templateString,Map dataModel) throws Exception {
        StringWriter out = new StringWriter();
        Template template = new Template("ts", new StringReader(templateString), cfg);
        template.process(dataModel , out);
        return out.toString();
    }

    public static void main(String[] args) throws Exception {
        String templatePath = "D:\\p\\模版";
        String outPath = "D:\\p\\代码生成";

        Map<String,Object> dataModel = new HashMap<>();
        dataModel.put("username","张三");
        new Generator(templatePath , outPath).scanAndGenerator(dataModel);
    }
}
