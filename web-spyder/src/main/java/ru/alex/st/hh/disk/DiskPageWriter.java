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
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.alex.st.hh.config.MessageSource;
import ru.alex.st.hh.config.SpyderConfiguration;
import ru.alex.st.hh.programm.Programm;

public class DiskPageWriter {
    // TODO idea to create general interface for tree and disk storage

    private static final Logger LOGGER = LogManager.getLogger(Programm.class);

    private static final String FILE_NOT_FOUND_ERROR_MESSAGE = "spyder.webpageloader.fnf";
    private static final String MALFORMED_URL_ERROR_MESSAGE = "spyder.webpageloader.malformedurl";
    private static final String CONNECTION_ERROR = "spyder.webpageloader.malformedurl";


    private SpyderConfiguration config;

    public DiskPageWriter(SpyderConfiguration config) {
        this.config = config;
    }
    
    public void writePage(String urlString) {
        //TODO fix error
        writePage(urlString, urlString);
    }

    public void writePage(String urlString, String fileName) {
        try {
            URL url = new URL(urlString);
            try (InputStream is = url.openStream()) {
                writePage(is, fileName);
            } catch (IOException ex) {
                LOGGER.error(MessageSource.getMessage(CONNECTION_ERROR, config.getLocale()), ex);
            }
        } catch (MalformedURLException ex) {
            LOGGER.error(MessageSource.getMessage(MALFORMED_URL_ERROR_MESSAGE, config.getLocale()), ex);
        }

    }

    public void writePage(InputStream is, String fileName) {
        try (InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        FileOutputStream fos = new FileOutputStream(
                                        Paths.get(config.getDiskStoragePath(), fileName).toFile(), false);
                        OutputStreamWriter fow = new OutputStreamWriter(fos);
                        BufferedWriter bw = new BufferedWriter(fow)) {
            String s = null;
            while ((s = br.readLine()) != null) {
                bw.write(s);
                bw.write('\r');
            }
        } catch (IOException e) {
            LOGGER.error(MessageSource.getMessage(FILE_NOT_FOUND_ERROR_MESSAGE, config.getLocale()), e);
        }
    }

}
