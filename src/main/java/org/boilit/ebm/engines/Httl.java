package org.boilit.ebm.engines;

import httl.Engine;
import org.boilit.ebm.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public final class Httl extends Benchmark {
    private Engine engine;
    private String templateUrl;

    @Override
    public void init() {
        templateUrl = "/templates/httl.html";

        Properties prop = new Properties();
        prop.setProperty("import.packages", "java.util");
        prop.setProperty("filter", "null");
        prop.setProperty("logger", "null");
        prop.setProperty("input.encoding", this.getInputEncoding());
        prop.setProperty("output.encoding", this.getOutputEncoding());
        engine = Engine.getEngine(prop);
    }

    @Override
    protected void workOnBytes(List<StockModel> items) throws Exception {
        final OutputStream outputStream = this.getOutputStream();
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("items", items);
        model.put("outputEncoding", this.getOutputEncoding());
        engine.getTemplate(this.templateUrl).render(model, outputStream);
        this.close(outputStream);
    }

    @Override
    protected void workOnChars(List<StockModel> items) throws Exception {
        final String templateUrl = this.templateUrl;
        final Writer writer = this.getWriter();
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("items", items);
        model.put("outputEncoding", this.getOutputEncoding());
        engine.getTemplate(templateUrl).render(model, writer);
        this.close(writer);
    }

    public static void main(String[] args) throws Exception {
        EngineInfo ei = EngineInfoFactory.getHttlEngineInfo();
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
