package org.boilit.ebm;

import java.io.*;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public abstract class Benchmark implements Runnable {
    private int warmCount = 10;
    private int loopCount = 10000;
    private long elapsedTime = 0l;
    private String inputEncoding = System.getProperty("file.encoding");
    private String outputEncoding = "UTF-8";
    private boolean buffered = false;
    private OutputMode outputMode = OutputMode.BYTES;
    private ByteArrayOutputStream outputStream;

    @Override
    public void run() {
        try {
            List<StockModel> items = StockModel.dummyItems();
            this.init();
            // warm
            switch (outputMode) {
                case CHARS: {
                    final int warnCount = this.warmCount;
                    for (int i = 0; i < warnCount; i++) {
                        this.workOnChars(items);
                    }
                    break;
                }
                default: {
                    final int warnCount = this.warmCount;
                    for (int i = 0; i < warnCount; i++) {
                        this.workOnBytes(items);
                    }
                    break;
                }
            }
            // loop
            switch (outputMode) {
                case CHARS: {
                    final int loopCount = this.loopCount;
                    long t1 = System.currentTimeMillis();
                    for (int i = 0; i < loopCount; i++) {
                        this.workOnChars(items);
                    }
                    long t2 = System.currentTimeMillis();
                    this.elapsedTime = t2 - t1;
                    break;
                }
                default: {
                    final int loopCount = this.loopCount;
                    long t1 = System.currentTimeMillis();
                    for (int i = 0; i < loopCount; i++) {
                        this.workOnBytes(items);
                    }
                    long t2 = System.currentTimeMillis();
                    this.elapsedTime = t2 - t1;
                    break;
                }
            }
            this.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected OutputStream getOutputStream() {
        outputStream = new ByteArrayOutputStream(1024);
        if (this.isBuffered()) {
            return new BufferedOutputStream(outputStream);
        }
        return outputStream;
    }

    protected Writer getWriter() throws UnsupportedEncodingException {
        outputStream = new ByteArrayOutputStream(1024);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, this.getOutputEncoding());
        if (this.isBuffered()) {
            return new BufferedWriter(writer);
        }
        return writer;
    }

    protected void close(OutputStream outputStream) throws IOException {
        outputStream.close();
    }

    protected void close(Writer writer) throws IOException {
        writer.close();
    }

    public abstract void init();

    protected abstract void workOnBytes(List<StockModel> items) throws Exception;

    protected abstract void workOnChars(List<StockModel> items) throws Exception;

    protected void shutdown() throws Exception {
    }

    public final int getLoopCount() {
        return loopCount;
    }

    public final void setLoopCount(final int loopCount) {
        this.loopCount = loopCount;
    }

    public final int getWarmCount() {
        return warmCount;
    }

    public final void setWarmCount(final int warmCount) {
        this.warmCount = warmCount;
    }

    public final String getInputEncoding() {
        return inputEncoding;
    }

    public final void setInputEncoding(final String inputEncoding) {
        this.inputEncoding = inputEncoding;
    }

    public final String getOutputEncoding() {
        return outputEncoding;
    }

    public final void setOutputEncoding(final String outputEncoding) {
        this.outputEncoding = outputEncoding;
    }

    public final boolean isBuffered() {
        return buffered;
    }

    public final void setBuffered(final boolean buffered) {
        this.buffered = buffered;
    }

    public final OutputMode getOutputModel() {
        return outputMode;
    }

    public final int getOutputSize() {
        if(outputStream == null) {
            return 0;
        }
        return outputStream.size();
    }

    public final void writeTo(OutputStream outputStream) throws Exception {
        if(outputStream == null || this.outputStream == null) {
            return;
        }
        outputStream.write(this.outputStream.toByteArray());
    }

    public final void setOutputModel(final OutputMode outputMode) {
        this.outputMode = outputMode;
    }

    public final long getElapsedTime() {
        return elapsedTime;
    }
}
