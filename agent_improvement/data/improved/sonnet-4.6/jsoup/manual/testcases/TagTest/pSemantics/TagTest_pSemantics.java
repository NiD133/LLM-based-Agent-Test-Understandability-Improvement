package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_pSemantics {

    /**
     * Tag lookup must be case-insensitive in HTML: "script" and "SCRIPT" should resolve to the same tag.
     * When looked up via a TagSet the two calls must return the exact same instance (assertSame),
     * whereas the static Tag.valueOf path only guarantees equality (assertEquals).
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Static valueOf normalises the tag name, so both inputs produce equal Tags.
        Tag scriptLower = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpper = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLower, scriptUpper);

        // TagSet caches instances, so the same normalised name must yield the identical object.
        TagSet htmlTags = TagSet.Html();
        Tag scriptLowerFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpperFromSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptLowerFromSet, scriptUpperFromSet);
    }

    @Test
    @DisplayName("<p> is a known block-level element, not inline")
    public void pSemantics() {
        Tag p = Tag.valueOf("p");

        assertTrue(p.isKnownTag(), "<p> should be a pre-defined HTML tag");
        assertTrue(p.isBlock(),    "<p> should be a block-level element");
        assertFalse(p.isInline(),  "<p> should not be treated as inline");
    }
}
