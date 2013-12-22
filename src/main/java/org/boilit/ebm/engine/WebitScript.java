package org.boilit.ebm.engine;

import org.boilit.ebm.AbstractEngine;

import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import webit.script.CFG;

/**
 * @author zqq
 * @see
 */
public class WebitScript extends AbstractEngine {

    private String templateUrl;
    private webit.script.Engine engine;

    @Override
    public final void init(Properties properties) throws Exception {
        templateUrl = "/templates/webit.html";

        Map<String, Object> parameters = new HashMap<String, Object>();
        initConfig(parameters, properties);

        engine = webit.script.Engine.createEngine("", parameters);
    }

    protected void initConfig(Map<String, Object> parameters, Properties properties) {

        parameters.put(CFG.OUT_ENCODING, properties.getProperty("outputEncoding", "UTF-8"));
        parameters.put(CFG.CLASSPATH_LOADER_ENCODING, properties.getProperty("inputEncoding", "UTF-8"));

        String outMode = properties.getProperty("outs", "0");
        if (outMode.equals("0")) {
            parameters.put(CFG.TEXT_FACTORY, CFG.BYTE_ARRAY_TEXT_FACTORY);
        } else if (outMode.equals("1")) {
            parameters.put(CFG.TEXT_FACTORY, CFG.CHAR_ARRAY_TEXT_FACTORY);
        } //else use default
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
