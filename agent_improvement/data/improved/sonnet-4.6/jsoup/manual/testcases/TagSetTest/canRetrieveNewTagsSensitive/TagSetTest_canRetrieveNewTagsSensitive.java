package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagSetTest_canRetrieveNewTagsSensitive {

    @Test
    void canRetrieveNewTagsSensitive() {
        // Parse with case-preserving settings so that custom tag names like "FOO" are kept as-is
        Document doc = Jsoup.parse("<div><p>One</p></div>", "", Parser.htmlParser().settings(ParseSettings.preserveCase));
        TagSet tags = doc.parser().tagSet();

        // Known HTML tags (e.g. "meta") should be present and flagged as known
        Tag meta = tags.get("meta", NamespaceHtml);
        assertNotNull(meta);
        assertTrue(meta.isKnownTag());

        // Elements parsed from HTML should also carry a known tag
        Element p = doc.expectFirst("p");
        assertTrue(p.tag().isKnownTag());

        // An unrecognised tag name returns null before it has been introduced
        assertNull(tags.get("FOO", NamespaceHtml));

        // Renaming the element introduces "FOO" as a new, unknown tag in the TagSet
        p.tagName("FOO");
        Tag foo = p.tag();

        // The new tag preserves the user-supplied case, stores the lowercase normalName, and lives in the HTML namespace
        assertEquals("FOO", foo.name());
        assertEquals("foo", foo.normalName());
        assertEquals(NamespaceHtml, foo.namespace());

        // Because "FOO" was not declared upfront it is not a known tag
        assertFalse(foo.isKnownTag());

        // Both get() and valueOf() must return the exact same Tag instance that was created
        assertSame(foo, tags.get("FOO", NamespaceHtml));
        assertSame(foo, tags.valueOf("FOO", NamespaceHtml));

        // The tag is only registered under its own namespace; other namespaces return null
        assertNull(tags.get("FOO", "SomeOtherNamespace"));
    }
}
