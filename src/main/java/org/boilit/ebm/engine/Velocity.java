package org.boilit.ebm.engine;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.boilit.ebm.AbstractEngine;
import org.boilit.ebm.IEngine;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public final class Velocity extends AbstractEngine {
    private String templateUrl;
    private VelocityEngine engine;

    @Override
    public String getName() {
        return "Velocity";
    }

    @Override
    public String getVersion() {
        return "1.7";
    }

    @Override
    public String getSite() {
        return "http://velocity.apache.org";
    }

    @Override
    public final void init(Properties properties) throws Exception {
        templateUrl = "/vm.html";

        engine = new VelocityEngine();
        //engine.setProperty("file.resource.loader.path", "templates");
        //engine.setProperty("file.resource.loader.cache", "true");
        //engine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        //engine.init();
        Properties prop = new Properties();
        InputStream inputStream = Velocity.class.getResourceAsStream("/velocity.properties");
        prop.load(inputStream);
        inputStream.close();
        prop.setProperty("file.resource.loader.path", Velocity.class.getResource("/templates").getPath());
        prop.setProperty("input.encoding", properties.getProperty("inputEncoding", "UTF-8"));
        prop.setProperty("output.encoding", properties.getProperty("outputEncoding", "UTF-8"));
        engine.init(prop);
    }

    @Override
    public void work(Map model, Writer writer) throws Exception {
        engine.getTemplate(this.templateUrl).merge(new VelocityContext(model), writer);
    }

    @Override
    public final boolean isSupportByteStream() {
        return false;
    }
}
