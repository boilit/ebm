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
        final String[] engineNames = properties.getProperty("engines", "").split(";");
        final Result[] results = new Result[engineNames.length];
        File commandFile;
        File resultFile;
        Process process;
        String engineName;
        String engineSite;
        final int warm = Integer.parseInt(properties.getProperty("warm"));
        final int loop = Integer.parseInt(properties.getProperty("loop"));
        for (int i = 0, n = engineNames.length; i < n; i++) {
            engineName = engineNames[i].trim();
            engineSite = properties.getProperty(engineName + ".site", "");
            System.out.println("processing Engine[" + engineNames[i].trim() + "]...");
            commandFile = new File(classPath, engineNames[i].trim() + ".bat");
            resultFile = new File(classPath, engineNames[i].trim() + ".txt");
            Benchmark.generateCmdFile(classPath, commandFile, engineName, config, properties, defaultJavaHome);
            process = Runtime.getRuntime().exec("cmd /c " + engineName + ".bat >> out.info.log 2>> out.err.log", null, new File(classPath));
            process.waitFor();
            commandFile.delete();
            results[i] = readResultFile(resultFile);
            resultFile.delete();
            results[i].setSite(engineSite);
            results[i].setTps(loop * 1000 / results[i].getTime());
            results[i].setSize(results[i].getSize() / (warm + loop));
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

    private static Result readResultFile(final File resultFile) throws Exception {
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
        return result;
    }
}
