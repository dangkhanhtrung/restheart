package com.fds.repository.qlvtdb;

public class CoreObject {
    private String shortName;
    private String title;
    private CoreObject _source;

    public CoreObject get_source() {
        return this._source;
    }

    public void set_source(CoreObject _source) {
        this._source = _source;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}