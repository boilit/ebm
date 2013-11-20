Engine Benchmark
===
<pre>
目前网络上的Java模板引擎测试基本上都是非独立JVM测试的，
这样做后测试的引擎性能会较高，与实际性能相比有较大偏差，
因此本测试对每个引擎都使用独立JVM测试，保证了各个引擎间环境的公平性；

以下是2.0.0的测试方法：

1、修改properties文件，属性参考properties文件内的注释；
2、执行Benchmark的main方法，将在类路径下产生report.html结果；

</pre>

使用
===
###1.编译
<pre>
mvn clean install
</pre>

###2.修改参数
+ /target/classes/benchmark.bat
+ /target/classes/benchmark.properties

###3.运行
+ Windows 下执行
<pre>
/target/classes/benchmark.bat
</pre>
+ Linux暂未支持

参考资料
===
<pre>
交流QQ群：109365467

测试结果：<a href="http://boilit.github.io/bsl/zh/ability/jdk7utf8.html">测试结果</a>

引擎参考：
  BSL
    项目地址:https://github.com/boilit/bsl
    官方地址:http://boilit.github.io/bsl
  Webit-Script
    项目地址:https://github.com/zqq90/webit-script
    官方地址:http://zqq90.github.io/webit-script
  JetBrick-Template
    项目地址:https://github.com/subchen/jetbrick-template
    官方地址:http://subchen.github.io/jetbrick-template
  HTTL
    项目地址:https://github.com/httl/httl
    官方地址:http://httl.github.io
  Beetl
    官方地址:http://ibeetl.com/wordpress
  Rythm
    官方地址:http://rythmengine.org
  Velocity
    官方地址:http://velocity.apache.org
  FreeMarker
    官方地址:http://freemarker.org
</pre>

软件作者
===
<pre>
软件作者：Boilit
作者姓名：于景洋
所在单位：胜利油田胜利软件有限责任公司
</pre>

License(许可证)
===
<pre>
Template Engine Benchmark Test is released under the MIT License. 
See the bundled LICENSE file for details.

Template Engine Benchmark Test依据MIT许可证发布。
详细请看捆绑的LICENSE文件。
</pre>

