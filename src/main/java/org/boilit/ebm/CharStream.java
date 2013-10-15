package org.boilit.ebm;

import java.io.*;

/**
 * @author Boilit
 * @see
 */
public final class CharStream extends Writer implements IOutput {
    private Writer writer;
    private ByteStream byteStream = new ByteStream();

    public CharStream() {
        this.writer = new OutputStreamWriter(this.byteStream);
    }

    public CharStream(final String encoding) throws UnsupportedEncodingException {
        this.writer = new OutputStreamWriter(this.byteStream, encoding);
    }

    @Override
    public void write(int c) throws IOException {
        this.writer.write(c);
    }

    @Override
    public void write(char[] cbuf) throws IOException {
        this.writer.write(cbuf);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        this.writer.write(cbuf, off, len);
    }

    @Override
    public void write(String str) throws IOException {
        this.writer.write(str);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        this.writer.write(str, off, len);
    }

    @Override
    public Writer append(CharSequence csq) throws IOException {
        return this.writer.append(csq);
    }

    @Override
    public Writer append(CharSequence csq, int start, int end) throws IOException {
        return this.writer.append(csq, start, end);
    }

    @Override
    public Writer append(char c) throws IOException {
        return this.writer.append(c);
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public final long getStreamSize() {
        return byteStream.getStreamSize();
    }
}
