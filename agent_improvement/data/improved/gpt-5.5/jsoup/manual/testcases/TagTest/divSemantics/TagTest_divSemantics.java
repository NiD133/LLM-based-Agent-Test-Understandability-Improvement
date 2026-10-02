package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagTest_divSemantics {
    private static final String SCRIPT_TAG = "script";
    private static final String UPPERCASE_SCRIPT_TAG = "SCRIPT";
    private static final String DIV_TAG = "div";

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        Tag script = htmlTag(SCRIPT_TAG);
        Tag uppercaseScript = htmlTag(UPPERCASE_SCRIPT_TAG);
        assertEquals(script, uppercaseScript);

        TagSet htmlTags = TagSet.Html();
        Tag scriptFromSet = htmlTags.valueOf(SCRIPT_TAG, NamespaceHtml, ParseSettings.htmlDefault);
        Tag uppercaseScriptFromSet = htmlTags.valueOf(UPPERCASE_SCRIPT_TAG, NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptFromSet, uppercaseScriptFromSet);
    }

    @Test
    public void divSemantics() {
        Tag div = Tag.valueOf(DIV_TAG);

        assertTrue(div.isBlock());
        assertFalse(div.isInline());
        assertTrue(div.isKnownTag());
    }

    private static Tag htmlTag(String tagName) {
        return Tag.valueOf(tagName, NamespaceHtml, ParseSettings.htmlDefault);
    }
}
