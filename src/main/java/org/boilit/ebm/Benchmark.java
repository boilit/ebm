package org.boilit.ebm;

import org.boilit.bsl.Engine;
import org.boilit.bsl.formatter.DateFormatter;
import org.boilit.bsl.xio.FileResourceLoader;

import java.io.*;
import java.util.*;

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
        final int warm = Integer.parseInt(properties.getProperty("warm"));
        final int loop = Integer.parseInt(properties.getProperty("loop"));
        for (int i = 0, n = engineClassNames.length; i < n; i++) {
            engines[i] = (IEngine) classLoader.loadClass(engineClassNames[i].trim()).newInstance();
            System.out.println("processing Engine[" + engines[i].getName() + "]...");
            commandFile = new File(classPath, engines[i].getName() + ".bat");
            resultFile = new File(classPath, engines[i].getName() + ".txt");
            Benchmark.generateCmdFile(commandFile, engineClassNames[i], config, properties);
            process = Runtime.getRuntime().exec("cmd /c " + engines[i].getName() + ".bat", new String[]{}, new File(classPath));
            process.waitFor();
            commandFile.delete();
            results[i] = readResultFile(resultFile);
            resultFile.delete();
            results[i].site = engines[i].getSite();
            results[i].tps = loop * 1000 / results[i].time;
            results[i].size /= (warm + loop);
        }
        Arrays.sort(results, new Comparator<Result>() {
            @Override
            public int compare(Result o1, Result o2) {
                if (o1.tps < o2.tps) {
                    return -1;
                } else if (o1.tps > o2.tps) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        File baseFile = new File(Benchmark.class.getResource("/tpl").getPath());
        String template = new File(baseFile, "tpl.html").getAbsolutePath();
        File report = new File(baseFile.getParentFile(), "report.html");
        if (!report.getParentFile().exists()) {
            report.getParentFile().mkdirs();
        }
        if (!report.exists()) {
            report.createNewFile();
        }
        OutputStream fos = new FileOutputStream(report);
        Engine engine = new Engine();
        engine.getTemplateCache().clear();
        engine.setInputEncoding("UTF-8");
        engine.setOutputEncoding("UTF-8");
        engine.registerFormatter(Date.class, new DateFormatter("yyyy-MM-dd"));
        engine.setResourceLoader(new FileResourceLoader());
        try {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("flotr2ie", readFile(new File(baseFile, "flotr2.ie.min.js")));
            model.put("flotr2", readFile(new File(baseFile, "flotr2.min.js")));
            model.put("engineCount", engines.length);
            model.put("loopCount", loop);
            model.put("results", results);
            model.put("date", new Date());
            engine.getTemplate(template).execute(model, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fos.close();
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

    public static String readFile(File file) throws Exception {
        final StringBuffer buffer = new StringBuffer();
        final Reader reader = new InputStreamReader(new FileInputStream(file));
        try{
            int len;
            char[] buff = new char[4096];
            while ((len = reader.read(buff)) != -1) {
                buffer.append(buff, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        return buffer.toString();
    }

    public static class Result {
        public String name;
        public String site;
        public long time;
        public long size;
        public long tps;
    }
}
