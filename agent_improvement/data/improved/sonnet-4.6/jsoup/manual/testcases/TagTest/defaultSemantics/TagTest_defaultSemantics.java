package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_defaultSemantics {

    /**
     * Verifies that tag lookup is case-insensitive: "script" and "SCRIPT" resolve to
     * equivalent (and, when retrieved from a shared TagSet, the same) Tag instance.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Static valueOf normalises the name, so both inputs produce equal Tags.
        Tag script1 = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script2 = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(script1, script2);

        // TagSet caches known tags, so the same canonical instance is returned.
        TagSet htmlTags = TagSet.Html();
        Tag script3 = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script4 = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(script3, script4);
    }

    /**
     * Verifies the default semantics applied to a custom (unknown) tag.
     * Unknown tags are treated as generic inline elements with no special behaviour.
     */
    @Test
    public void defaultSemantics() {
        // Two lookups of the same unknown tag name should be value-equal.
        Tag foo = Tag.valueOf("FOO");
        Tag fooAgain = Tag.valueOf("FOO");
        assertEquals(foo, fooAgain);

        // An unknown tag is not registered in the HTML tag set.
        assertFalse(foo.isKnownTag());

        // Unknown tags default to inline (not block) layout.
        assertTrue(foo.isInline());
        assertFalse(foo.isBlock());

        // Unknown tags do not carry the InlineContainer pretty-print hint.
        assertFalse(foo.is(Tag.InlineContainer));

        // Unknown tags do not preserve whitespace (no <pre>-like behaviour).
        assertFalse(foo.preserveWhitespace());
    }
}
