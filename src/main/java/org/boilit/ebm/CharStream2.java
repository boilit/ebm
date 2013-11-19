package org.boilit.ebm;

import java.io.*;

/**
 * @author Boilit
 * @see
 */
public final class CharStream2 extends Writer implements IOutput {

    private long streamSize = 0;

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        streamSize += len;
    }

    @Override
    public void write(int c) throws IOException {
        streamSize++;
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public final long getStreamSize() {
        return streamSize;
    }
}
