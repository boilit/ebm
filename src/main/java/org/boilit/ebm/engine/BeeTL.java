package org.boilit.ebm.engine;

import org.bee.tl.core.GroupTemplate;
import org.bee.tl.core.Template;
import org.bee.tl.core.io.OutputStreamByteWriter;
import org.boilit.ebm.AbstractEngine;
import org.boilit.ebm.IEngine;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public final class BeeTL extends AbstractEngine {
    private String templateUrl;
    private GroupTemplate engine;

    @Override
    public String getName() {
        return "BeeTL";
    }

    @Override
    public String getVersion() {
        return "1.25.01";
    }

    @Override
    public final void init(Properties properties) throws Exception {
        templateUrl = "/beetl.html";

        engine = new GroupTemplate(new File(BeeTL.class.getResource("/templates").getPath()));
        engine.config("<!--:", "-->", "${", "}");
        engine.setCharset(properties.getProperty("outputEncoding", "UTF-8"));
        engine.enableOptimize();
        engine.enableDirectOutputByte();
        OutputStreamByteWriter.DEFAULT_BYTE_BUFFER_SIZE = 2048;
    }

    @Override
    public void work(final Map<String, Object> model, final Writer writer) throws Exception {
        final Template template = engine.getFileTemplate(this.templateUrl);
        Map.Entry<String, Object> entry;
        final Iterator<Map.Entry<String, Object>> iterator = model.entrySet().iterator();
        while (iterator.hasNext()) {
            entry = iterator.next();
            template.set(entry.getKey(), entry.getValue());
        }
        template.getText(writer);
    }

    @Override
    public void work(final Map<String, Object> model, final OutputStream outputStream) throws Exception {
        final Template template = engine.getFileTemplate(this.templateUrl);
        Map.Entry<String, Object> entry;
        final Iterator<Map.Entry<String, Object>> iterator = model.entrySet().iterator();
        while (iterator.hasNext()) {
            entry = iterator.next();
            template.set(entry.getKey(), entry.getValue());
        }
        template.getText(outputStream);
    }
}
