package org.boilit.ebm.engine;

import org.boilit.ebm.AbstractEngine;
import org.boilit.ebm.IEngine;

import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public final class WebitScript extends AbstractEngine {
    private String templateUrl;
    private webit.script.Engine engine_bytes;
    private webit.script.Engine engine_chars;

    @Override
    public String getName() {
        return "webit-script";
    }

    @Override
    public String getVersion() {
        return "1.1.5";
    }

    @Override
    public String getSite() {
        return "https://github.com/zqq90/webit-script";
    }

    @Override
    public final void init(Properties properties) throws Exception {
        templateUrl = "/templates/webit.html";

        Map parameters = new HashMap();
        parameters.put("webit.script.Engine.encoding", properties.getProperty("outputEncoding", "UTF-8"));

        parameters.put("webit.script.Engine.textStatmentFactoryClass", "webit.script.core.text.impl.CharArrayTextStatmentFactory");
        engine_chars = webit.script.Engine.createEngine(null, parameters);

        parameters.put("webit.script.Engine.textStatmentFactoryClass", "webit.script.core.text.impl.ByteArrayTextStatmentFactory");
        engine_bytes = webit.script.Engine.createEngine(null, parameters);
    }

    @Override
    public void work(Map model, Writer writer) throws Exception {
        engine_chars.getTemplate(this.templateUrl).merge(model, writer);
    }

    @Override
    public void work(Map model, OutputStream outputStream) throws Exception {
        engine_bytes.getTemplate(this.templateUrl).merge(model, outputStream);
    }
}
