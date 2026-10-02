package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link Tag#valueOf(String)} and related lookups, focusing on how tag
 * names are resolved (case handling) and validated (rejecting blank names).
 */
public class TagTest_valueOfChecksNotEmpty {

    /**
     * Tag name lookup is case-insensitive: resolving the same tag in different
     * letter cases yields equivalent tags, regardless of the active locale.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // The static lookup treats "script" and "SCRIPT" as the same tag (by equality).
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        // Looking up via a shared TagSet returns the very same cached Tag instance.
        TagSet htmlTags = TagSet.Html();
        Tag cachedLowerCaseScript = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag cachedUpperCaseScript = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(cachedLowerCaseScript, cachedUpperCaseScript);
    }

    /**
     * A blank tag name (here a single space) is invalid and must be rejected.
     */
    @Test
    public void valueOfChecksNotEmpty() {
        assertThrows(IllegalArgumentException.class, () -> Tag.valueOf(" "));
    }
}
