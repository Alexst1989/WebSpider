package ru.alex.st.hh.disk.search;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.alex.st.hh.config.MessageSource;
import ru.alex.st.hh.config.SpiderConfiguration;
import ru.alex.st.hh.tree.TreeNode;
import ru.alex.st.hh.web.PageData;

public class DiskSearcher {

    private static final Logger LOGGER = LogManager.getLogger(DiskSearcher.class);

    private static final String ERROR1 = "spider.disksearcher.error1";
    private static final String ERROR2 = "spider.disksearcher.error2";
    private static final String WARN1 = "spider.disksearcher.warn1";  
    
    private SpiderConfiguration config;

    private String wordToFind;

    private TreeNode<PageData> treeNode;

    private Pattern searhPattern;

    public DiskSearcher(SpiderConfiguration config, TreeNode<PageData> treeNode, String wordToFind) {
        this.config = config;
        this.wordToFind = wordToFind;
        this.treeNode = treeNode;
        searhPattern = Pattern.compile(String.format("%s", this.wordToFind));
    }

    public SearchResult search() {
        SearchResult result = new SearchResult();
        for (TreeNode<PageData> pageData : treeNode) {
            findInFile(pageData, result);
        }
        return result;
    }

    private void findInFile(TreeNode<PageData> pageData, SearchResult result) {
        Path path = pageData.getData().getDiskPath();
        String link = pageData.getData().getUrl().toString();
        if (path != null && link != null) {
            int row = 0;
            try (FileInputStream fis = new FileInputStream(path.toFile());
                            InputStreamReader isr = new InputStreamReader(fis);
                            BufferedReader br = new BufferedReader(isr)) {
                String s = null;
                while ((s = br.readLine()) != null) {
                    row++;
                    parseLine(s, result, row, link, path);
                }
            } catch (FileNotFoundException e) {
                LOGGER.error(MessageSource.getMessage(ERROR1, config.getLocale()), path.toString(), link);
            } catch (IOException e) {
                LOGGER.error(MessageSource.getMessage(ERROR2, config.getLocale()), e);
            }
        } else {
            LOGGER.warn(MessageSource.getMessage(WARN1, config.getLocale()), link);
        }
    }

    private void parseLine(String s, SearchResult result, int row, String link, Path path) {
        Matcher matcher = searhPattern.matcher(s);
        while (matcher.find()) {
            result.addOccurrence(new Occurrence(link, row, matcher.start(), matcher.group(), path));
        }
    }

}
