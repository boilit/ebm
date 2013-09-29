package org.boilit.ebm;

/**
 * @author Boilit
 * @see
 */
public final class EngineInfoFactory {

    public static final EngineInfo getBeetlEngineInfo() {
        EngineInfo ei = new EngineInfo();
        ei.setName("Beetl");
        ei.setVersion("1.25.01");
        ei.setClassName("org.boilit.ebm.engines.Beetl");
        ei.setJarFiles(new String[]{"beetl.1.25.01.jar"});
        return ei;
    }

    public static final EngineInfo getBslEngineInfo() {
        EngineInfo ei = new EngineInfo();
        ei.setName("Bsl");
        ei.setVersion("1.1.0");
        ei.setClassName("org.boilit.ebm.engines.Bsl");
        ei.setJarFiles(new String[]{"bsl-1.1.0.jar"});
        return ei;
    }

    public static final EngineInfo getFreeMarkerEngineInfo() {
        EngineInfo ei = new EngineInfo();
        ei.setName("FreeMarker");
        ei.setVersion("2.3.19");
        ei.setClassName("org.boilit.ebm.engines.FreeMarker");
        ei.setJarFiles(new String[]{"freemarker-2.3.19.jar"});
        return ei;
    }

    public static final EngineInfo getHttlEngineInfo() {
        EngineInfo ei = new EngineInfo();
        ei.setName("Httl");
        ei.setVersion("1.0.11");
        ei.setClassName("org.boilit.ebm.engines.Httl");
        ei.setJarFiles(new String[]{"httl-1.0.11.jar"});
        return ei;
    }

    public static final EngineInfo getJdkStringEngineInfo() {
        EngineInfo ei = new EngineInfo();
        ei.setName("StringBuilder");
        ei.setVersion("jdk"+System.getProperty("java.version"));
        ei.setClassName("org.boilit.ebm.engines.JdkString");
        ei.setJarFiles(new String[]{});
        return ei;
    }

    public static final EngineInfo getVelocityEngineInfo() {
        EngineInfo ei = new EngineInfo();
        ei.setName("Velocity");
        ei.setVersion("1.7");
        ei.setClassName("org.boilit.ebm.engines.Velocity");
        ei.setJarFiles(new String[]{"velocity-1.7.jar", "velocity-1.7-dep.jar"});
        return ei;
    }

    public static final EngineInfo getWebitEngineInfo() {
        EngineInfo ei = new EngineInfo();
        ei.setName("Webit");
        ei.setVersion("1.1.3");
        ei.setClassName("org.boilit.ebm.engines.Webit");
        ei.setJarFiles(new String[]{
                "webit-script-1.1.3.jar",
                "jodd-3.4.5.jar"
        });
        return ei;
    }
}
