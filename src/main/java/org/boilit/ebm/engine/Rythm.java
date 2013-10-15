package org.boilit.ebm.engine;

import org.boilit.ebm.AbstractEngine;
import org.rythmengine.RythmEngine;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public final class Rythm extends AbstractEngine {
    private String templateUrl;
    private RythmEngine engine;

    @Override
    public String getName() {
        return "Rythm";
    }

    @Override
    public String getVersion() {
        return "1.0.0-b10-SNAPSHOT";
    }

    @Override
    public final void init(Properties properties) throws Exception {
        templateUrl = "templates/rythm.html";

        Properties p = new Properties();
        p.put("log.enabled", false);
        p.put("feature.smart_escape.enabled", false);
        p.put("feature.transform.enabled", false);
        //p.put("codegen.dynamic_exp.enabled", true);
        //p.put("built_in.code_type", "false");
        //p.put("built_in.transformer", "false");
        //p.put("engine.file_write", "false");
        //p.put("codegen.compact.enabled", "false");
        //p.put("home.template", "p:/template-engine-benchmarks");
        //p.put("home.tmp", "c:\\tmp");
        //p.put("engine.mode", Rythm.Mode.dev);
        engine = new RythmEngine(p);
    }

    @Override
    public final void work(final Map<String, Object> model, final Writer writer) throws Exception {
        engine.render(writer, templateUrl, model.get("outputEncoding"), model.get("items"));
    }

    @Override
    public final void work(final Map<String, Object> model, final OutputStream outputStream) throws Exception {
        engine.render(outputStream, templateUrl, model.get("outputEncoding"), model.get("items"));
    }

    @Override
    public final void shutdown() {
        engine.shutdown();
    }
}
