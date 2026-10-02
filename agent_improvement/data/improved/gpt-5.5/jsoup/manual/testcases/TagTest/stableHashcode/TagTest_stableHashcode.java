package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.Parser.NamespaceSvg;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagTest_stableHashcode {

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        TagSet htmlTags = TagSet.Html();
        Tag cachedLowerCaseScript = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag cachedUpperCaseScript = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(cachedLowerCaseScript, cachedUpperCaseScript);
    }

    @Test
    void stableHashcode() {
        HashSet<Tag> tags = new HashSet<>();

        Tag htmlLowerCaseImg = Tag.valueOf("img");
        Tag htmlUpperCaseImg = Tag.valueOf("IMG");
        Tag svgImg = Tag.valueOf("img", NamespaceSvg, ParseSettings.htmlDefault);

        assertEquals(-2074969810, htmlLowerCaseImg.hashCode());
        assertEquals(-2075954866, htmlUpperCaseImg.hashCode());
        assertEquals(-292873947, svgImg.hashCode());

        tags.add(htmlLowerCaseImg);
        tags.add(htmlUpperCaseImg);
        tags.add(svgImg);

        svgImg.set(Tag.Block);
        assertEquals(-292873947, svgImg.hashCode());

        assertTrue(tags.contains(htmlLowerCaseImg));
        assertTrue(tags.contains(htmlUpperCaseImg));
        assertTrue(tags.contains(svgImg));
    }
}
