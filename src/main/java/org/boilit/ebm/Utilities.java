package org.boilit.ebm;

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

    public static final Properties getProperties(final String file) throws Exception {
        final Properties properties = new Properties();
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final InputStream inputStream = classLoader.getResourceAsStream(file);
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

    public static final void writeResult(final IEngine engine, final long time, final OutputStream outputStream) throws Exception {
        long size = 0;
        if (outputStream instanceof IOutput) {
            size = ((IOutput) outputStream).getStreamSize();
        }
        writeResult(engine, time, size);
    }

    public static final void writeResult(final IEngine engine, final long time, final Writer writer) throws Exception {
        long size = 0;
        if (writer instanceof IOutput) {
            size = ((IOutput) writer).getStreamSize();
        }
        writeResult(engine, time, size);
    }

    private static final void writeResult(final IEngine engine, final long time, final long size) throws Exception {
        // write result to file.
        final String classPath = Thread.currentThread().getContextClassLoader().getResource("").getFile();
        final File file = new File(classPath, engine.getName() + ".txt");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        final BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        try {
            bw.write(engine.getName() + "-" + engine.getVersion() + ";");
            bw.write(String.valueOf(time) + ";");
            bw.write(String.valueOf(size));
            bw.newLine();
        } catch (Exception e) {
            throw e;
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }
}
