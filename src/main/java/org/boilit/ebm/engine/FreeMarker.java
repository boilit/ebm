package org.boilit.ebm.engine;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import org.boilit.ebm.AbstractEngine;

import java.io.File;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public final class FreeMarker extends AbstractEngine {
    private String templateUrl;
    private Configuration engine;

    @Override
    public final void init(Properties properties) throws Exception {
        templateUrl = "/ftl.html";
        engine = new Configuration();
        engine.setDefaultEncoding(properties.getProperty("outputEncoding", "UTF-8"));
        engine.setDirectoryForTemplateLoading(new File(FreeMarker.class.getResource("/templates").getPath()));
        engine.setObjectWrapper(new DefaultObjectWrapper());
    }

    @Override
    public void work(final Map<String, Object> model, final Writer writer) throws Exception {
        this.engine.getTemplate(this.templateUrl).process(model, writer);
    }

    @Override
    public final boolean isSupportByteStream() {
        return false;
    }
}
