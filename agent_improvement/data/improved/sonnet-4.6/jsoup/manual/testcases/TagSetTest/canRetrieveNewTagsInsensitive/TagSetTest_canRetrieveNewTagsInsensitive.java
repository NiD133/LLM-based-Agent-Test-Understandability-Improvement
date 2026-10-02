package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagSetTest_canRetrieveNewTagsInsensitive {

    @Test
    void canRetrieveNewTagsInsensitive() {
        Document doc = Jsoup.parse("<div><p>One</p></div>");
        TagSet tags = doc.parser().tagSet();

        // Known tags (defined in the HTML spec) are retrievable by exact name
        Tag meta = tags.get("meta", NamespaceHtml);
        assertNotNull(meta);
        assertTrue(meta.isKnownTag());

        // Tags created during parsing are also marked as known
        Element p = doc.expectFirst("p");
        assertTrue(p.tag().isKnownTag());

        // An unknown tag name returns null before any element uses it
        assertNull(tags.get("FOO", NamespaceHtml));

        // Renaming an element to an unknown tag name dynamically registers that tag,
        // normalized to lowercase regardless of how it was supplied
        p.tagName("FOO");
        Tag foo = p.tag();
        assertEquals("foo", foo.name());
        assertEquals("foo", foo.normalName());
        assertEquals(NamespaceHtml, foo.namespace());
        assertFalse(foo.isKnownTag());

        // The dynamically registered tag is now retrievable case-insensitively
        assertSame(foo, tags.get("foo", NamespaceHtml));
        assertSame(foo, tags.valueOf("FOO", NamespaceHtml, doc.parser().settings()));

        // The same tag name in a different namespace is a distinct (absent) entry
        assertNull(tags.get("foo", "SomeOtherNamespace"));
    }
}
