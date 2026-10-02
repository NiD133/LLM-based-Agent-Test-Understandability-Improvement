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
 * Tests how {@link Tag} resolves tag names and what default semantics an
 * unknown (not pre-defined) tag is given.
 */
public class TagTest_defaultSemantics {

    /**
     * Resolving a tag name is case-insensitive when the default HTML parse
     * settings are used, regardless of the active {@link Locale}.
     *
     * <p>Two lookups for "script" and "SCRIPT" should yield equal tags. When
     * the lookups go through the same {@link TagSet}, the pre-defined "script"
     * tag is cached, so the results are not just equal but the very same
     * instance.</p>
     */
    @MultiLocaleTest
    public void resolvesTagNameCaseInsensitively(Locale locale) {
        Locale.setDefault(locale);

        // Looking up via Tag.valueOf: lower- and upper-case names resolve equal.
        Tag scriptLowerCase = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpperCase = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLowerCase, scriptUpperCase);

        // Looking up via the same TagSet returns the identical cached instance.
        TagSet htmlTags = TagSet.Html();
        Tag scriptFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptFromSetUpperCase = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptFromSet, scriptFromSetUpperCase);
    }

    /**
     * An unknown tag (one not pre-defined in the HTML TagSet) is created as a
     * generic tag: equal to another lookup of the same name, but flagged as
     * "not known" and given inline, non-block, non-whitespace-preserving
     * defaults.
     */
    @Test
    public void unknownTagGetsGenericInlineDefaults() {
        // "FOO" is not a defined HTML tag, so each lookup yields a generic tag.
        Tag foo = Tag.valueOf("FOO");
        Tag fooAgain = Tag.valueOf("FOO");
        assertEquals(foo, fooAgain);

        // Default semantics for an unknown tag:
        assertFalse(foo.isKnownTag());                 // not pre-defined
        assertTrue(foo.isInline());                    // inline by default
        assertFalse(foo.isBlock());                    // and therefore not block
        assertFalse(foo.is(Tag.InlineContainer));      // no inline-container hint
        assertFalse(foo.preserveWhitespace());         // does not preserve whitespace
    }
}
