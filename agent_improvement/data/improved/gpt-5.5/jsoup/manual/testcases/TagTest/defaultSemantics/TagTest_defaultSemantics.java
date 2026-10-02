package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagTest_defaultSemantics {
    private static final String SCRIPT_TAG = "script";
    private static final String SCRIPT_TAG_UPPERCASE = "SCRIPT";
    private static final String UNKNOWN_TAG = "FOO";

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        Tag defaultScriptTag = Tag.valueOf(SCRIPT_TAG, NamespaceHtml, ParseSettings.htmlDefault);
        Tag defaultUppercaseScriptTag = Tag.valueOf(SCRIPT_TAG_UPPERCASE, NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(defaultScriptTag, defaultUppercaseScriptTag);

        TagSet htmlTags = TagSet.Html();
        Tag scriptTagFromSet = htmlTags.valueOf(SCRIPT_TAG, NamespaceHtml, ParseSettings.htmlDefault);
        Tag uppercaseScriptTagFromSet = htmlTags.valueOf(SCRIPT_TAG_UPPERCASE, NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptTagFromSet, uppercaseScriptTagFromSet);
    }

    @Test
    public void defaultSemantics() {
        Tag unknownTag = Tag.valueOf(UNKNOWN_TAG);
        Tag sameUnknownTag = Tag.valueOf(UNKNOWN_TAG);

        assertEquals(unknownTag, sameUnknownTag);
        assertHasDefaultUnknownTagSemantics(unknownTag);
    }

    private static void assertHasDefaultUnknownTagSemantics(Tag tag) {
        assertFalse(tag.isKnownTag());
        assertTrue(tag.isInline());
        assertFalse(tag.isBlock());
        assertFalse(tag.is(Tag.InlineContainer));
        assertFalse(tag.preserveWhitespace());
    }
}
