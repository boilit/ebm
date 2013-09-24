Template Engine Benchmark Test
===
<pre>
目前网络上的Java模板引擎测试基本上都是非独立JVM测试的，
这样做后测试的引擎性能会较高，与实际性能相比有较大偏差，
因此本测试对每个引擎都使用独立JVM测试，保证了各个引擎间环境的公平性；

以下为org.boilit.ebm.Main的参数调整及测试方法        

1、基准测试使用的方式是对每款引擎进行独立JVM测试
2、环境：OsName(操作系统名称)、OsVersion(操作系统版本)
         OsArch(架构x86、x64)、CpuCore(CPU核心数)
         JreVersion(Jre版本)、MaxMem(最大JVM内存)
         FreeMem(空闲JVM内存)、UsedMem(已用JVM内存)
         Items(数据条数)、WarmCount(预热次数、不计算时间)
         LoopCount(测试次数、计算时间)、Buffered(是否开启IO缓冲)
         OutputEncoding(输出编码)；
3、标题：Engine(引擎)、Version(版本)、Time(耗时)
         Size(单次执行生成数据量)、Tps(吞吐量)
         Rate(对比StringBuilder的性能比例)；
4、测试：
         方法一：
                  编译工程，
                  在编译路径下找到benchmark.bat；
                  修改指定的Benchmark.jar的路径及JAVA_HOME；
                  根据需求修改命令行参数，参数参考环境里参数的说明；
                  双击运行benchmark.bat；
                  运行完成后可以在命令行窗口看到测试结果；
                  也可以在编译路径下的benchmark.txt中看到测试结果；
         方法二：
                  修改基准测试项目的org.boilit.ebm.Main类的main方法内的参数
                  以Java Application方式运行测试；
                  Main的实例运行生成bat批处理文件顺序启动对应的模板引擎生成测试结果，
                  Main最后汇总计算生成测试报表；
5、参数：org.boilit.ebm.Main类中main方法内的参数调整：
        // 预热次数、不计算时间消耗
        final int warmCount = 10;
        // 测试次数、计算时间消耗
        final int loopCount = 10000;
        // 是否开启IO缓冲
        final boolean buffered = false;
        // 输出编码
        final String outputEncoding = "UTF-8";
        // 输出方式（OutputStream、Writer）
        final OutputMode outputMode = OutputMode.BYTES;
        // 数据数量、CAPACITY_1至CAPACITY_5，即10-50条数据可选
        final int capacity = StockModel.CAPACITY_2;
</pre>
软件作者
===
<pre>
软件作者：Boilit
作者姓名：于景洋
所在单位：胜利油田胜利软件有限责任公司
</pre>
测试结果
===
<pre>
注：以下测试结果的Size（单次执行生成数据量）不同是正常的，
    原因是各个引擎对输出的空行压缩机制不同，导致输出结果大小不一，
    输出Size越小意味着占用更少的IO资源，也意味着在网络环境下有更大的吞吐量,
    其中Bsl、Httl有相应的静态文本空行压缩扩展接口，且性能不错；
    Bsl是解释执行引擎，且具有动态语言特性，性能卓越，敏捷开发上有着先天优势；
    Httl-1.0.11较1.0.7版本性能下降不少，且不知为什么for内嵌套if else给我报语法错误，无奈用条件表达式代替了。

JDK1.7上UTF-8编码 基准测试源码使用JDK1.6编译 测试结果
===============================================================================
OsName:Windows 7, OsVersion:6.1, OsArch:amd64, CpuCore:4
JreVersion:1.7.0_25, MaxMem:1795M, FreeMem:119M, UsedMem:1M
Items:20, WarmCount:10, LoopCount:10000, Buffered:false
OutputEncoding:UTF-8, OutputMode:BYTES
===============================================================================
Engine              Version             Time(ms)  Size(b)   Tps       Rate(%)   
StringBuilder       jdk1.7.0_25         794       6733      12594     100.00    
Bsl                 1.0.1               773       4613      12936     102.72    
Httl                1.0.11              991       6805      10090     80.12     
Webit               1.0.0               852       7335      11737     93.19     
Beetl               1.25.01             1058      6807      9451      75.05     
Velocity            1.7                 1995      7498      5012      39.80     
FreeMarker          2.3.19              2455      6809      4073      32.34     
===============================================================================

JDK1.6上UTF-8编码 基准测试源码使用JDK1.6编译 测试结果
===============================================================================
OsName:Windows 7, OsVersion:6.1, OsArch:x86, CpuCore:4
JreVersion:1.6.0_30, MaxMem:247M, FreeMem:14M, UsedMem:1M
Items:20, WarmCount:10, LoopCount:10000, Buffered:false
OutputEncoding:UTF-8, OutputMode:BYTES
===============================================================================
Engine              Version             Time(ms)  Size(b)   Tps       Rate(%)   
StringBuilder       jdk1.6.0_30         712       6733      14044     100.00    
Bsl                 1.0.1               903       4613      11074     78.85     
Httl                1.0.11              1097      6805      9115      64.90     
Webit               1.0.0               1314      7335      7610      54.19     
Beetl               1.25.01             1611      6807      6207      44.20     
Velocity            1.7                 2538      7498      3940      28.05     
FreeMarker          2.3.19              3624      6809      2759      19.65     
===============================================================================

JDK1.7上GBK编码 基准测试源码使用JDK1.6编译 测试结果
===============================================================================
OsName:Windows 7, OsVersion:6.1, OsArch:amd64, CpuCore:4
JreVersion:1.7.0_25, MaxMem:1795M, FreeMem:119M, UsedMem:1M
Items:20, WarmCount:10, LoopCount:10000, Buffered:false
OutputEncoding:GBK, OutputMode:BYTES
===============================================================================
Engine              Version             Time(ms)  Size(b)   Tps       Rate(%)   
StringBuilder       jdk1.7.0_25         1067      6731      9372      100.00    
Bsl                 1.0.1               826       4611      12106     129.18    
Httl                1.0.11              1162      6803      8605      91.82     
Webit               1.0.0               1085      7333      9216      98.34     
Beetl               1.25.01             1233      6805      8110      86.54     
Velocity            1.7                 2273      7496      4399      46.94     
FreeMarker          2.3.19              2789      6807      3585      38.26     
===============================================================================

JDK1.6上GBK编码 基准测试源码使用JDK1.6编译 测试结果
===============================================================================
OsName:Windows 7, OsVersion:6.1, OsArch:x86, CpuCore:4
JreVersion:1.6.0_30, MaxMem:247M, FreeMem:14M, UsedMem:1M
Items:20, WarmCount:10, LoopCount:10000, Buffered:false
OutputEncoding:GBK, OutputMode:BYTES
===============================================================================
Engine              Version             Time(ms)  Size(b)   Tps       Rate(%)   
StringBuilder       jdk1.6.0_30         901       6731      11098     100.00    
Bsl                 1.0.1               994       4611      10060     90.64     
Httl                1.0.11              1120      6803      8928      80.45     
Webit               1.0.0               1557      7333      6422      57.87     
Beetl               1.25.01             1614      6805      6195      55.82     
Velocity            1.7                 2790      7496      3584      32.29     
FreeMarker          2.3.19              3708      6807      2696      24.30     
===============================================================================
</pre>
参考资料
===
<pre>
引擎参考：<a href="https://github.com/boilit/bsl">BSL</a>

引擎文档：<a href="http://boilit.github.io/bsl">BSL文档</a>

Download：<a href="http://boilit.github.io/bsl/files/bsl-1.0.2.jar">bsl-1.0.2.jar</a>

交流群：109365467 <a target="_blank" href="http://wp.qq.com/wpa/qunwpa?idkey=aa38808704bd813440ca2314873dd634b878b76be392ab0279b005db18be006b"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="Boilit技术群" title="Boilit技术群"></a>
</pre>
License(许可证)
===
<pre>
Template Engine Benchmark Test is released under the MIT License. 
See the bundled LICENSE file for details.

Template Engine Benchmark Test依据MIT许可证发布。
详细请看捆绑的LICENSE文件。
</pre>

