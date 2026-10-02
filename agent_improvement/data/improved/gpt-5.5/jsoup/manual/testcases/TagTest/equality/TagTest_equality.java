package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TagTest_equality {

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
    public void equality() {
        Tag firstStaticLookup = Tag.valueOf("p");
        Tag secondStaticLookup = Tag.valueOf("p");

        assertEquals(firstStaticLookup, secondStaticLookup);
        // Tag.valueOf uses a fresh clone of the HTML tag set, so equivalent tags are not the same instance.
        assertNotSame(firstStaticLookup, secondStaticLookup);

        TagSet firstHtmlTagSet = TagSet.Html();
        TagSet secondHtmlTagSet = TagSet.Html();
        assertEquals(firstHtmlTagSet, secondHtmlTagSet);
        assertNotSame(firstHtmlTagSet, secondHtmlTagSet);

        Tag firstTagSetFirstLookup = firstHtmlTagSet.valueOf("p", NamespaceHtml);
        Tag firstTagSetSecondLookup = firstHtmlTagSet.valueOf("p", NamespaceHtml);
        Tag secondTagSetFirstLookup = secondHtmlTagSet.valueOf("p", NamespaceHtml);
        Tag secondTagSetSecondLookup = secondHtmlTagSet.valueOf("p", NamespaceHtml);

        assertEquals(firstStaticLookup, firstTagSetFirstLookup);
        assertEquals(firstTagSetFirstLookup, firstTagSetSecondLookup);
        assertEquals(firstTagSetSecondLookup, secondTagSetFirstLookup);
        assertSame(firstTagSetFirstLookup, firstTagSetSecondLookup);
        assertSame(secondTagSetFirstLookup, secondTagSetSecondLookup);
        assertNotSame(firstTagSetFirstLookup, secondTagSetFirstLookup);
    }
}
