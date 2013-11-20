package org.boilit.ebm.engine;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;
import jetbrick.template.JetConfig;
import jetbrick.template.JetEngine;
import org.boilit.ebm.AbstractEngine;

/**
 * @author Boilit
 * @see
 */
public final class JetbrickTemplate extends AbstractEngine {
    private String templateUrl;
    private JetEngine engine;

    @Override
    public final void init(Properties properties) throws Exception {
        templateUrl = "/templates/jetx.html";

        Properties prop = new Properties();
        prop.setProperty(JetConfig.COMPILE_DEBUG, "false");
        prop.setProperty(JetConfig.INPUT_ENCODING, properties.getProperty("inputEncoding", "UTF-8"));
        prop.setProperty(JetConfig.OUTPUT_ENCODING, properties.getProperty("outputEncoding", "UTF-8"));
        engine = JetEngine.create(prop);
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
