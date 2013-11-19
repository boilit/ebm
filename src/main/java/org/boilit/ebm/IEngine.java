package org.boilit.ebm;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public interface IEngine {
//    public abstract String getName();
//
//    public abstract String getVersion();
//
//    public abstract String getSite();

    public abstract void init(Properties properties) throws Exception;

    public abstract void work(Map<String, Object> model, Writer writer) throws Exception;

    public abstract void work(Map<String, Object> model, OutputStream outputStream) throws Exception;

    public abstract boolean isSupportByteStream();

    public abstract boolean isSupportCharStream();

    public abstract void shutdown() throws Exception;
}
