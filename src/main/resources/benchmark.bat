@rem Please give BenchmarkLocation.jar location to find engine's depend jars.
@rem set Libraries=D:\W04WorkSpace\Maven001\ebm\lib\BenchmarkLocation.jar
@rem Please give JAVA_HOME location to run benchmark
@set JAVA_HOME=D:\Java\jdk7u40x64
@set PATH=.;%JAVA_HOME%\bin;
@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\lib\tools.jar
@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\lib\dt.jar
@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\jre\lib\rt.jar
@set CLASSPATH=%CLASSPATH%;D:\W04WorkSpace\Maven001\ebm1\lib\beetl.1.25.01.jar
@set CLASSPATH=%CLASSPATH%;D:\W04WorkSpace\Maven001\ebm1\lib\BenchmarkLocation.jar
@set CLASSPATH=%CLASSPATH%;D:\W04WorkSpace\Maven001\ebm1\lib\bsl-1.3.0.jar
@set CLASSPATH=%CLASSPATH%;D:\W04WorkSpace\Maven001\ebm1\lib\freemarker-2.3.19.jar
@set CLASSPATH=%CLASSPATH%;D:\W04WorkSpace\Maven001\ebm1\lib\httl-1.0.11.jar
@set CLASSPATH=%CLASSPATH%;D:\W04WorkSpace\Maven001\ebm1\lib\jcommon-1.0.20.jar
@set CLASSPATH=%CLASSPATH%;D:\W04WorkSpace\Maven001\ebm1\lib\jfreechart-1.0.16.jar
@set CLASSPATH=%CLASSPATH%;D:\W04WorkSpace\Maven001\ebm1\lib\jodd-3.4.5.jar
@set CLASSPATH=%CLASSPATH%;D:\W04WorkSpace\Maven001\ebm1\lib\rythm-engine-1.0-b10-SNAPSHOT.jar
@set CLASSPATH=%CLASSPATH%;D:\W04WorkSpace\Maven001\ebm1\lib\slf4j-api-1.7.2.jar
@set CLASSPATH=%CLASSPATH%;D:\W04WorkSpace\Maven001\ebm1\lib\slf4j-simple-1.7.2.jar
@set CLASSPATH=%CLASSPATH%;D:\W04WorkSpace\Maven001\ebm1\lib\velocity-1.7-dep.jar
@set CLASSPATH=%CLASSPATH%;D:\W04WorkSpace\Maven001\ebm1\lib\velocity-1.7.jar
@set CLASSPATH=%CLASSPATH%;D:\W04WorkSpace\Maven001\ebm1\lib\webit-script-1.1.4.jar
@set CLASSPATH=%CLASSPATH%;D:\W04WorkSpace\Maven001\ebm1\target\classes;
@%JAVA_HOME%\bin\java -Xnoclassgc org.boilit.ebm.Benchmark -config benchmark.properties
@pause
