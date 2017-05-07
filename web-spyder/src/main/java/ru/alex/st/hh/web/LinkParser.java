package ru.alex.st.hh.web;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkParser {

    private static final Pattern RELATIVE_LINK = Pattern.compile("href=\"/[w].+?\"");
    private static final Pattern ABSOLUT_LINK_WITH_PROTOCOL = Pattern.compile("href=\"(http|https)://.*?(wiki)+.+?\"");
    private static final Pattern ABSOLUT_LINK_WITHOUT_PROTOCOL = Pattern.compile("href=\"//.*?(wiki)+.+?\"");

    // private static final Pattern RELATIVE_LINK =
    // Pattern.compile("href=\"/[w^/](iki)?.+?\""); //()
    // private static final Pattern ABSOLUT_LINK_WITH_PROTOCOL =
    // Pattern.compile("href=\"(http|https)://.+?(wiki)+.+?\"");
    // private static final Pattern ABSOLUT_LINK_WITHOUT_PROTOCOL =
    // Pattern.compile("href=\"//[w](iki)?.+?\"");

    private static LinkedList<String> list = new LinkedList<>();

    static {
        list.add("jpg");
        list.add("bmp");
        list.add("svg");
        list.add("png");
        list.add("exe");
    }

    private Set<String> linkSet = new LinkedHashSet<String>();

    private URL parent;

    public LinkParser(URL parent) {
        this.parent = parent;
    }

    public Set<String> findLinks(String str) {
        Matcher absolutMatcher = ABSOLUT_LINK_WITH_PROTOCOL.matcher(str);
        while (absolutMatcher.find()) {
            String link = getLinkFromHrefMatcher(absolutMatcher);
            if (endIsOk(link)) {
                linkSet.add(link);
            }
        }
        Matcher relatedMatcher = RELATIVE_LINK.matcher(str);
        while (relatedMatcher.find()) {
            String link = getLinkFromHrefMatcher(relatedMatcher);
            if (endIsOk(link)) {
                linkSet.add(String.format("%s://%s%s", parent.getProtocol(), parent.getHost(), link));
            }
        }
        Matcher absolutNoProtocolMatcher = ABSOLUT_LINK_WITHOUT_PROTOCOL.matcher(str);
        while (absolutNoProtocolMatcher.find()) {
            String link = getLinkFromHrefMatcher(absolutNoProtocolMatcher);
            if (endIsOk(link)) {
                linkSet.add(String.format("%s:%s", parent.getProtocol(), link));
            }
        }
        return linkSet;
    }

    private boolean endIsOk(String s) {
        String end = s.substring(s.length() - 3, s.length());
        for (String odd : list) {
            if (odd.equals(end)) {
                return false;
            }
        }
        return true;
    }

    private static String getLinkFromHrefMatcher(Matcher matcher) {
        return matcher.group().split("\"")[1];
    }

    public Set<String> getLinks() {
        return linkSet;
    }

}
