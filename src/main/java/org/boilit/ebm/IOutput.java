package org.boilit.ebm;

/**
 * @author Boilit
 * @see
 */
public interface IOutput {
    public static final int OUTS_BYTE_STREAM = 0;
    public static final int OUTS_CHAR_STREAM = 1;

    public long getStreamSize();
}
