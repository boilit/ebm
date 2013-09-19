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


引擎参考：<a href="https://github.com/boilit/bsl">BSL</a>

引擎文档：<a href="http://boilit.github.io/bsl">BSL文档</a>

Download: 
    Bin:<a href="http://boilit.github.io/bsl/files/bsl-1.0.0-SNAPSHOT.jar">bsl-1.0.0-SNAPSHOT.jar</a>
    Src:<a href="http://boilit.github.io/bsl/files/bsl-1.0.0-SNAPSHOT-sources.jar">bsl-1.0.0-SNAPSHOT-sources.jar</a>
</pre>
