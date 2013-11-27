package org.boilit.ebm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public final class ByteStream extends OutputStream implements IOutput {
    private long streamSize = 0;

    @Override
    public final void write(byte[] b) throws IOException {
        streamSize += b.length;
    }

    @Override
    public final void write(byte[] b, int off, int len) throws IOException {
        streamSize += len;
    }

    @Override
    public final void flush() throws IOException {
    }

    @Override
    public final void write(int b) throws IOException {
        streamSize++;
    }

    @Override
    public final long getStreamSize() {
        return streamSize;
    }

    public void init(Properties properties) {
    }
}
