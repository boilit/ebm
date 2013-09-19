package org.boilit.ebm.engines;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.boilit.ebm.*;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public final class Velocity extends Benchmark {
    private VelocityEngine engine;
    private String templateUrl;

    @Override
    public void init() {
        templateUrl = "/vm.html";

        engine = new VelocityEngine();
        //engine.setProperty("file.resource.loader.path", "templates");
        //engine.setProperty("file.resource.loader.cache", "true");
        //engine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        try {
            //engine.init();
            Properties properties = new Properties();
            InputStream inputStream = Benchmark.class.getResourceAsStream("/velocity.properties");
            properties.load(inputStream);
            inputStream.close();
            properties.setProperty("file.resource.loader.path", Benchmark.class.getResource("/templates").getPath());
            properties.setProperty("input.encoding", this.getInputEncoding());
            properties.setProperty("output.encoding ", this.getOutputEncoding());
            engine.init(properties);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void workOnBytes(List<StockModel> items) throws Exception {
        final Writer writer = this.getWriter();
        VelocityContext model = new VelocityContext();
        model.put("items", items);
        model.put("outputEncoding", this.getOutputEncoding());
        this.engine.getTemplate(this.templateUrl).merge(model, writer);
        this.close(writer);
    }

    @Override
    protected void workOnChars(List<StockModel> items) throws Exception {
        final Writer writer = this.getWriter();
        VelocityContext model = new VelocityContext();
        model.put("items", items);
        model.put("outputEncoding", this.getOutputEncoding());
        this.engine.getTemplate(this.templateUrl).merge(model, writer);
        this.close(writer);
    }

    public static EngineInfo getEngineInfo() {
        EngineInfo ei = new EngineInfo();
        ei.setName("Velocity");
        ei.setVersion("1.7");
        ei.setClassName(Velocity.class.getName());
        ei.setJarFiles(new String[]{"velocity-1.7.jar", "velocity-1.7-dep.jar"});
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
