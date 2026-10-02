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

public class TagTest_updateNameAndNamespace {

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
    void updateNameAndNamespace() {
        Tag tag = new Tag("foo", NamespaceHtml);
        tag.name("bar").namespace(NamespaceSvg);
        tag.set(Tag.Block);
        assertEquals("bar", tag.name());
        assertEquals(NamespaceSvg, tag.namespace());
        // properties are unchanged
        assertTrue(tag.isBlock());
        // test in a doc
        Document doc = Jsoup.parse("<foo>One</foo><foo>Two</foo>");
        Tag foo = doc.expectFirst("foo").tag();
        foo.name("BAR");
        // is case-sensitive
        assertEquals("<BAR>One</BAR><BAR>Two</BAR>", doc.body().html());
    }
}
