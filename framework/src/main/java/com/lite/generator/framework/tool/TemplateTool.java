package com.lite.generator.framework.tool;

import com.lite.generator.framework.el.Context;
import com.lite.generator.framework.model.Config;
import com.lite.generator.framework.model.Project;
import com.lite.generator.framework.template.TemplateConfig;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

public class TemplateTool {

    public static void generate(String templateFile, String outFile, Map<String,Object> dataMap){
        try {
            int index = templateFile.lastIndexOf(SystemVariable.fileSeparator);
            String path = templateFile.substring(0, index);
            String template = templateFile.substring(index + 1);
            Properties properties = new Properties();
            properties.setProperty("resource.loader", "file");
            properties.setProperty("file.resource.loader.path", path);
            properties.setProperty("input.encoding", "UTF-8");
            properties.put("output.encoding", "UTF-8");
            properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
            VelocityEngine ve = new VelocityEngine();
            ve.init(properties);
            Template t = ve.getTemplate(template);
            VelocityContext context = new VelocityContext();
            dataMap.forEach((k, v) -> context.put(k, v));
            StringWriter writer = new StringWriter();
            t.merge(context, writer);
            FileOutputStream fos = new FileOutputStream(outFile);
            PrintStream ps = new PrintStream(fos, true, "UTF-8");
            ps.print(writer.toString());
            ps.flush();
            ps.close();
            fos.close();
        }
        catch(Exception exception){
            throw new RuntimeException("加载模板失败", exception);
        }
    }

    public static void generate(Config generateConfig, Project project){
        TemplateConfig templateConfig = generateConfig.getTemplateConfig();
        Context context = new Context();
        context.setVariable("config", generateConfig);
        context.setVariable("project", project);
        templateConfig.execute(context);
    }

}
