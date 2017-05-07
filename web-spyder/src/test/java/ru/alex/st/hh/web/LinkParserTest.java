package ru.alex.st.hh.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.testng.annotations.Test;

import junit.framework.Assert;
import ru.alex.st.hh.web.LinkParser;

public class LinkParserTest {


    @Test
    public void testLinkParser() throws MalformedURLException {
        LinkParser parser = new LinkParser(new URL("https://ru.wikipedia.org/wiki"));

        String s = "<p><b><a href=\"/wiki/%D0%98%D0%BC%D1%8F_%D1%81%D1%83%D1%89%D0%B5%D1%81%D1%82%D0%B2%D0%B8%D1%82%D0%B5%D0%BB%D1%8C%D0%BD%D0%BE%D0%B5_%D0%B2_%D0%BC%D0%B0%D0%BA%D0%B5%D0%B4%D0%BE%D0%BD%D1%81%D0%BA%D0%BE%D0%BC_%D1%8F%D0%B7%D1%8B%D0%BA%D0%B5\" title=\"Имя существительное в македонском языке\">Имя существительное в македонском языке</a></b> "
                        + "(<a href=\"/wiki/%D0%9C%D0%B0%D0%BA%D0%B5%D0%B4%D0%BE%D0%BD%D1%81%D0%BA%D0%B8%D0%B9_%D1%8F%D0%B7%D1%8B%D0%BA\" title=\"Македонский язык\">макед.</a> "
                        + "<span lang=\"mk\" style=\"font-style: italic\" xml:lang=\"mk\">именка во македонскиот јазик</span>) — "
                        + "<a href=\"/wiki/%D0%97%D0%BD%D0%B0%D0%BC%D0%B5%D0%BD%D0%B0%D1%82%D0%B5%D0%BB%D1%8C%D0%BD%D1%8B%D0%B5_%D1%81%D0%BB%D0%BE%D0%B2%D0%B0\" title=\"Знаменательные слова\">знаменательная</a> "
                        + "<a href=\"/wiki/%D0%A7%D0%B0%D1%81%D1%82%D1%8C_%D1%80%D0%B5%D1%87%D0%B8\" title=\"Часть речи\">часть речи</a> "
                        + "<a href=\"/wiki/%D0%9C%D0%B0%D0%BA%D0%B5%D0%B4%D0%BE%D0%BD%D1%81%D0%BA%D0%B8%D0%B9_%D1%8F%D0%B7%D1%8B%D0%BA\" title=\"Македонский язык\">македонского языка</a>, называющая предметы и одушев­лён­ные существа. Характеризуется "
                        + "<a href=\"/wiki/%D0%93%D1%80%D0%B0%D0%BC%D0%BC%D0%B0%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B0%D1%8F_%D0%BA%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F\" title=\"Грамматическая категория\">грамматическими категориями</a> "
                        + "<a href=\"/wiki/%D0%A0%D0%BE%D0%B4_(%D0%BB%D0%B8%D0%BD%D0%B3%D0%B2%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B0)\" title=\"Род (лингвистика)\">рода</a>, "
                        + "<a href=\"/wiki/%D0%93%D1%80%D0%B0%D0%BC%D0%BC%D0%B0%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%BE%D0%B5_%D1%87%D0%B8%D1%81%D0%BB%D0%BE\" title=\"Грамматическое число\">числа</a> и "
                        + "<span class=\"iw plainlinks\" data-title=\"Категория определённости — неопределённости\" data-lang=\"en\" data-lang-name=\"англ.\">"
                        + "<a href=\"/w/index.php?title=%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F_%D0%BE%D0%BF%D1%80%D0%B5%D0%B4%D0%B5%D0%BB%D1%91%D0%BD%D0%BD%D0%BE%D1%81%D1%82%D0%B8_%E2%80%94_%D0%BD%D0%B5%D0%BE%D0%BF%D1%80%D0%B5%D0%B4%D0%B5%D0%BB%D1%91%D0%BD%D0%BD%D0%BE%D1%81%D1%82%D0%B8&amp;action=edit&amp;redlink=1\" class=\"new\" title=\"Категория определённости — неопределённости (страница отсутствует)\">определённости — неопре­де­лённости</a>"
                        + "<sup class=\"iw__note noprint\" style=\"font-style:normal; font-weight:normal;\">"
                        + "<a href=\"https://en.wikipedia.org/wiki/Definiteness\" class=\"extiw\" title=\"en:Definiteness\">"
                        + "<span class=\"iw__tooltip\" title=\"Definiteness — версия статьи «Категория определённости — неопределённости» на английском языке\">[en]</span></a></sup></span>. Среди македонских "
                        + "<a href=\"/wiki/%D0%98%D0%BC%D1%8F_%D1%81%D1%83%D1%89%D0%B5%D1%81%D1%82%D0%B2%D0%B8%D1%82%D0%B5%D0%BB%D1%8C%D0%BD%D0%BE%D0%B5\" title=\"Имя существительное\">существительных</a> выделяются "
                        + "<a href=\"/wiki/%D0%98%D0%BC%D1%8F_%D1%81%D0%BE%D0%B1%D1%81%D1%82%D0%B2%D0%B5%D0%BD%D0%BD%D0%BE%D0%B5\" title=\"Имя собственное\">имена собственные</a>, "
                        + "<a href=\"/wiki/%D0%98%D0%BC%D1%8F_%D0%BD%D0%B0%D1%80%D0%B8%D1%86%D0%B0%D1%82%D0%B5%D0%BB%D1%8C%D0%BD%D0%BE%D0%B5\" title=\"Имя нарицательное\">нарицательные</a>, собирательные, вещественные и отвлечённые. Последние три группы образуют класс неисчисляемых существительных. Внутри класса "
                        + "<a href=\"/wiki/%D0%9C%D1%83%D0%B6%D1%81%D0%BA%D0%BE%D0%B9_%D1%80%D0%BE%D0%B4\" title=\"Мужской род\">мужского рода</a> выделяется подкласс имён — названий лиц мужского пола, противопоставленный всем прочим существительным. Помимо общей "
                        + "<a href=\"/wiki/%D0%93%D1%80%D0%B0%D0%BC%D0%BC%D0%B0%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B0%D1%8F_%D1%84%D0%BE%D1%80%D0%BC%D0%B0\" title=\"Грамматическая форма\">формы</a> "
                        + "<a href=\"/wiki/%D0%9C%D0%BD%D0%BE%D0%B6%D0%B5%D1%81%D1%82%D0%B2%D0%B5%D0%BD%D0%BD%D0%BE%D0%B5_%D1%87%D0%B8%D1%81%D0%BB%D0%BE\" title=\"Множественное число\">множественного числа</a> грамматически релевантны формы счётного и собирательного множественного числа.</p>"
                        + "<a href=\"/wiki/qweqwe.jpg\" title=\"Множественное число\">множественного числа</a> грамматически релевантны формы счётного и собирательного множественного числа.</p>";
        
        Set<String> linkList = parser.findLinks(s);
        for (String link : linkList) {
            System.out.println(link);
        }
        System.out.println("size = " + linkList.size());
        Assert.assertEquals(15, linkList.size());
    }

}
