@rem Please give BenchmarkLocation.jar location to find engine's depend jars.
@set Libraries=D:\W04WorkSpace\Maven001\ebm\lib\BenchmarkLocation.jar
@rem Please give JAVA_HOME location to run benchmark
@set JAVA_HOME=D:\Java\jdk7u25
@set PATH=.;%JAVA_HOME%\bin;%PATH%
@set CLASSPATH=%JAVA_HOME%\lib\tool.jar;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\jre\lib\rt.jar;%Libraries%;%CD%;%CLASSPATH%
@set PARAMETERS= -warmCount 10 -loopCount 10000 -buffered false -outputEncoding UTF-8 -outputModel BYTES -capacity 2
@java org.boilit.ebm.Main %PARAMETERS%
@pause
