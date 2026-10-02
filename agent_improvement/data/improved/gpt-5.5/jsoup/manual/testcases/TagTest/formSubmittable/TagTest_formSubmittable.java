package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagTest_formSubmittable {

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        Tag lowercaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag uppercaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowercaseScript, uppercaseScript);

        TagSet htmlTags = TagSet.Html();
        Tag cachedLowercaseScript = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag cachedUppercaseScript = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(cachedLowercaseScript, cachedUppercaseScript);
    }

    @Test
    void formSubmittable() {
        // https://github.com/jhy/jsoup/issues/2323
        Tag imageTag = Tag.valueOf("img");
        Tag inputTag = Tag.valueOf("input");
        int imageOptionsBeforeCheck = imageTag.options;
        int inputOptionsBeforeCheck = inputTag.options;

        assertFalse(imageTag.isFormSubmittable());
        assertTrue(inputTag.isFormSubmittable());

        assertEquals(imageOptionsBeforeCheck, imageTag.options);
        assertEquals(inputOptionsBeforeCheck, inputTag.options);
    }
}
