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
 * Tests for how {@link Tag} resolves and configures tag options.
 */
public class TagTest_canSetOptions {

    /**
     * Tag lookup ignores case, regardless of the JVM's default locale.
     * <p>
     * {@link Tag#valueOf} compares two tags as {@code equals} when their names differ only by case,
     * while {@link TagSet#valueOf} returns the very same cached instance for both spellings.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Tag.valueOf: lower- and upper-case spellings resolve to equal tags.
        Tag scriptLowerCase = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpperCase = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLowerCase, scriptUpperCase);

        // TagSet.valueOf: lower- and upper-case spellings resolve to the identical cached instance.
        TagSet htmlTags = TagSet.Html();
        Tag cachedScriptLowerCase = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag cachedScriptUpperCase = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(cachedScriptLowerCase, cachedScriptUpperCase);
    }

    /**
     * Setting an option on a freshly created tag also marks the tag as "known".
     */
    @Test
    void canSetOptions() {
        // A brand-new tag has no options: it is neither known nor a void (empty) tag.
        Tag tag = new Tag("foo", NamespaceHtml);
        assertFalse(tag.isKnownTag());
        assertFalse(tag.isEmpty());

        // Marking it as a Void tag turns on the empty flag and implicitly makes it a known tag.
        tag.set(Tag.Void);
        assertTrue(tag.isEmpty());
        assertTrue(tag.isKnownTag());
    }
}
