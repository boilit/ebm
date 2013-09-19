package org.boilit.ebm.engines;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import org.boilit.ebm.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public final class FreeMarker extends Benchmark {
    private Configuration engine;
    private String templateUrl;

    @Override
    public void init() {
        templateUrl = "/ftl.html";

        try{
            engine = new Configuration();
            engine.setDefaultEncoding(this.getOutputEncoding());
            engine.setDirectoryForTemplateLoading(new File(Benchmark.class.getResource("/templates").getPath()));
            engine.setObjectWrapper(new DefaultObjectWrapper());
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void workOnBytes(List<StockModel> items) throws Exception {
        final Writer writer = this.getWriter();
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("items", items);
        model.put("outputEncoding", this.getOutputEncoding());
        this.engine.getTemplate(this.templateUrl).process(model, writer);
        this.close(writer);
    }

    @Override
    protected void workOnChars(List<StockModel> items) throws Exception {
        final Writer writer = this.getWriter();
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("items", items);
        model.put("outputEncoding", this.getOutputEncoding());
        this.engine.getTemplate(this.templateUrl).process(model, writer);
        this.close(writer);
    }

    public static EngineInfo getEngineInfo() {
        EngineInfo ei = new EngineInfo();
        ei.setName("FreeMarker");
        ei.setVersion("2.3.19");
        ei.setClassName(FreeMarker.class.getName());
        ei.setJarFiles(new String[]{"freemarker-2.3.19.jar"});
        return ei;
    }

    public static void main(String[] args) throws Exception {
        EngineInfo ei = getEngineInfo();
        Benchmark benchmark = Utilities.inject(args);
        benchmark.run();

        File file = new File(Utilities.getClassPath(), ei.getName().concat(".txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(ei.getName() + ";");
        writer.write(ei.getVersion() + ";");
        writer.write(benchmark.getElapsedTime() + ";");
        writer.write(String.valueOf(benchmark.getOutputSize()));
        writer.flush();
        writer.close();
    }
}
