package de.hszigr.gpics.controller;

import org.primefaces.model.StreamedContent;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 04.05.11
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public class Bild {

    private String name;
    private String path;
    private StreamedContent content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public StreamedContent getContent() {
        return content;
    }

    public void setContent(StreamedContent content) {
        this.content = content;
    }
}
