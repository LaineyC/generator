package com.lite.generator.framework.tool;

import com.lite.generator.framework.util.FileUtil;
import java.io.File;

public class SystemVariable {

    public final static String fileSeparator = File.separator;

    public final static String projectPath = FileUtil.getProjectPath();

    public final static String templateFolder = "templates";

    public final static String templatePath = projectPath + fileSeparator + templateFolder;

    public final static String configFolder = "configs";

    public final static String configPath = projectPath + fileSeparator + configFolder;

    public final static String projectConfigFolder = "project";

    public final static String projectConfigPath = projectPath + fileSeparator + projectConfigFolder;

    public final static String groupFolder = "groups";

    public final static String logFolder = "logs";

    public final static String logPath  = projectPath + fileSeparator + logFolder;

    public final static String pluginFolder = "plugins";

    public final static String pluginPath  = projectPath + fileSeparator + pluginFolder;

    public final static String classFolder = pluginFolder + fileSeparator + "classes";

    public final static String classPath  = projectPath + fileSeparator + classFolder;

    public final static String libraryFolder = pluginFolder + fileSeparator + "library";

    public final static String libraryPath  = projectPath + fileSeparator + libraryFolder;

    public SystemVariable(){

    }

}
