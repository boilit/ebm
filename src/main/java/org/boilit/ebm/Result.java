package org.boilit.ebm;

public final class Result {
    private String name;
    private String site;
    private long time;
    private long size;
    private long tps;

    public final String getName() {
        return this.name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getSite() {
        return this.site;
    }

    public final void setSite(String site) {
        this.site = site;
    }

    public final long getTime() {
        return this.time;
    }

    public final void setTime(long time) {
        this.time = time;
    }

    public final long getSize() {
        return this.size;
    }

    public final void setSize(long size) {
        this.size = size;
    }

    public final long getTps() {
        return this.tps;
    }

    public final void setTps(long tps) {
        this.tps = tps;
    }
}
