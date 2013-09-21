package org.boilit.ebm.engines;

import org.boilit.bsl.Engine;
import org.boilit.bsl.xtc.ExtremeCompressor;
import org.boilit.ebm.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public final class Bsl extends Benchmark {
    private Engine engine;
    private String templateUrl;

    @Override
    public void init() {
        templateUrl = Benchmark.class.getResource("/templates/bsl.html").getPath();

        engine = new Engine();
        engine.setInputEncoding(this.getInputEncoding());
        engine.setOutputEncoding(this.getOutputEncoding());
        engine.setSpecifiedEncoder(true);
        engine.setUseTemplateCache(true);
        engine.setTextCompressor(new ExtremeCompressor());
    }

    @Override
    protected void workOnBytes(List<StockModel> items) throws Exception {
        final OutputStream outputStream = this.getOutputStream();
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("items", items);
        model.put("outputEncoding", this.getOutputEncoding());
        this.engine.getTemplate(this.templateUrl).execute(model, outputStream);
        this.close(outputStream);
    }

    @Override
    protected void workOnChars(List<StockModel> items) throws Exception {
        final Writer writer = this.getWriter();
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("items", items);
        model.put("outputEncoding", this.getOutputEncoding());
        this.engine.getTemplate(this.templateUrl).execute(model, writer);
        this.close(writer);
    }

    public static void main(String[] args) throws Exception {
        EngineInfo ei = EngineInfoFactory.getBslEngineInfo();
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
