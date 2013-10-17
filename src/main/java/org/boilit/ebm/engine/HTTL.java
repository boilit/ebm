package org.boilit.ebm.engine;

import httl.Engine;
import org.boilit.ebm.AbstractEngine;
import org.boilit.ebm.IEngine;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public final class HTTL extends AbstractEngine {
    private String templateUrl;
    private Engine engine;

    @Override
    public final void init(Properties properties) throws Exception {
        templateUrl = "/templates/httl.html";

        Properties prop = new Properties();
        prop.setProperty("import.packages", "java.util");
        prop.setProperty("filter", "null");
        prop.setProperty("logger", "null");
        prop.setProperty("input.encoding", properties.getProperty("inputEncoding", "UTF-8"));
        prop.setProperty("output.encoding", properties.getProperty("outputEncoding", "UTF-8"));
        engine = Engine.getEngine(prop);
    }

    @Override
    public void work(final Map model, final Writer writer) throws Exception {
        engine.getTemplate(this.templateUrl).render(model, writer);
    }

    @Override
    public void work(final Map model, final OutputStream outputStream) throws Exception {
        engine.getTemplate(this.templateUrl).render(model, outputStream);
    }
}
