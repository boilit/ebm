package org.boilit.ebm;

import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public interface IOutput {

    public static final int OUTS_BYTE_STREAM = 0;
    public static final int OUTS_CHAR_STREAM = 1;

    public void init(Properties properties) throws Exception;

    public long getStreamSize();

    public void close() throws Exception;
}
