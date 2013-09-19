Template Engine Benchmark Test
===
<pre>
以下为org.boilit.ebm.Main的参数调整及测试方法        
        
        final int warmCount = 10;
        final int loopCount = 10000;
        final boolean buffered = false;
        final String outputEncoding = "UTF-8";
        final OutputMode outputMode = OutputMode.BYTES;
        final int capacity = StockModel.CAPACITY_5;

测试用例的Main中可以通过修改这些参数来测试，修改后直接执行main方法即可
执行main方法时，会在classpath下生成bat文件，然后被main方法以独立进程启动并产生结果，
最后main方法汇总结果并计算比较。


软件作者：Boilit
作者姓名：于景洋
所在单位：胜利油田胜利软件有限责任公司

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


引擎参考：<a href="https://github.com/boilit/bsl">BSL</a>

引擎文档：<a href="http://boilit.github.io/bsl">BSL文档</a>

Download: 
    Bin:<a href="http://boilit.github.io/bsl/files/bsl-1.0.0-SNAPSHOT.jar">bsl-1.0.0-SNAPSHOT.jar</a>
    Src:<a href="http://boilit.github.io/bsl/files/bsl-1.0.0-SNAPSHOT-sources.jar">bsl-1.0.0-SNAPSHOT-sources.jar</a>
</pre>
