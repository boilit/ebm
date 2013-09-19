package org.boilit.ebm;

/**
 * @author Boilit
 * @see
 */
public final class EngineInfo {
    private String name;
    private String version;
    private String className;
    private String[] jarFiles;

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getVersion() {
        return version;
    }

    public final void setVersion(String version) {
        this.version = version;
    }

    public final String getClassName() {
        return className;
    }

    public final void setClassName(String className) {
        this.className = className;
    }

    public final String[] getJarFiles() {
        return jarFiles;
    }

    public final void setJarFiles(String[] jarFiles) {
        this.jarFiles = jarFiles;
    }
}
