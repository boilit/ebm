package org.boilit.ebm;

import java.io.*;
import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public final class Benchmark {
    public static void main(String[] args) throws Exception {
        final Properties arguments = Utilities.getArguments(args);
        final String config = arguments.getProperty("-config", "benchmark.properties");
        final Properties properties = Utilities.getProperties(config);
        final String[] engineClassNames = properties.getProperty("engines", "").split(";");
        final IEngine[] engines = new IEngine[engineClassNames.length];
        final Result[] results = new Result[engineClassNames.length];
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final String classPath = classLoader.getResource("").getFile();
        File commandFile;
        File resultFile;
        Process process;
        System.out.print(fit("Engine", 30, false));
        System.out.print(fit("Time", 10, true));
        System.out.print(fit("Size", 15, true));
        System.out.println();
        for (int i = 0, n = engineClassNames.length; i < n; i++) {
            engines[i] = (IEngine) classLoader.loadClass(engineClassNames[i].trim()).newInstance();
            commandFile = new File(classPath, engines[i].getName() + ".bat");
            resultFile = new File(classPath, engines[i].getName() + ".txt");
            Benchmark.generateCmdFile(commandFile, engineClassNames[i], config, properties);
            process = Runtime.getRuntime().exec("cmd /c " + engines[i].getName() + ".bat", new String[]{}, new File(classPath));
            process.waitFor();
            commandFile.delete();
            results[i] = readResultFile(resultFile);
            resultFile.delete();
//            System.out.println(results[i].name + "," + results[i].time + "," + results[i].size + "," + results[i].memory);
            System.out.print(fit(results[i].name, 30, false));
            System.out.print(fit(results[i].time, 10, true));
            System.out.print(fit(results[i].size, 15, true));
            System.out.println();
        }
    }

    private static final void generateCmdFile(
            final File commandFile, final String engineClassName,
            final String config, final Properties properties) throws Exception {
        if (!commandFile.getParentFile().exists()) {
            commandFile.getParentFile().mkdirs();
        }
        if (!commandFile.exists()) {
            commandFile.createNewFile();
        }
        final BufferedWriter bw = new BufferedWriter(new FileWriter(commandFile));
        try {
            final String javaHome = properties.getProperty("jdk", "");
            if (javaHome.trim().length() > 0) {
                bw.write("@set JAVA_HOME=");
                bw.write(javaHome);
                bw.newLine();
            }
            bw.write("@set PATH=.;%JAVA_HOME%\\bin;");
            bw.newLine();
            bw.write("@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\\lib\\tools.jar");
            bw.newLine();
            bw.write("@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\\lib\\dt.jar");
            bw.newLine();
            bw.write("@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\\jre\\lib\\rt.jar");
            bw.newLine();
            final String lib = properties.getProperty("lib", "");
            if (lib.trim().length() > 0) {
                final File[] files = new File(lib).listFiles();
                if (files != null) {
                    for (int i = 0, n = files.length; i < n; i++) {
                        bw.write("@set CLASSPATH=%CLASSPATH%;");
                        bw.write(files[i].getAbsolutePath());
                        bw.newLine();
                    }
                }
            }
            bw.write("@set CLASSPATH=%CLASSPATH%;" + commandFile.getParentFile().getAbsolutePath() + ";");
            bw.newLine();
            bw.write("@%JAVA_HOME%\\bin\\java");
            final String jvm = properties.getProperty("jvm", "");
            if(jvm != null && jvm.trim().length()>0) {
                bw.write(" "+jvm.trim());
            }
            final int mode = Integer.parseInt(properties.getProperty("mode", "0"));
            if (mode == 1) {
                bw.write(" -server");
            } else if (mode == 2) {
                bw.write(" -client");
            }
            bw.write(" ");
            bw.write(Executor.class.getName());
            bw.write(" -engine ");
            bw.write(engineClassName.trim());
            bw.write(" -config ");
            bw.write(config);
            bw.newLine();
        } catch (Exception e) {
            throw e;
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    private static final Result readResultFile(final File resultFile) throws Exception {
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
                result.name = resultArray[0];
                result.time += Long.parseLong(resultArray[1]);
                result.size += Long.parseLong(resultArray[2]);
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

    private static final String fit(final Object value, final int size, final boolean previous) {
        final String string = value == null ? "" : value.toString();
        final StringBuffer buffer = new StringBuffer(string);
        for (int i = size - string.length(); i >= 0; i--) {
            if (previous) {
                buffer.insert(0, ' ');
            } else {
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }

    private static class Result {
        private String name;
        private long time;
        private long size;
    }
}
