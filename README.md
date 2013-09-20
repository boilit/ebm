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
JDK1.7上UTF-8编码 编译测试
===============================================================================
process engine [StringBuilder] use standlone jvm ...
process engine [Bsl] use standlone jvm ...
process engine [Httl] use standlone jvm ...
process engine [Webit] use standlone jvm ...
process engine [Beetl] use standlone jvm ...
process engine [Velocity] use standlone jvm ...
process engine [FreeMarker] use standlone jvm ...
===============================================================================
OsName:Windows 7, OsVersion:6.1, OsArch:amd64, CpuCore:4
JreVersion:1.7.0_25, MaxMem:1795M, FreeMem:117M, UsedMem:3M
Items:20, WarmCount:10, LoopCount:10000, Buffered:false, OutputEncoding:UTF-8
===============================================================================
Engine              Version             Time(ms)  Size(b)   Tps       Rate(%)   
StringBuilder       jdk1.7.0_25         844       6733      11848     100.00    
Bsl                 1.0.0-SNAPSHOT      775       4613      12903     108.90    
Httl                1.0.7               819       6805      12210     103.05    
Webit               0.8.0-SNAPSHOT      958       7335      10438     88.10     
Beetl               1.25.01             1128      6807      8865      74.82     
Velocity            1.7                 1960      7498      5102      43.06     
FreeMarker          2.3.19              2550      6809      3921      33.10     
===============================================================================

JDK1.6上UTF-8编码 编译测试
===============================================================================
process engine [StringBuilder] use standlone jvm ...
process engine [Bsl] use standlone jvm ...
process engine [Httl] use standlone jvm ...
process engine [Webit] use standlone jvm ...
process engine [Beetl] use standlone jvm ...
process engine [Velocity] use standlone jvm ...
process engine [FreeMarker] use standlone jvm ...
===============================================================================
OsName:Windows 7, OsVersion:6.1, OsArch:x86, CpuCore:4
JreVersion:1.6.0_30, MaxMem:247M, FreeMem:13M, UsedMem:2M
Items:20, WarmCount:10, LoopCount:10000, Buffered:false, OutputEncoding:UTF-8
===============================================================================
Engine              Version             Time(ms)  Size(b)   Tps       Rate(%)   
StringBuilder       jdk1.6.0_30         748       6733      13368     100.00    
Bsl                 1.0.0-SNAPSHOT      962       4613      10395     77.75     
Httl                1.0.7               1111      6805      9000      67.33     
Webit               0.8.0-SNAPSHOT      1467      7335      6816      50.99     
Beetl               1.25.01             1877      6807      5327      39.85     
Velocity            1.7                 2715      7498      3683      27.55     
FreeMarker          2.3.19              3562      6809      2807      21.00     
===============================================================================

JDK1.7上GBK编码 编译测试
===============================================================================
process engine [StringBuilder] use standlone jvm ...
process engine [Bsl] use standlone jvm ...
process engine [Httl] use standlone jvm ...
process engine [Webit] use standlone jvm ...
process engine [Beetl] use standlone jvm ...
process engine [Velocity] use standlone jvm ...
process engine [FreeMarker] use standlone jvm ...
===============================================================================
OsName:Windows 7, OsVersion:6.1, OsArch:amd64, CpuCore:4
JreVersion:1.7.0_25, MaxMem:1795M, FreeMem:117M, UsedMem:3M
Items:20, WarmCount:10, LoopCount:10000, Buffered:false, OutputEncoding:GBK
===============================================================================
Engine              Version             Time(ms)  Size(b)   Tps       Rate(%)   
StringBuilder       jdk1.7.0_25         1081      6731      9250      100.00    
Bsl                 1.0.0-SNAPSHOT      911       4611      10976     118.66    
Httl                1.0.7               1056      6803      9469      102.37    
Webit               0.8.0-SNAPSHOT      1282      7333      7800      84.32     
Beetl               1.25.01             1341      6805      7457      80.61     
Velocity            1.7                 2489      7496      4017      43.43     
FreeMarker          2.3.19              2952      6807      3387      36.62     
===============================================================================

JDK1.6上GBK编码 编译测试
===============================================================================
process engine [StringBuilder] use standlone jvm ...
process engine [Bsl] use standlone jvm ...
process engine [Httl] use standlone jvm ...
process engine [Webit] use standlone jvm ...
process engine [Beetl] use standlone jvm ...
process engine [Velocity] use standlone jvm ...
process engine [FreeMarker] use standlone jvm ...
===============================================================================
OsName:Windows 7, OsVersion:6.1, OsArch:x86, CpuCore:4
JreVersion:1.6.0_30, MaxMem:247M, FreeMem:13M, UsedMem:2M
Items:20, WarmCount:10, LoopCount:10000, Buffered:false, OutputEncoding:GBK
===============================================================================
Engine              Version             Time(ms)  Size(b)   Tps       Rate(%)   
StringBuilder       jdk1.6.0_30         901       6731      11098     100.00    
Bsl                 1.0.0-SNAPSHOT      984       4611      10162     91.57     
Httl                1.0.7               1076      6803      9293      83.74     
Webit               0.8.0-SNAPSHOT      1439      7333      6949      62.61     
Beetl               1.25.01             1646      6805      6075      54.74     
Velocity            1.7                 2813      7496      3554      32.03     
FreeMarker          2.3.19              3715      6807      2691      24.25     
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

