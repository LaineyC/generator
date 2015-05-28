代码生成器：根据项目组织结构自定义代码生成规则生成代码，摆脱重复代码的编码工作。
通过二次开发和配置最终完成一个代码生成器，达到生成目标代码的目的。

<html lang="zh-CN">
<body>
<div class="panel panel-primary">
    <div class="panel-heading">启动设置</div>
    <div class="panel-body">
        <p class="bg-info">首次启动时，设定好生成器模型数据的属性描述（一个属性描述对象相当于一列），设置后建议不能修改。</p>
        <p class="bg-info"><code>名称：</code>列的英文名称，生成代码需要用到，必输项。</p>
        <p class="bg-info"><code>注释（列标题）：</code>列的标题，必输项。</p>
        <p class="bg-info"><code>显示宽度：</code>列的宽度，必输项。</p>
        <div class="bg-info"><code>输入类型：</code>列单元格输入类型，必输项。
            <p class="bg-info">Boolean：Java类型小boolean，编辑器类型CheckBox。如果参考值用,隔开，前表示true显示的文字，后表示false显示的文字，如：是,否。</p>
            <p class="bg-info">Integer：Java类型Integer，编辑器类型TextField。</p>
            <p class="bg-info">Double：Java类型Double，编辑器类型TextField。</p>
            <p class="bg-info">String：Java类型String，编辑器类型TextField。如果参考值用,隔开，编辑器类型为可编辑的ComboBox。</p>
            <p class="bg-info">Date：Java类型Date，编辑器类型DatePicker。</p>
            <p class="bg-info">Enum：Java类型String，编辑器类型ChoiceBox。</p>
            <p class="bg-info">Group：Java类型Group，编辑器类型TreeView。</p>
        </div>
        <p class="bg-info"><code>默认值：</code>列单元格添加时默认值。</p>
        <p class="bg-info"><code>验证脚本（El表达式）：</code>列单元格的保存提交时的验证规则，用El表达式自定义。
            EL表达式取得列单元格的值用名称，如：如果列名称为name，EL表达式可以为fn:length(name)>5，表示这一列输入值长度必须大于5。</p>
        <p class="bg-info"><code>验证提示：</code>验证脚本验证失败时的提示信息。</p>
        <p class="bg-info"><code>参考值：</code>主要表示Enum类型时的值，多个值用,隔开。</p>
    </div>
</div>
<div class="panel panel-primary">
    <div class="panel-heading">生成设置</div>
    <div class="panel-body">
        <p class="bg-info"><code>生成路径：</code>代码生成的根路径。</p>
        <p class="bg-info"><code>生成分组：</code>生成代码时，临时选择的分组。未勾选，则不参与代码生成。</p>
    </div>
</div>
<div class="panel panel-primary">
    <div class="panel-heading">快捷设置</div>
    <div class="panel-body">
        <p class="bg-info">设置快捷属性后，可在添加模型数据时直接添加预先编辑的快捷属性。</p>
    </div>
</div>
<div class="panel panel-primary">
    <div class="panel-heading">模板设置</div>
    <div class="panel-body">
        <p class="bg-info">同一份生成器的数据，可以选择不同的模板生成策略生成不同的代码。</p>
    </div>
</div>
<div class="panel panel-primary">
    <div class="panel-heading">模板编写</div>
    <div class="panel-body">
        <p class="bg-info">同一份生成器的数据，可以选择不同的模板生成策略生成不同的代码。</p>
        <p class="bg-info">模板生成策略文件的所有数据都来自生成器。</p>
        <p class="bg-info">模板生成策略文件必须放入templates文件夹下，所有的模板文件必须templates文件夹或者子文件下，模板用velocity。</p>
        <p class="bg-info"><code>数据结构：</code></p>
        <pre>
config = {
    <code>&lt;!-- 文件生成根路径 --></code>
    generatePath
    <code>&lt;!-- 快捷属性，是Map结构，key根据启动设置来确定 --></code>
    quickProperties:[]
    <code>&lt;!-- 启动设置对应对象 --></code>
    propertyFeatures:[
        {
            <code>&lt;!-- 名称 --></code>
            name
            <code>&lt;!-- 注释（列标题） --></code>
            comment
            <code>&lt;!-- 显示宽度 --></code>
            viewWidth
            <code>&lt;!-- 输入类型 --></code>
            type
            <code>&lt;!-- 默认值 --></code>
            defaultValue
            <code>&lt;!-- 验证脚本（EL表达式） --></code>
            checkStatement
            <code>&lt;!-- 验证提示 --></code>
            checkMessage
            <code>&lt;!-- 参考值 --></code>
            referenceValues
        }
    ],
    <code>&lt;!-- 生成策略配置文件 --></code>
    templateConfig:{
        <code>&lt;!-- 是否被应用 --></code>
        apply
        <code>&lt;!-- 文件名 --></code>
        name
        <code>&lt;!-- 策略作用描述 --></code>
        description
    }
}

project，module，model继承于group（项目、模块、模型，抽象成分组），组合模式。
group = {
    <code>&lt;!-- 主键 --></code>
    id
    <code>&lt;!-- 名称 --></code>
    name
    <code>&lt;!-- 注释 --></code>
    comment
    <code>&lt;!-- 描述 --></code>
    description
    <code>&lt;!-- 父分组 --></code>
    parent
    <code>&lt;!-- 子分组--></code>
    children:[]
}

<code>&lt;!-- 项目 --></code>
project = {
    <code>&lt;!-- 子分组：可以是module和model --></code>
    children:[]
}

<code>&lt;!-- 模块 --></code>
module = {
    <code>&lt;!-- 子分组：可以是module和model --></code>
    children:[]
}

<code>&lt;!-- 模型 --></code>
model = {
    <code>&lt;!-- 快捷属性，是Map结构，key根据启动设置来确定 --></code>
    properties:[]
}
        </pre>
        <p class="bg-info"><code>模板生成策略文件：</code></p>
        <p class="bg-info">顶级变量config和project</p>
        <pre>
&lt;?xml version="1.0" encoding="UTF-8"?>
<code>&lt;!-- global：取得配置数据的顶级key description：描述模板策略的作用 --></code>
&lt;template-config description="架构代码模板">
    <code>&lt;!-- file：生成一个文件 name:文件名称 template：生成文件用的模板 --></code>
    &lt;file name="数据字典.html" template="framework/data-dictionary.vm">
        <code>&lt;!-- template-context：模板文件的生成文件所需的上下文 --></code>
        &lt;template-context var="config" value="${config}"/>
        &lt;template-context var="date" value="${fn:newInstance('org.apache.velocity.tools.generic.DateTool')}"/>
    &lt;/file>
    <code>&lt;!-- folder：生成一个文件夹 name：文件夹名称 --></code>
    &lt;folder name="com">
        &lt;folder name="lite">
            &lt;folder name="app">
                &lt;folder name="${fn:toLowerCase(project.name)}">
                    &lt;folder name="service">
                        <code>&lt;!-- foreach：for循环 item：迭代时变量名 items：迭代的集合，支持List，Map，Array --></code>
                        <code>&lt;!-- status：迭代状态对象，有属性first：是否第一次迭代 last：是否最后一次 index：索引，0开始 count：索引，1开始 current：当前迭代项 --></code>
                        &lt;foreach item="model" items="${project.children}" status="status">
                            &lt;file name="${fn:toUpperCaseFirst(model.name)}Service.java" template="framework/service.vm">
                                &lt;template-context var="config" value="${config}"/>
                                &lt;template-context var="model" value="${model}"/>
                                &lt;template-context var="date" value="${fn:newInstance('org.apache.velocity.tools.generic.DateTool')}"/>
                            &lt;/file>
                            <code>&lt;!-- if：条件判断 --></code>
                            &lt;if test="${status.index % 2 == 0}">
                                <code>&lt;!-- continue：结束本次循环，只能用于foreach里 --></code>
                                &lt;continue/>
                            &lt;/if>
                            &lt;if test="${status.index % 2 == 0}">
                                <code>&lt;!-- break：跳出循环，只能用于foreach里 --></code>
                                &lt;break/>
                            &lt;/if>
                        &lt;/foreach>
                    &lt;/folder>
                &lt;/folder>
            &lt;/folder>
        &lt;/folder>
    &lt;/folder>
    <code>&lt;!-- function：定义一个函数 name：函数名 argument[i]：形参参数名，i从1开始 --></code>
    &lt;function name="out" argument1="projectName" argument2="project" argument[i]...>
            <code>&lt;!-- var：定义一个变量 name：变量名 value：变量值 --></code>
            &lt;var name="pn" value="${projectName}"/>
            <code>&lt;!-- out：输出值到控制台 value：输出值 --></code>
            &lt;out value="${pn}"/>
            <code>&lt;!-- call：调用一个函数 function：函数名 argument[i]：实参，i从1开始 --></code>
            &lt;call function="out" argument1="${project.name}" argument2="${project}" argument[i].../>
            <code>&lt;!-- return：返回函数 --></code>
            &lt;return/>
    &lt;/function>
&lt;/template-config>
        </pre>
        <p class="bg-info" style="color: darkred;font-weight: bold;">因为都是JavaFx封装的可观察类型，所以数据结构中的Map取值都要用${对象名.key.value}</p>
        <p class="bg-info"><code>velocity模板文件：</code></p>
        <pre>
&lt;!DOCTYPE html>
&lt;html>
    &lt;head>
        &lt;title>Hello,World!&lt;/title>
    &lt;/head>
    &lt;body>
        &lt;h1>project.name - $project.comment&lt;/h1>
        &lt;h4>生成日期:$date.get('yyyy-MM-dd HH:mm:ss')&lt;/h4>
    #foreach($module in $project.children)
        &lt;div>$velocityCount.$!module.name&lt;/div>
    #end
    &lt;/body>
&lt;/html>
        </pre>
    </div>
</div>
<div class="panel panel-primary">
    <div class="panel-heading">插件扩展</div>
    <div class="panel-body">
        <div class="bg-info"><code>编译代码目录：</code>plugins\classes
            <p class="bg-info">扩展EL表达式：类注解com.lite.generator.framework.el.Function。</p>
            <pre>
<code>package</code> com.lite.generator.framework.el;

<code>import</code> java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
<code>public</code> @<code>interface</code> Function {

    //表达式库前缀
    String prefix();

}
<code>--------------</code>
<code>package</code> <code>......</code>

<code>import</code> <code>......</code>
<code>......</code>

@com.lite.generator.framework.el.Function(prefix = "fn")
<code>public</code> <code>class</code> Function {

    <code>public</code> <code>static</code> String trim(String input) {
        <code>if</code> (input == <code>null</code>) <code>return</code> "";
            <code>return</code> input.trim();
    }
    <code>......</code>
}
<code>--------------</code>
${fn:trim(string)}
            </pre>
        </div>
        <p class="bg-info"><code>依赖Jar包目录：</code>plugins\library</p>
    </div>
</div>
<div class="panel panel-primary">
    <div class="panel-heading">快捷键</div>
    <div class="panel-body">
        <p class="bg-info"><code>删除：</code>Ctrl + D</p>
        <p class="bg-info"><code>撤销：</code>Ctrl + Z</p>
        <p class="bg-info"><code>重做：</code>Ctrl + Shift + Z </p>
    </div>
</div>
</body>
</html>
