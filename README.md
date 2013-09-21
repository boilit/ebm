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
StringBuilder       jdk1.7.0_25         753       6733      13280     100.00    
Bsl                 1.0.0-SNAPSHOT      737       7258      13568     102.17    
Httl                1.0.11              1013      6805      9871      74.33     
Webit               1.0.0               833       7335      12004     90.40     
Beetl               1.25.01             1034      6807      9671      72.82     
Velocity            1.7                 1975      7498      5063      38.13     
FreeMarker          2.3.19              2441      6809      4096      30.85     
===============================================================================

JDK1.6上UTF-8编码 基准测试源码使用JDK1.6编译 测试结果
===============================================================================
OsName:Windows 7, OsVersion:6.1, OsArch:x86, CpuCore:4
JreVersion:1.6.0_30, MaxMem:247M, FreeMem:14M, UsedMem:1M
Items:20, WarmCount:10, LoopCount:10000, Buffered:false
OutputEncoding:UTF-8, OutputMode:BYTES
===============================================================================
Engine              Version             Time(ms)  Size(b)   Tps       Rate(%)   
StringBuilder       jdk1.6.0_30         706       6733      14164     100.00    
Bsl                 1.0.0-SNAPSHOT      892       7258      11210     79.15     
Httl                1.0.11              1107      6805      9033      63.78     
Webit               1.0.0               1360      7335      7352      51.91     
Beetl               1.25.01             1610      6807      6211      43.85     
Velocity            1.7                 2509      7498      3985      28.14     
FreeMarker          2.3.19              3509      6809      2849      20.12     
===============================================================================

JDK1.7上GBK编码 基准测试源码使用JDK1.6编译 测试结果
===============================================================================
OsName:Windows 7, OsVersion:6.1, OsArch:amd64, CpuCore:4
JreVersion:1.7.0_25, MaxMem:1795M, FreeMem:119M, UsedMem:1M
Items:20, WarmCount:10, LoopCount:10000, Buffered:false
OutputEncoding:GBK, OutputMode:BYTES
===============================================================================
Engine              Version             Time(ms)  Size(b)   Tps       Rate(%)   
StringBuilder       jdk1.7.0_25         1078      6731      9276      100.00    
Bsl                 1.0.0-SNAPSHOT      834       7256      11990     129.26    
Httl                1.0.11              1185      6803      8438      90.97     
Webit               1.0.0               1057      7333      9460      101.99    
Beetl               1.25.01             1218      6805      8210      88.51     
Velocity            1.7                 2241      7496      4462      48.10     
FreeMarker          2.3.19              2825      6807      3539      38.16     
===============================================================================

JDK1.6上GBK编码 基准测试源码使用JDK1.6编译 测试结果
===============================================================================
OsName:Windows 7, OsVersion:6.1, OsArch:x86, CpuCore:4
JreVersion:1.6.0_30, MaxMem:247M, FreeMem:14M, UsedMem:1M
Items:20, WarmCount:10, LoopCount:10000, Buffered:false
OutputEncoding:GBK, OutputMode:BYTES
===============================================================================
Engine              Version             Time(ms)  Size(b)   Tps       Rate(%)   
StringBuilder       jdk1.6.0_30         900       6731      11111     100.00    
Bsl                 1.0.0-SNAPSHOT      1016      7256      9842      88.58     
Httl                1.0.11              1131      6803      8841      79.58     
Webit               1.0.0               1617      7333      6184      55.66     
Beetl               1.25.01             1646      6805      6075      54.68     
Velocity            1.7                 2788      7496      3586      32.28     
FreeMarker          2.3.19              3750      6807      2666      24.00     
===============================================================================
</pre>
参考资料
===
<pre>
引擎参考：<a href="https://github.com/boilit/bsl">BSL</a>

引擎文档：<a href="http://boilit.github.io/bsl">BSL文档</a>

Download: 
    Bin:<a href="http://boilit.github.io/bsl/files/bsl-1.0.0-SNAPSHOT.jar">bsl-1.0.0-SNAPSHOT.jar</a>
    Src:<a href="http://boilit.github.io/bsl/files/bsl-1.0.0-SNAPSHOT-sources.jar">bsl-1.0.0-SNAPSHOT-sources.jar</a>
</pre>
License(许可证)
===
<pre>
Template Engine Benchmark Test is released under the MIT License. 
See the bundled LICENSE file for details.

Template Engine Benchmark Test依据MIT许可证发布。
详细请看捆绑的LICENSE文件。
</pre>

