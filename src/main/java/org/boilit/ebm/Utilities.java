package org.boilit.ebm;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;

/**
 * @author Boilit
 * @see
 */
public final class Utilities {
    public static final String CR_LF = System.getProperty("line.separator");

    public static final String showEnv(final JvmMode jvmMode, final int warmCount, final int loopCount,
                                       final boolean buffered, final String outputEncoding, final OutputMode outputMode) {
        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");
        String osVersion = System.getProperty("os.version");
        String jreVersion = System.getProperty("java.version");
        Runtime runtime = Runtime.getRuntime();
        int cpuCores = runtime.availableProcessors();
        long max = runtime.maxMemory() >> 20;
        long free = runtime.freeMemory() >> 20;
        long total = runtime.totalMemory() >> 20;
        long used = total - free;
        final StringBuilder builder = new StringBuilder();
        builder.append(getDelimiter());
        builder.append(CR_LF);
        builder.append("OsName:").append(osName).append(", ");
        builder.append("OsVersion:").append(osVersion).append(", ");
        builder.append("OsArch:").append(osArch).append(", ");
        builder.append("CpuCore:").append(cpuCores).append(CR_LF);
        builder.append("JreVersion:").append(jreVersion).append(", ");
        builder.append("MaxMem:").append(max).append("M, ");
        builder.append("FreeMem:").append(free).append("M, ");
        builder.append("UsedMem:").append(used).append("M").append(CR_LF);
        builder.append("JvmMode:").append(jvmMode).append(", ");
        builder.append("OutputEncoding:").append(outputEncoding).append(", ");
        builder.append("OutputMode:").append(outputMode).append(CR_LF);
        builder.append("Items:").append(StockModel.getCapacity() * 10).append(", ");
        builder.append("WarmCount:").append(warmCount).append(", ");
        builder.append("LoopCount:").append(loopCount).append(", ");
        builder.append("Buffered:").append(buffered).append(CR_LF);
        builder.append(getDelimiter());
        return builder.toString();
    }

    public static String getDelimiter() {
        return "===============================================================================";
    }

    public static final File write(final EngineInfo ei, final JvmMode jvmMode, final String parameters) throws Exception {
        final StringBuilder builder = new StringBuilder();
        final String javaHome = Utilities.getJavaHome();
        builder.append("@set JAVA_HOME=").append(javaHome).append(CR_LF);
        builder.append("@set PATH=").append(Utilities.getJavaPath()).append(CR_LF);
        builder.append("@set CLASSPATH=").append(Utilities.getClassPath(javaHome, ei.getJarFiles())).append(CR_LF);
        builder.append("@set PARAMETERS=").append(parameters).append(CR_LF);
        builder.append("@java");
        if(JvmMode.CLIENT == jvmMode) {
            builder.append(" -client");
        } else if(JvmMode.SERVER == jvmMode) {
            builder.append(" -server");
        }
        builder.append(" ").append(ei.getClassName()).append(" %PARAMETERS%").append(CR_LF);
        //builder.append("@pause").append(CR_LF);
        File file = new File(getClassPath(), ei.getName().concat(".bat"));
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file);
        fw.write(builder.toString());
        fw.close();
        return file;
    }

    public static final Benchmark inject(final String[] args) throws Exception {
        if (args == null || args.length < 2) {
            return null;
        }
        if (!args[0].trim().equals("-class")) {
            return null;
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Benchmark benchmark = (Benchmark) classLoader.loadClass(args[1]).newInstance();
        for (int i = 0, n = args.length; i < n; i += 2) {
            if (args[i].trim().equals("-buffered")) {
                benchmark.setBuffered(Boolean.parseBoolean(args[i + 1]));
            } else if (args[i].trim().equals("-warmCount")) {
                benchmark.setWarmCount(Integer.parseInt(args[i + 1]));
            } else if (args[i].trim().equals("-loopCount")) {
                benchmark.setLoopCount(Integer.parseInt(args[i + 1]));
            } else if (args[i].trim().equals("-outputEncoding")) {
                benchmark.setOutputEncoding(args[i + 1]);
            } else if (args[i].trim().equals("-outputModel")) {
                benchmark.setOutputModel(OutputMode.valueOf(args[i + 1]));
            } else if (args[i].trim().equals("-capacity")) {
                StockModel.setCapacity(Integer.parseInt(args[i + 1]));
            }
        }
        return benchmark;
    }

    private static final String getJavaHome() {
        return new File(System.getProperty("java.home")).getParentFile().getAbsolutePath();
    }

    private static final String getJavaPath() {
        return ".;".concat(getPath(getJavaHome(), "bin")).concat(";%PATH%");
    }

    private static final String getClassPath(final String javaHome, final String[] jarFiles) {
        final StringBuilder builder = new StringBuilder();
        builder.append(getPath(javaHome, "lib", "tool.jar"));
        builder.append(';').append(getPath(javaHome, "lib", "dt.jar"));
        builder.append(';').append(getPath(javaHome, "jre", "lib", "rt.jar"));
        final String library = getLibraryPath();
        for (int i = 0, n = jarFiles.length; i < n; i++) {
            builder.append(';').append(getPath(library, jarFiles[i]));
        }
        builder.append(';').append(getClassPath()).append(";%CLASSPATH%");
        return builder.toString();
    }

    public static final String getClassPath() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String file = classLoader.getResource("").getFile();
        while (file.startsWith("/")) {
            file = file.substring(1);
        }
        while (file.startsWith(File.separator)) {
            file = file.substring(1);
        }
        if (file.startsWith("file:/")) {
            file = file.substring(6);
        }
        while (file.endsWith("/")) {
            file = file.substring(0, file.length() - 1);
        }
        while (file.endsWith(File.separator)) {
            file = file.substring(0, file.length() - 1);
        }
        return file;
    }

    private static final String getLibraryPath() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource("BenchmarkLocation.txt");
        String jarFilePath = url.getFile().substring("file:/".length());
        return new File(jarFilePath.substring(0, jarFilePath.indexOf("!/"))).getParentFile().getAbsolutePath();
    }

    public static final String getPath(final String parent, final String... paths) {
        final StringBuilder builder = new StringBuilder(parent);
        for (int i = 0, n = paths.length; i < n; i++) {
            builder.append(File.separator).append(paths[i]);
        }
        return builder.toString();
    }
}
