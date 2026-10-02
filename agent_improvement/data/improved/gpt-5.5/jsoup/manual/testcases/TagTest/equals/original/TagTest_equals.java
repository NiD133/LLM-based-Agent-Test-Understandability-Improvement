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

public class TagTest_equals {

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
    void equals() {
        TagSet tags = TagSet.Html();
        Tag p1 = tags.get("p", NamespaceHtml);
        Tag p2 = p1.clone();
        assertEquals(p1, p2);
        assertNotEquals(p1, tags);
        p2.namespace = "Other";
        assertNotEquals(p1, p2);
        p2.namespace = p1.namespace;
        p2.tagName = "P";
        assertNotEquals(p1, p2);
        p2.tagName = p1.tagName;
        p2.normalName = "pp";
        assertNotEquals(p1, p2);
        p2.normalName = p1.normalName;
        p2.options = 0;
        assertNotEquals(p1, p2);
        p2.options = p1.options;
    }
}
