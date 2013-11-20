@rem beta version, current version run error!
@rem Please give libraries path.
@set Libraries=%CD%/lib

@rem Please give JAVA_HOME location to run benchmark
@rem @set JAVA_HOME=D:\Java\jdk7u40x64

@set PATH=.;%JAVA_HOME%\bin;
@set CLASSPATH=%CD%;
@for /F "delims=" %%i in ('dir /A:-D /B /S "%Libraries%"') do @if exist %%i (
	@call :SetClassPath %%i
)
@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\lib\tools.jar
@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\lib\dt.jar
@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\jre\lib\rt.jar

@"%JAVA_HOME%\bin\java" org.boilit.ebm.Benchmark -config benchmark.properties -jdk "%JAVA_HOME%"

@pause 

@rem ========================SetClassPath Function============================
:SetClassPath
@set CLASSPATH=%CLASSPATH%;%1
@GOTO :EOF