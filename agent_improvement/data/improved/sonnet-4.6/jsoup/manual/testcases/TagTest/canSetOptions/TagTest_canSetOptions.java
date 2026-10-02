package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_canSetOptions {

    /**
     * Tags looked up by name should resolve to the same instance regardless of case,
     * because HTML tag names are case-insensitive.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Static valueOf normalises case via ParseSettings, so lowercase and uppercase resolve to equal tags
        Tag scriptLower = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpper = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLower, scriptUpper);

        // TagSet.valueOf returns the canonical instance, so both lookups must be the exact same object
        TagSet htmlTags = TagSet.Html();
        Tag scriptFromTagSetLower = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptFromTagSetUpper = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptFromTagSetLower, scriptFromTagSetUpper);
    }

    /**
     * Calling set() on a tag should apply the requested option and also implicitly
     * mark the tag as known, because any explicit configuration counts as a definition.
     */
    @Test
    void canSetOptions() {
        // A freshly constructed tag is unknown and not void before any options are applied
        Tag fooTag = new Tag("foo", NamespaceHtml);
        assertFalse(fooTag.isKnownTag());
        assertFalse(fooTag.isEmpty());

        // Setting Tag.Void makes the tag a void (empty) element AND automatically marks it as known
        fooTag.set(Tag.Void);
        assertTrue(fooTag.isEmpty());
        assertTrue(fooTag.isKnownTag());
    }
}
