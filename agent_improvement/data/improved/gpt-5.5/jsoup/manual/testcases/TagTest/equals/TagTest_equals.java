package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TagTest_equals {

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        TagSet htmlTags = TagSet.Html();
        Tag lowerCaseScriptFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScriptFromSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(lowerCaseScriptFromSet, upperCaseScriptFromSet);
    }

    @Test
    void equals() {
        TagSet htmlTags = TagSet.Html();
        Tag paragraphTag = htmlTags.get("p", NamespaceHtml);
        Tag comparisonTag = paragraphTag.clone();

        assertEquals(paragraphTag, comparisonTag);
        assertNotEquals(paragraphTag, htmlTags);

        comparisonTag.namespace = "Other";
        assertNotEquals(paragraphTag, comparisonTag);
        comparisonTag.namespace = paragraphTag.namespace;

        comparisonTag.tagName = "P";
        assertNotEquals(paragraphTag, comparisonTag);
        comparisonTag.tagName = paragraphTag.tagName;

        comparisonTag.normalName = "pp";
        assertNotEquals(paragraphTag, comparisonTag);
        comparisonTag.normalName = paragraphTag.normalName;

        comparisonTag.options = 0;
        assertNotEquals(paragraphTag, comparisonTag);
        comparisonTag.options = paragraphTag.options;
    }
}
