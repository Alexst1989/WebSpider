package ru.alex.st.hh.disk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.alex.st.hh.config.MessageSource;
import ru.alex.st.hh.config.SpiderConfiguration;
import ru.alex.st.hh.tree.TreeNode;
import ru.alex.st.hh.web.LinkParser;
import ru.alex.st.hh.web.PageData;
import ru.alex.st.hh.web.spider.SpiderException;

public class DiskPageWriter {

    private static final Logger LOGGER = LogManager.getLogger(DiskPageWriter.class);

    private static final String INFO1 = "spider.diskpagewriter.info1";
    private static final String ERROR1 = "spider.diskpagewriter.error1";
    private static final String DEBUG1 = "spider.diskpagewriter.debug1";
    private static final String EXCEPTION_MSG = "spider.diskpagewriter.exc.msg";
    private static final String FILE_NOT_FOUND_ERROR_MESSAGE = "spider.webpageloader.fnf";
    private static final String CONNECTION_ERROR = "spider.webpageloader.malformedurl";
    
    private AtomicInteger fileCounter = new AtomicInteger(0);

    private static final String HTML = ".html";

    private SpiderConfiguration config;

    public DiskPageWriter(SpiderConfiguration config) {
        this.config = config;
    }

    public Path writePage(TreeNode<PageData> treeNode, LinkParser linkParser) {
        URL url = treeNode.getData().getUrl();
        LOGGER.info(MessageSource.getMessage(INFO1, config.getLocale()), url.toString());
        try (InputStream is = url.openStream()) {
            return writePage(is, getWritingPath(treeNode), linkParser);
        } catch (IOException ex) {
            LOGGER.error(MessageSource.getMessage(ERROR1, config.getLocale()), treeNode.getData().getUrl());
            LOGGER.error(MessageSource.getMessage(CONNECTION_ERROR, config.getLocale()), ex);
            throw new SpiderException(
                            String.format(MessageSource.getMessage(EXCEPTION_MSG, config.getLocale()), url.toString()),
                            ex);
        }
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
            bw.flush();
            return outPath;
        } catch (IOException e) {
            LOGGER.error(MessageSource.getMessage(FILE_NOT_FOUND_ERROR_MESSAGE, config.getLocale()), e);
            return null;
        }
    }

    private Path getWritingPath(TreeNode<PageData> treeNode) throws IOException {
        String randomFileName = String.format("%s%s", fileCounter.incrementAndGet(), HTML);
        if (treeNode.isRoot()) {
            return Paths.get(config.getDiskStoragePath().toString(), randomFileName);
        }
        Path parentPath = treeNode.getParent().getData().getDiskPath();
        String s = parentPath.toString();
        Path dir = Paths.get(s.substring(0, s.length() - HTML.length()));
        if (!Files.exists(dir) && !Files.isDirectory(dir)) {
            Files.createDirectory(dir);
            LOGGER.debug(MessageSource.getMessage(DEBUG1, config.getLocale()), dir.toString());
        }

        return Paths.get(dir.toString(), randomFileName);
    }

}
