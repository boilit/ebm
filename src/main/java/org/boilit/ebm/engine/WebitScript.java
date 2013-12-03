package org.boilit.ebm.engine;

import org.boilit.ebm.AbstractEngine;

import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zqq
 * @see
 */
public final class WebitScript extends AbstractEngine {

    private String templateUrl;
    private webit.script.Engine engine;

    @Override
    public final void init(Properties properties) throws Exception {
        templateUrl = "/templates/webit.html";

        Map parameters = new HashMap();
        parameters.put("webit.script.Engine.encoding", properties.getProperty("outputEncoding", "UTF-8"));
        parameters.put("webit.script.loaders.impl.ClasspathLoader.encoding", properties.getProperty("inputEncoding", "UTF-8"));

        String outMode = properties.getProperty("outs", "0");
        if (outMode.equals("0")) {
            parameters.put("webit.script.Engine.textStatmentFactoryClass", "webit.script.core.text.impl.CharArrayTextStatmentFactory");
        } else if (outMode.equals("1")) {
            parameters.put("webit.script.Engine.textStatmentFactoryClass", "webit.script.core.text.impl.ByteArrayTextStatmentFactory");
        } //else use default
        engine = webit.script.Engine.createEngine("", parameters);
    }

    @Override
    public void work(Map model, Writer writer) throws Exception {
        engine.getTemplate(this.templateUrl).merge(model, writer);
    }

    @Override
    public void work(Map model, OutputStream outputStream) throws Exception {
        engine.getTemplate(this.templateUrl).merge(model, outputStream);
    }
}
