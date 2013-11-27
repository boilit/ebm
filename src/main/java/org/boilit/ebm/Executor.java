package org.boilit.ebm;

import org.boilit.ebm.utils.Utilities;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.boilit.ebm.utils.WriterOutputStream;

/**
 * @author Boilit
 * @see
 */
public final class Executor implements Runnable {

    private IEngine engine;
    private String engineName;
    private Properties properties;

    public Executor(IEngine engine, String engineName, Properties properties) {
        this.engine = engine;
        this.engineName = engineName;
        this.properties = properties;
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void execute() throws Exception {
        final boolean bytesMode = properties.getProperty("outs", "0").equals("0");
        final IOutput output = createOutput(properties, bytesMode);
        final long time;
        //
        int warm = Integer.parseInt(properties.getProperty("warm", "10"));
        int loop = Integer.parseInt(properties.getProperty("loop", "10000"));
        final String outputEncoding = properties.getProperty("outputEncoding", "UTF-8");
        // set model's capacity.
        StockModel.setCapacity(Integer.parseInt(properties.getProperty("capacity", "2")));
        // execute body.
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("outputEncoding", outputEncoding);
        model.put("items", StockModel.dummyItems());

        if (bytesMode) {
            if (engine.isSupportByteStream()) {
                time = doWork(engine, model, (OutputStream) output, loop, warm);
            } else {
                OutputStreamWriter writer;
                time = doWork(engine, model, writer = new OutputStreamWriter((OutputStream) output, outputEncoding), loop, warm);
                writer.close();
            }
        } else {
            if (engine.isSupportCharStream()) {
                time = doWork(engine, model, (Writer) output, loop, warm);
            } else {
                //assert Support ByteStream
                WriterOutputStream outputStream;
                time = doWork(engine, model, outputStream = new WriterOutputStream((Writer) output, outputEncoding), loop, warm);
                outputStream.close();
            }
        }
        Utilities.writeResult(engineName, properties, time, output.getStreamSize());
        output.close();
    }

    private IOutput createOutput(Properties properties, boolean bytesMode) throws Exception {
        final IOutput output;
        if (bytesMode) {
            output = (IOutput) Utilities.loadClass(
                    properties.getProperty("byteStream", ByteStream.class.getName()))
                    .newInstance();
        } else {
            output = (IOutput) Utilities.loadClass(
                    properties.getProperty("charStream", CharStream.class.getName()))
                    .newInstance();
        }
        output.init(properties);
        return output;
    }

    private final long doWork(final IEngine engine, final Map<String, Object> model, final OutputStream outputStream, final int count, final int warm) throws Exception {
        int i;
        for (i = 0; i < warm; i++) {
            engine.work(model, outputStream);
        }
        final long t1 = System.currentTimeMillis();
        for (i = 0; i < count; i++) {
            engine.work(model, outputStream);
        }
        return System.currentTimeMillis() - t1;
    }

    private final long doWork(final IEngine engine, final Map<String, Object> model, final Writer writer, final int count, final int warm) throws Exception {
        int i;
        for (i = 0; i < warm; i++) {
            engine.work(model, writer);
        }
        final long t1 = System.currentTimeMillis();
        for (i = 0; i < count; i++) {
            engine.work(model, writer);
        }
        return System.currentTimeMillis() - t1;
    }

    public static final void main(final String[] args) throws Exception {
        final Properties arguments = Utilities.getArguments(args);
        final String config = arguments.getProperty("-config", "benchmark.properties");
        final Properties properties = Utilities.getProperties(config);
        final String engineName = arguments.getProperty("-name", "NONE");
        final String engineClassName = arguments.getProperty("-engine");
        final IEngine engine = (IEngine) Utilities.loadClass(engineClassName).newInstance();
        new File(Utilities.getDefaultClassLoader().getResource("").getFile(), engineName + ".txt").delete();
        engine.init(properties);
        try {
            final int thread = Integer.parseInt(properties.getProperty("thread", "1"));
            if (thread > 1) {
                final ExecutorService threadPool = Executors.newFixedThreadPool(thread);
                for (int i = 0; i < thread; i++) {
                    threadPool.execute(new Executor(engine, engineName, properties));
                }
                threadPool.shutdown();
                // wait thread pool finish.
                while (!threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS));
            } else {
                new Executor(engine, engineName, properties).run();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            engine.shutdown();
        }
    }
}
