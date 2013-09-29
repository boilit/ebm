package org.boilit.ebm.engines;

import org.boilit.ebm.*;
import webit.script.Engine;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public final class Webit extends Benchmark {
    private Engine engine_bytes;
    private Engine engine_chars;
    private String templateUrl;

    @Override
    public void init() {
        templateUrl = "/templates/webit.html";

        Map parameters = new HashMap();
        parameters.put("webit.script.Engine.encoding", this.getOutputEncoding());

        parameters.put("webit.script.Engine.textStatmentFactoryClass", "webit.script.core.text.impl.CharArrayTextStatmentFactory");
        engine_chars = Engine.createEngine(null, parameters);

        parameters.put("webit.script.Engine.textStatmentFactoryClass", "webit.script.core.text.impl.ByteArrayTextStatmentFactory");
        engine_bytes = Engine.createEngine(null, parameters);

        //engine.setEncoding(this.getOutputEncoding());
        //engine.setEnableAsmNative(true);
    }

    @Override
    protected void workOnBytes(List<StockModel> items) throws Exception {
        final OutputStream outputStream = this.getOutputStream();
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("items", items);
        model.put("outputEncoding", this.getOutputEncoding());
        this.engine_bytes.getTemplate(this.templateUrl).merge(model, outputStream);
        this.close(outputStream);
    }

    @Override
    protected void workOnChars(List<StockModel> items) throws Exception {
        final Writer writer = this.getWriter();
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("items", items);
        model.put("outputEncoding", this.getOutputEncoding());
        this.engine_chars.getTemplate(this.templateUrl).merge(model, writer);
        this.close(writer);
    }

    public static void main(String[] args) throws Exception {
        EngineInfo ei = EngineInfoFactory.getWebitEngineInfo();
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