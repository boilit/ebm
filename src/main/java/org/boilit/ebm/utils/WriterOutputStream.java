package org.boilit.ebm.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

public class WriterOutputStream extends OutputStream {

    private static final int DEFAULT_BUFFER_SIZE = 1024;

    private final Writer writer;
    private final CharsetDecoder decoder;

    private final ByteBuffer decoderIn;

    private final CharBuffer decoderOut;

    public WriterOutputStream(Writer writer, String charsetName) {
        this.writer = writer;
        decoder = Charset.forName(charsetName).newDecoder();
        decoder.onMalformedInput(CodingErrorAction.REPLACE);
        decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
        decoder.replaceWith("?");
        decoderOut = CharBuffer.allocate(DEFAULT_BUFFER_SIZE);
        decoderIn = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        while (len > 0) {
            int c = Math.min(len, decoderIn.remaining());
            decoderIn.put(b, off, c);
            processInput(false);
            len -= c;
            off += c;
        }
        flushOutput();
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(int b) throws IOException {
        write(new byte[]{(byte) b}, 0, 1);
    }

    @Override
    public void flush() throws IOException {
        flushOutput();
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        processInput(true);
        flushOutput();
        writer.close();
    }

    private void processInput(boolean endOfInput) throws IOException {
        decoderIn.flip();
        CoderResult coderResult;
        while (true) {
            coderResult = decoder.decode(decoderIn, decoderOut, endOfInput);
            if (coderResult.isOverflow()) {
                flushOutput();
            } else if (coderResult.isUnderflow()) {
                break;
            } else {
                throw new IOException("Unexpected coder result");
            }
        }
        decoderIn.compact();
    }

    private void flushOutput() throws IOException {
        if (decoderOut.position() > 0) {
            writer.write(decoderOut.array(), 0, decoderOut.position());
            decoderOut.rewind();
        }
    }
}
