package org.boilit.ebm.utils;

import java.io.*;
import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public final class Utilities {

    public static final String CR_LF = System.getProperty("file.separator");

    public static final Properties getArguments(final String[] args) {
        Properties arguments = new Properties();
        if (args == null || args.length == 0) {
            return arguments;
        }
        for (int i = 0, n = args.length; i < n; i += 2) {
            arguments.setProperty(args[i].trim(), args[i + 1].trim());
        }
        return arguments;
    }

    public static ClassLoader getDefaultClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class loadClass(String className) throws ClassNotFoundException {
        return getDefaultClassLoader().loadClass(className);
    }

    public static final Properties getProperties(final String file) throws Exception {
        final Properties properties = new Properties();
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (Exception e) {
            inputStream = null;
        }
        if (inputStream == null) {
            try {
                inputStream = new FileInputStream(new File(classLoader.getResource("").getFile(), file));
            } catch (Exception e) {
                inputStream = null;
                e.printStackTrace();
            }
        }
        if (inputStream == null) {
            try {
                inputStream = classLoader.getResourceAsStream(file);
            } catch (Exception e) {
                inputStream = null;
            }
        }
        try {
            properties.load(inputStream);
        } catch (Exception e) {
            throw e;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return properties;
    }

    public static final void writeResult(final String engineName, final Properties properties,
            final long time, final long size) throws Exception {
        // write result to file.
        final String classPath = Thread.currentThread().getContextClassLoader().getResource("").getFile();
        final File file = new File(classPath, engineName + ".txt");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        final FileWriter bw = new FileWriter(file, true);
        try {
            StringBuilder builder
                    = new StringBuilder()
                    .append(engineName).append('-').append(properties.getProperty(engineName + ".version")).append(';')
                    .append(Long.toString(time)).append(';')
                    .append(Long.toString(size)).append('\n');
            bw.append(builder);
        } finally {
            bw.close();
        }
    }
}
