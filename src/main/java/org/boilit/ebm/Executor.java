package org.boilit.ebm;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Boilit
 * @see
 */
public final class Executor implements Runnable {
    private IEngine engine;
    private String engineName;
    private Properties properties;
    private Writer writer;
    private OutputStream outputStream;

    @Override
    public void run() {
        try {
            execute(engine, engineName, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void execute(final IEngine engine, final String engineName, final Properties properties) throws Exception {
        final boolean buff = Boolean.parseBoolean(properties.getProperty("buff", "false"));
        final String outputEncoding = properties.getProperty("outputEncoding", "UTF-8");
        // set writer and output stream.
        final String byteStreamClassName = properties.getProperty("byteStream", ByteStream.class.getName());
        final String charStreamClassName = properties.getProperty("charStream", ByteStream.class.getName());
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        this.outputStream = (OutputStream) classLoader.loadClass(byteStreamClassName).newInstance();
        final Class charStreamClass = classLoader.loadClass(charStreamClassName);
        if (outputEncoding == null || outputEncoding.trim().length() == 0) {
            this.writer = (Writer) charStreamClass.newInstance();
        } else {
            this.writer = (Writer) charStreamClass.getConstructor(String.class).newInstance(outputEncoding);
        }
        if (buff) {
            this.outputStream = new BufferedOutputStream(this.outputStream);
            this.writer = new BufferedWriter(this.writer);
        }
        // set model's capacity.
        StockModel.setCapacity(Integer.parseInt(properties.getProperty("capacity", "2")));
        // execute body.
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("outputEncoding", outputEncoding);
        model.put("items", StockModel.dummyItems());
        final long time = this.doStreamOut(engine, properties, model);
        // write result to file.
        final int outs = Integer.parseInt(properties.getProperty("outs", "0"));
        if (outs == IOutput.OUTS_BYTE_STREAM) {
            if(engine.isSupportByteStream()) {
                Utilities.writeResult(engineName, properties, time, this.outputStream);
            } else {
                Utilities.writeResult(engineName, properties, time, this.writer);
            }
        } else {
            if(engine.isSupportCharStream()) {
                Utilities.writeResult(engineName, properties, time, this.writer);
            } else {
                Utilities.writeResult(engineName, properties, time, this.outputStream);
            }
        }
        this.writer.close();
        this.outputStream.close();
    }

    private final long doStreamOut(final IEngine engine, final Properties properties, final Map<String, Object> model) throws Exception {
        int outs = Integer.parseInt(properties.getProperty("outs", "0"));
        int warm = Integer.parseInt(properties.getProperty("warm", "10"));
        int loop = Integer.parseInt(properties.getProperty("loop", "10000"));
        if (outs == IOutput.OUTS_BYTE_STREAM) {
            this.doByteStreamOut(engine, model, warm);
            return this.doByteStreamOut(engine, model, loop);
        } else {
            this.doCharStreamOut(engine, model, warm);
            return this.doCharStreamOut(engine, model, loop);
        }
    }

    private final long doByteStreamOut(final IEngine engine, final Map<String, Object> model, final int count) throws Exception {
        if (!engine.isSupportByteStream()) {
            return doCharStreamOut(engine, model, count);
        }
        final OutputStream outputStream = this.outputStream;
        final long t1 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            engine.work(model, outputStream);
        }
        final long t2 = System.currentTimeMillis();
        return t2 - t1;
    }

    private final long doCharStreamOut(final IEngine engine, final Map<String, Object> model, final int count) throws Exception {
        if (!engine.isSupportCharStream()) {
            return doByteStreamOut(engine, model, count);
        }
        final Writer writer = this.writer;
        final long t1 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            engine.work(model, writer);
        }
        final long t2 = System.currentTimeMillis();
        return t2 - t1;
    }

    public static final void main(final String[] args) throws Exception {
        final Properties arguments = Utilities.getArguments(args);
        final String config = arguments.getProperty("-config", "benchmark.properties");
        final Properties properties = Utilities.getProperties(config);
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final String engineName = arguments.getProperty("-name", "NONE");
        final String engineClassName = arguments.getProperty("-engine");
        final IEngine engine = (IEngine) classLoader.loadClass(engineClassName).newInstance();
        new File(classLoader.getResource("").getFile(), engineName + ".txt").delete();
        engine.init(properties);
        try {
            Executor executor;
            final int thread = Integer.parseInt(properties.getProperty("thread", "1"));
            if(thread > 1) {
                final ExecutorService threadPool = Executors.newFixedThreadPool(thread);
                for (int i = 0; i < thread; i++) {
                    executor = new Executor();
                    executor.engine = engine;
                    executor.engineName = engineName;
                    executor.properties = properties;
                    threadPool.execute(executor);
                }
                threadPool.shutdown();
                // wait thread pool finish.
                while (!threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS)) ;
            } else {
                executor = new Executor();
                executor.engine = engine;
                executor.engineName = engineName;
                executor.properties = properties;
                executor.run();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            engine.shutdown();
        }
    }
}
