package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link Tag} name resolution: case-insensitive tag lookup and the
 * "known tag" check.
 */
public class TagTest_knownTags {

    /**
     * Resolving a tag by name should ignore case, regardless of the active locale.
     * <p>
     * When resolved through the shared HTML tag set, the lower- and upper-case
     * spellings must return the very same cached {@link Tag} instance; when
     * resolved via the static {@link Tag#valueOf} helper they must at least be
     * equal.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Static valueOf resolves "script" and "SCRIPT" to equal tags.
        Tag scriptLowerCase = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpperCase = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLowerCase, scriptUpperCase);

        // Resolving through the shared HTML tag set returns the same cached instance.
        TagSet htmlTags = TagSet.Html();
        Tag sharedScriptLowerCase = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag sharedScriptUpperCase = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(sharedScriptLowerCase, sharedScriptUpperCase);
    }

    /**
     * {@link Tag#isKnownTag(String)} recognises predefined HTML tags such as
     * "div", but not arbitrary made-up names such as "explain".
     */
    @Test
    public void knownTags() {
        assertTrue(Tag.isKnownTag("div"));
        assertFalse(Tag.isKnownTag("explain"));
    }
}
