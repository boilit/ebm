package org.boilit.ebm.engines;

import org.bee.tl.core.GroupTemplate;
import org.bee.tl.core.Template;
import org.bee.tl.core.io.OutputStreamByteWriter;
import org.boilit.ebm.*;

import java.io.*;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class Beetl extends Benchmark {
    private GroupTemplate engine;
    private String templateUrl;

    @Override
    public void init() {
        templateUrl = "/beetl.html";

        engine = new GroupTemplate(new File(Benchmark.class.getResource("/templates").getPath()));
        engine.config("<!--:", "-->", "${", "}");
        engine.setCharset(this.getOutputEncoding());
        engine.enableOptimize();
        engine.enableDirectOutputByte();
        OutputStreamByteWriter.DEFAULT_BYTE_BUFFER_SIZE = 2048;
    }

    @Override
    protected void workOnBytes(List<StockModel> items) throws Exception {
        final OutputStream outputStream = this.getOutputStream();
        Template template = engine.getFileTemplate(this.templateUrl);
        template.set("items", items);
        template.set("outputEncoding", this.getOutputEncoding());
        template.getText(outputStream);
        this.close(outputStream);
    }

    @Override
    protected void workOnChars(List<StockModel> items) throws Exception {
        final Writer writer = this.getWriter();
        Template template = engine.getFileTemplate(this.templateUrl);
        template.set("items", items);
        template.set("outputEncoding", this.getOutputEncoding());
        template.getText(writer);
        this.close(writer);
    }

    public static void main(String[] args) throws Exception {
        EngineInfo ei = EngineInfoFactory.getBeetlEngineInfo();
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
