package ru.alex.st.hh.disk.search;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTMLEditorKit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LinkParser {
    
    private static final Logger LOGGER = LogManager.getLogger(LinkParser.class);

    private static final Pattern RELATED_LINK = Pattern.compile("href=\".+\"");
    private static final Pattern ABSOLUT_LINK = Pattern.compile("href=\"http.?:.+\"");

    public LinkParser() {

    }

    public List<String> findAndSave(String str) {
        LinkedList<String> foundLinks = new LinkedList<>();
        Matcher absolutMatcher = ABSOLUT_LINK.matcher(str);
        while (absolutMatcher.find()) {
            foundLinks.add(absolutMatcher.group().split("\"")[1]);
        }
        Matcher relatedMatcher = RELATED_LINK.matcher(str);
        while (relatedMatcher.find()) {
            foundLinks.add(relatedMatcher.group().split("\"")[1]);
        }
        

        // LinkedList<String> result = new LinkedList<>();
        // InputStream in = url.openStream(); // Ask the URL object to create an
        // input stream
        // InputStreamReader isr = new InputStreamReader(in); // Convert the
        // stream to a reader
        // SpiderParserCallback cb = new SpiderParserCallback(treenode); //
        // Create a callback object
        // ParserDelegator pd = new ParserDelegator(); // Create the delegator
        // pd.parse(isr,cb,true); // Parse the stream
        //
        //
        // isr.close(); // Close the stream

        return foundLinks;
    }

    public static void main(String args[]) {
        
        String s1 = "<link rel=\"stylesheet\" href=\"/w/load.php?debug=false&amp;lang=ru&amp;modules=site.styles&amp;only=styles&amp;skin=vector\"/>";
        String s2 = "<link rel=\"canonical\" href=\"https://ru.wikipedia.org/wiki/%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0\"/>";
        LinkParser linkParser = new LinkParser();
        LOGGER.info(linkParser.findAndSave(s2));
        LOGGER.info(linkParser.findAndSave(s1));
        
        
        
//        <noscript><link rel="stylesheet" href="/w/load.php?debug=false&amp;lang=ru&amp;modules=noscript&amp;only=styles&amp;skin=vector"/></noscript>
//        <meta name="generator" content="MediaWiki 1.29.0-wmf.21"/>
//        <meta name="referrer" content="origin-when-cross-origin"/>
//        <meta property="og:image" content="https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/Robert_Edward_Lee.jpg/1200px-Robert_Edward_Lee.jpg"/>

        
    }

    public static class SpiderParserCallback extends HTMLEditorKit.ParserCallback {

        public SpiderParserCallback() {

        }

        @Override
        public void handleText(char[] data, int pos) {

        }

    }

}
