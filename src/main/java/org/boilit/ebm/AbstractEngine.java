package org.boilit.ebm;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractEngine implements IEngine {

    public void init(Properties properties) throws Exception {
    }

    public void work(Map<String, Object> model, Writer writer) throws Exception {
    }

    public void work(Map<String, Object> model, OutputStream outputStream) throws Exception {
    }

    public boolean isSupportByteStream() {
        return true;
    }

    public boolean isSupportCharStream() {
        return true;
    }

    public void shutdown() throws Exception {
    }
}
