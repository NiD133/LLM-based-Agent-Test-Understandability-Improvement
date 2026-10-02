package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagSetTest_canRetrieveNewTagsInsensitive {

    @Test
    void canRetrieveNewTagsInsensitive() {
        Document doc = Jsoup.parse("<div><p>One</p></div>");
        TagSet tags = doc.parser().tagSet();

        // The parser's tag set starts as the full HTML default set, so a standard tag
        // like "meta" is already present and flagged as a known tag.
        Tag meta = tags.get("meta", NamespaceHtml);
        assertNotNull(meta);
        assertTrue(meta.isKnownTag());

        // The parsed <p> element resolves to a known HTML tag too.
        Element p = doc.expectFirst("p");
        assertTrue(p.tag().isKnownTag());

        // "FOO" is not a known tag, so it is absent from the set before we introduce it.
        assertNull(tags.get("FOO", NamespaceHtml));

        // Renaming the element to "FOO" creates a new, unknown tag in the set.
        p.tagName("FOO");
        Tag foo = p.tag();
        assertEquals("foo", foo.name());          // name is normalized to lower case
        assertEquals("foo", foo.normalName());
        assertEquals(NamespaceHtml, foo.namespace());
        assertFalse(foo.isKnownTag());            // dynamically created tags are not "known"

        // The new tag is now retrievable case-insensitively: both the lower-case lookup
        // and the original "FOO" (via valueOf) return the very same Tag instance...
        assertSame(foo, tags.get("foo", NamespaceHtml));
        assertSame(foo, tags.valueOf("FOO", NamespaceHtml, doc.parser().settings()));

        // ...but only within its own (HTML) namespace.
        assertNull(tags.get("foo", "SomeOtherNamespace"));
    }
}
