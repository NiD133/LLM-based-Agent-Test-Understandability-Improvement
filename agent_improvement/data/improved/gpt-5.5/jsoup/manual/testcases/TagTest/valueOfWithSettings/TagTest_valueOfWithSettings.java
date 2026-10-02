package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TagTest_valueOfWithSettings {

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        Tag lowercaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag uppercaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowercaseScript, uppercaseScript);

        TagSet htmlTags = TagSet.Html();
        Tag lowercaseScriptFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag uppercaseScriptFromSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(lowercaseScriptFromSet, uppercaseScriptFromSet);
    }

    @Test
    void valueOfWithSettings() {
        Tag defaultLowercaseImg = Tag.valueOf("img", ParseSettings.htmlDefault);
        Tag defaultUppercaseImg = Tag.valueOf("IMG", ParseSettings.htmlDefault);
        Tag casePreservedUppercaseImg = Tag.valueOf("IMG", ParseSettings.preserveCase);

        // Tag.valueOf uses a fresh HTML TagSet for each lookup.
        assertNotSame(defaultLowercaseImg, defaultUppercaseImg);
        assertNotSame(defaultLowercaseImg, casePreservedUppercaseImg);
        assertEquals("IMG", casePreservedUppercaseImg.toString());
        assertEquals("img", defaultLowercaseImg.toString());

        TagSet tagSet = TagSet.Html();
        Tag defaultLowercaseImgFromSet = tagSet.valueOf("img", NamespaceHtml, ParseSettings.htmlDefault);
        Tag defaultUppercaseImgFromSet = tagSet.valueOf("IMG", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(defaultLowercaseImgFromSet, defaultUppercaseImgFromSet);

        Tag caseSensitiveLowercaseImgFromSet = tagSet.valueOf("img", NamespaceHtml);
        Tag caseSensitiveUppercaseImgFromSet = tagSet.valueOf("IMG", NamespaceHtml);
        assertNotSame(caseSensitiveLowercaseImgFromSet, caseSensitiveUppercaseImgFromSet);
    }
}
