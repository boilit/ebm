Template Engine Benchmark Test
===
<pre>
目前网络上的Java模板引擎测试基本上都是非独立JVM测试的，
这样做后测试的引擎性能会较高，与实际性能相比有较大偏差，
因此本测试对每个引擎都使用独立JVM测试，保证了各个引擎间环境的公平性；

以下是2.0.0的测试方法：

1、修改properties文件；
2、执行Benchmark的main方法；

以下是1.0.0的测试方法：

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

参考资料
===
<pre>
引擎参考：<a href="https://github.com/boilit/bsl">BSL</a>

引擎文档：<a href="http://boilit.github.io/bsl">BSL文档</a>

Download：<a href="http://boilit.github.io/bsl/releases/bsl-2.0.1.jar">bsl-2.0.1.jar</a>

交流QQ群：109365467
</pre>
License(许可证)
===
<pre>
Template Engine Benchmark Test is released under the MIT License. 
See the bundled LICENSE file for details.

Template Engine Benchmark Test依据MIT许可证发布。
详细请看捆绑的LICENSE文件。
</pre>

