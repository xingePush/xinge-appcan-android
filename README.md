# XG-AppCan-Android

# 概述
- 本项目是信鸽在AppCan发布的插件[uexXGPush](http://plugin.appcan.cn/details.html#462_index)的Android版本源码。
- 基于的信鸽SDK版本是2.4.2。

# APICloud模块开发说明

本工程是基于Eclipse开发的，请先了解APICloud的插件开发流程，参见AppCan的[AndroidNative插件扩展机制开发文档](http://newdocx.appcan.cn/newdocx/docx?type=1366_1291)

## src目录
信鸽uexXGPush插件的java源码，最终编译成**plugin_uexXGPush.jar**

## assets目录
AppCan的demo界面，不参与生成最后的模块

## libs目录
AppCan相关的libs以及信鸽推送用到的libs

## exportplugin目录
目录下的uexXGPush-1.0.3.zip是最后发布的插件，是uexXGPush目录的压缩包

# 运行
该工程直接运行就是一个相当于使用信鸽SDK的AppCan的Android App Demo。

# 导出模块

导出模块包操作关键步骤：

1. 导出所有代码与你模块相关的代码文件到jar包里。操作步骤：File -> Export ->JAR file ->勾选需要导出代码及目录，选择src目录，导出plugin_uexXGPush.jar，然后将plugin_uexXGPush.jar放到uexXGPush目录的jar目录下
2. 使用命令``dx --dex --output=plugin_uexXGPush_dex.jar plugin_uexXGPush.jar``将plugin_uexXGPush.jar打包成plugin_uexXGPush_dex.jar，然后将plugin_uexXGPush_dex.jar放到uexXGPush目录的dex目录下
3. 复制libs文件夹下信鸽相关的jar包（jg、wup、Xg_sdk这3个jar）到uexXGPush目录的jar目录下
4. 把信鸽相关的so包(libtpnsSecurity.so)复制到uexXGPush目录的so目录下
5. 修改uexXGPush目录的AndroidManifest.xml文件，将**org.zywx.wbpalmstar.widgetone.uexDemo**改成app对应的包名
6. 将uexXGPush目录压缩成zip格式
