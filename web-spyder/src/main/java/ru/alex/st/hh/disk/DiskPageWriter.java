package ru.alex.st.hh.disk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.alex.st.hh.config.MessageSource;
import ru.alex.st.hh.config.SpiderConfiguration;
import ru.alex.st.hh.programm.Programm;
import ru.alex.st.hh.web.LinkParser;

public class DiskPageWriter {

    private static final Logger LOGGER = LogManager.getLogger(Programm.class);

    private static final String FILE_NOT_FOUND_ERROR_MESSAGE = "spider.webpageloader.fnf";
    private static final String MALFORMED_URL_ERROR_MESSAGE = "spider.webpageloader.malformedurl";
    private static final String CONNECTION_ERROR = "spider.webpageloader.malformedurl";

    private SpiderConfiguration config;

    public DiskPageWriter(SpiderConfiguration config) {
        this.config = config;
    }

    public Path writePage(String urlString, String fileName, LinkParser linkParser) {
        try {
            URL url = new URL(urlString);
            try (InputStream is = url.openStream()) {
                return writePage(is, Paths.get(config.getDiskStoragePath(), fileName), linkParser);
            } catch (IOException ex) {
                LOGGER.error(MessageSource.getMessage(CONNECTION_ERROR, config.getLocale()), ex);
            }
        } catch (MalformedURLException ex) {
            LOGGER.error(MessageSource.getMessage(MALFORMED_URL_ERROR_MESSAGE, config.getLocale()), ex);
        }
        return null;
    }

    public Path writePage(InputStream is, Path outPath, LinkParser linkParser) {
        try (InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        FileOutputStream fos = new FileOutputStream(outPath.toFile(), false);
                        OutputStreamWriter fow = new OutputStreamWriter(fos);
                        BufferedWriter bw = new BufferedWriter(fow)) {
            String s = null;
            while ((s = br.readLine()) != null) {
                linkParser.findLinks(s);
                bw.write(s);
                bw.write(System.lineSeparator());
            }
            return outPath;
        } catch (IOException e) {
            LOGGER.error(MessageSource.getMessage(FILE_NOT_FOUND_ERROR_MESSAGE, config.getLocale()), e);
            return null;
        }
    }

}
