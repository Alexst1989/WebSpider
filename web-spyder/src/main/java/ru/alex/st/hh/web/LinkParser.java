package ru.alex.st.hh.web;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkParser {
    
    private static final Pattern RELATIVE_LINK = Pattern.compile("href=\"/.+?\"");
    private static final Pattern ABSOLUT_LINK_WITH_PROTOCOL = Pattern.compile("href=\"(http|https)://.+?\"");
    private static final Pattern ABSOLUT_LINK_WITHOUT_PROTOCOL = Pattern.compile("href=\"//.+?\"");
    
    
    private Set<String> linkSet = new LinkedHashSet<String>();
    
    private URL parent;
    
    public LinkParser(URL parent) {
        this.parent = parent;
    }

    public Set<String> findLinks(String str) {
        Matcher absolutMatcher = ABSOLUT_LINK_WITH_PROTOCOL.matcher(str);
        while (absolutMatcher.find()) {
            linkSet.add(getLinkFromHrefMatcher(absolutMatcher));
        }
        Matcher relatedMatcher = RELATIVE_LINK.matcher(str);
        while (relatedMatcher.find()) {
            String link = getLinkFromHrefMatcher(relatedMatcher);
            linkSet.add(String.format("%s://%s%s", parent.getProtocol(), parent.getHost(), link));
        }
        Matcher absolutNoProtocolMatcher = ABSOLUT_LINK_WITHOUT_PROTOCOL.matcher(str);
        while (absolutNoProtocolMatcher.find()) {
            String link = getLinkFromHrefMatcher(absolutNoProtocolMatcher);
            linkSet.add(String.format("%s:", parent.getProtocol(), link));
        }
        return linkSet;
    }
    
    private static String getLinkFromHrefMatcher(Matcher matcher) {
        return matcher.group().split("\"")[1];
    }
    
    public Set<String> getLinks() {
        return linkSet;
    }

}
