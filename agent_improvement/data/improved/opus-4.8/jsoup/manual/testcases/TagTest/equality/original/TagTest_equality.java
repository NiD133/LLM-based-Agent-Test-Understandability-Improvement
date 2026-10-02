package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.Parser.NamespaceSvg;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_equality {

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);
        Tag script1 = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script2 = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(script1, script2);
        TagSet htmlTags = TagSet.Html();
        Tag script3 = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script4 = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(script3, script4);
    }

    @Test
    public void equality() {
        Tag p1 = Tag.valueOf("p");
        Tag p2 = Tag.valueOf("p");
        assertEquals(p1, p2);
        // not same because Tag.valueOf creates new clone of the TagSet.Html, so changes don't clobber all
        assertNotSame(p1, p2);
        TagSet html1 = TagSet.Html();
        TagSet html2 = TagSet.Html();
        assertEquals(html1, html2);
        assertNotSame(html1, html2);
        Tag p3 = html1.valueOf("p", NamespaceHtml);
        Tag p4 = html1.valueOf("p", NamespaceHtml);
        Tag p5 = html2.valueOf("p", NamespaceHtml);
        Tag p6 = html2.valueOf("p", NamespaceHtml);
        assertEquals(p1, p3);
        assertEquals(p3, p4);
        assertEquals(p4, p5);
        assertSame(p3, p4);
        assertSame(p5, p6);
        assertNotSame(p3, p5);
    }
}
