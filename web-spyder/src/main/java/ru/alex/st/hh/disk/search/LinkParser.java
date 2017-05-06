package ru.alex.st.hh.disk.search;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTMLEditorKit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LinkParser {
    
    private static final Logger LOGGER = LogManager.getLogger(LinkParser.class);

    private static final Pattern RELATED_LINK = Pattern.compile("href=\".+?\"");
    private static final Pattern ABSOLUT_LINK = Pattern.compile("href=\"(http|https)://.+?\"");

    public LinkParser() {

    }

    public Set<String> findAndSave(String str) {
        Set<String> foundLinks = new LinkedHashSet<>();
        Matcher absolutMatcher = ABSOLUT_LINK.matcher(str);
        while (absolutMatcher.find()) {
            foundLinks.add(absolutMatcher.group().split("\"")[1]);
        }
        Matcher relatedMatcher = RELATED_LINK.matcher(str);
        while (relatedMatcher.find()) {
            foundLinks.add(relatedMatcher.group().split("\"")[1]);
        }
        return foundLinks;
    }

    public static void main(String args[]) {
        
        String s1 = "<link rel=\"stylesheet\" href=\"/w/load.php?debug=false&amp;lang=ru&amp;modules=site.styles&amp;only=styles&amp;skin=vector\"/>";
        String s2 = "<link rel=\"canonical\" href=\"https://ru.wikipedia.org/wiki/%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0\"/>";
        String s3 = "<a href=\"/wiki/%D0%9C%D1%83%D0%B6%D1%81%D0%BA%D0%BE%D0%B9_%D1%80%D0%BE%D0%B4\" title=\"Мужской род\">мужского рода</a> выделяется подкласс имён — названий лиц мужского пола, противопоставленный всем прочим существительным. Помимо общей ";

        LinkParser linkParser = new LinkParser();
        LOGGER.info(linkParser.findAndSave(s2));
        LOGGER.info(linkParser.findAndSave(s1));
        LOGGER.info(linkParser.findAndSave(s3));
        
    }

    public static class SpiderParserCallback extends HTMLEditorKit.ParserCallback {

        public SpiderParserCallback() {

        }

        @Override
        public void handleText(char[] data, int pos) {

        }

    }

}
