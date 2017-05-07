package ru.alex.st.hh.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import ru.alex.st.hh.web.spider.SpiderException;

public class SpiderConfigurationImpl implements SpiderConfiguration {

    private String diskStoragePath;

    private URL startUrl;

    private int depth;

    private Locale locale = Locale.getDefault();
    
    private long limit;

    @Override
    public Path getDiskStoragePath() {
        return Paths.get(diskStoragePath);
    }

    @Override
    public void setDiskStoragePath(String diskStoragePath) {
        this.diskStoragePath = diskStoragePath;
    }

    @Override
    public URL getStartUrl() {
        return startUrl;
    }

    @Override
    public void setStartUrl(String startUrl) {
        try {
            this.startUrl = new URL(startUrl);
        } catch (MalformedURLException e) {
            throw new SpiderException(String.format("Cann't create URL from %s", startUrl));
        }
    }

    @Override
    public int getDepth() {
        return depth;
    }

    @Override
    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void setLocale(String localeStringValue) {
        locale = new Locale(localeStringValue);
    }

    @Override
    public long getLinkLevelLimit() {
        return limit;
    }

    @Override
    public void setLinkLevelLimit(long limit) {
        this.limit = limit;        
    }

}
