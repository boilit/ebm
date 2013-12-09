package org.boilit.ebm;

import org.boilit.ebm.utils.Utilities;
import java.io.*;
import java.util.*;

/**
 * @author Boilit
 * @see
 */
public final class Benchmark {

    public static void main(String[] args) throws Exception {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final String classPath = classLoader.getResource("").getFile();
        final Properties arguments = Utilities.getArguments(args);
        final String report = arguments.getProperty("-report", classPath);
        final String config = arguments.getProperty("-config", "benchmark.properties");
        final String defaultJavaHome = arguments.getProperty("-jdk", "");
        final Properties properties = Utilities.getProperties(config);

        //Check engine(s)
        final String[] engineNamesCache;
        int engineCount = 0;
        {
            String[] engineNamesRaw = properties.getProperty("engines", "").split(";");
            int len;
            engineNamesCache = new String[len = engineNamesRaw.length];
            String engineName;
            for (int i = 0; i < len; i++) {
                engineName = engineNamesRaw[i].trim();
                if (engineName.length() != 0 && engineName.charAt(0) != '#') {
                    engineNamesCache[engineCount++] = engineName;
                }
            }
        }

        final Result[] results = new Result[engineCount];
        String commandFileName;
        File commandFile;
        //String resultFile;
        Process process;
        String engineName;
        String engineSite;
        final int warm = Integer.parseInt(properties.getProperty("warm"));
        final int loop = Integer.parseInt(properties.getProperty("loop"));

        for (int i = 0; i < engineCount; i++) {
            engineName = engineNamesCache[i];
            engineSite = properties.getProperty(engineName + ".site", "");
            System.out.println("processing Engine[" + engineName + "]...");
            commandFileName = engineName + ".bat";
            generateCmdFile(classPath, commandFile = new File(classPath, commandFileName), engineName, config, properties, defaultJavaHome);
            process = Runtime.getRuntime().exec("cmd /c " + commandFileName + " >> out.info.log 2>> out.err.log", null, new File(classPath));
            process.waitFor();
            commandFile.delete();
            results[i] = readResultFile(new File(classPath, engineName + ".txt"), engineSite, warm, loop);
        }
        Arrays.sort(results, new Comparator<Result>() {
            @Override
            public int compare(Result o1, Result o2) {
                if (o1.getTps() < o2.getTps()) {
                    return -1;
                } else if (o1.getTps() > o2.getTps()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        Report.write(loop, results, new File(report));
    }

    private static void generateCmdFile(
            final String classPath, final File commandFile, final String engineName,
            final String config, final Properties properties, final String defaultJavaHome) throws Exception {
        if (!commandFile.getParentFile().exists()) {
            commandFile.getParentFile().mkdirs();
        }
        if (!commandFile.exists()) {
            commandFile.createNewFile();
        }
        final BufferedWriter bw = new BufferedWriter(new FileWriter(commandFile));
        try {
            final String javaHome = properties.getProperty("jdk", defaultJavaHome);
            final String jvmArgs = properties.getProperty("jvm_args", "");
            if (javaHome.trim().length() > 0) {
                bw.write("@set JAVA_HOME=");
                bw.write(javaHome);
                bw.newLine();
            }
//            bw.write("@set PATH=.;%JAVA_HOME%\\bin;");
//            bw.newLine();
            bw.write("@set CLASSPATH=;");
            bw.newLine();
            bw.write("@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\\lib\\tools.jar");
            bw.newLine();
            bw.write("@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\\lib\\dt.jar");
            bw.newLine();
            bw.write("@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\\jre\\lib\\rt.jar");
            bw.newLine();
            final String lib = properties.getProperty("lib", "");
            appendLibraryJarsToClassPath(bw, lib.trim().length() == 0 ? classPath : lib);
            appendLibraryJarsToClassPath(bw, (lib.trim().length() == 0 ? classPath : lib) + "/lib");
            bw.write("@set CLASSPATH=%CLASSPATH%;" + commandFile.getParentFile().getAbsolutePath() + ";");
            bw.newLine();
            bw.write("@\"%JAVA_HOME%\\bin\\java\"");
            final String jvm = properties.getProperty("jvm", "");
            if (jvm != null && jvm.trim().length() > 0) {
                bw.write(" " + jvm.trim());
            }
            final int mode = Integer.parseInt(properties.getProperty("mode", "0"));
            if (mode == 1) {
                bw.write(" -server");
            } else if (mode == 2) {
                bw.write(" -client");
            }
            bw.write(" ");
            bw.write(jvmArgs);
            bw.write(" ");
            bw.write(Executor.class.getName());
            bw.write(" -name ");
            bw.write(engineName.trim());
            bw.write(" -engine ");
            bw.write(properties.getProperty(engineName.trim() + ".engine").trim());
            bw.write(" -config ");
            bw.write(config.trim());
            bw.newLine();
        } catch (Exception e) {
            throw e;
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    private static void appendLibraryJarsToClassPath(BufferedWriter bw, final String lib) throws Exception {
        if (lib.trim().length() > 0) {
            final File[] files = new File(lib).listFiles();
            if (files != null) {
                for (int i = 0, n = files.length; i < n; i++) {
                    if (!files[i].getName().endsWith(".jar")) {
                        continue;
                    }
                    bw.write("@set CLASSPATH=%CLASSPATH%;");
                    bw.write(files[i].getAbsolutePath());
                    bw.newLine();
                }
            }
        }
    }

    private static Result readResultFile(final File resultFile, String engineSite, int warm, int loop) throws Exception {
        final BufferedReader br = new BufferedReader(new FileReader(resultFile));
        final Result result = new Result();
        try {
            String line;
            String[] resultArray;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                resultArray = line.split(";");
                result.setName(resultArray[0]);
                result.setTime(result.getTime() + Long.parseLong(resultArray[1]));
                result.setSize(result.getSize() + Long.parseLong(resultArray[2]));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (br != null) {
                br.close();
            }
        }

        resultFile.delete();
        result.setSite(engineSite);
        result.setTps(loop * 1000 / result.getTime());
        result.setSize(result.getSize() / (warm + loop));
        return result;
    }
}
