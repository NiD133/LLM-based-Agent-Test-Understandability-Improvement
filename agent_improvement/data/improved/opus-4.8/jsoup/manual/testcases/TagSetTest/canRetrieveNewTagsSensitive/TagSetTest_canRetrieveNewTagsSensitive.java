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

/**
 * Verifies how a parsed document's {@link TagSet} behaves when tag names are case-sensitive
 * (the parser is configured with {@code preserveCase}):
 * <ul>
 *     <li>standard HTML tags are present and reported as "known",</li>
 *     <li>renaming an element to a brand-new, case-sensitive name registers that tag in the set, and</li>
 *     <li>that newly registered tag is unknown, keeps its exact case, and is namespace-scoped.</li>
 * </ul>
 */
public class TagSetTest_canRetrieveNewTagsSensitive {

    @Test
    void canRetrieveNewTagsSensitive() {
        // Parse with case preserved, so tag names are looked up exactly as written.
        Document doc = Jsoup.parse(
            "<div><p>One</p></div>", "",
            Parser.htmlParser().settings(ParseSettings.preserveCase));
        TagSet tags = doc.parser().tagSet();

        // The set starts as the full default HTML tag set: a standard tag like "meta" is present and known.
        Tag meta = tags.get("meta", NamespaceHtml);
        assertNotNull(meta);
        assertTrue(meta.isKnownTag());

        // The parsed <p> element also resolves to a known HTML tag.
        Element p = doc.expectFirst("p");
        assertTrue(p.tag().isKnownTag());

        // "FOO" is not a standard tag, so it is absent from the set before we introduce it.
        assertNull(tags.get("FOO", NamespaceHtml));

        // Rename the element to the new, case-sensitive name "FOO".
        p.tagName("FOO");
        Tag foo = p.tag();

        // The new tag keeps its exact case as its name, lower-cases to its normal name,
        // lives in the HTML namespace, and is reported as unknown (not a standard tag).
        assertEquals("FOO", foo.name());
        assertEquals("foo", foo.normalName());
        assertEquals(NamespaceHtml, foo.namespace());
        assertFalse(foo.isKnownTag());

        // Renaming registered "FOO" in the set, so get() and valueOf() now return that very same instance.
        assertSame(foo, tags.get("FOO", NamespaceHtml));
        assertSame(foo, tags.valueOf("FOO", NamespaceHtml));

        // The tag is scoped to the HTML namespace, so it is not found under a different namespace.
        assertNull(tags.get("FOO", "SomeOtherNamespace"));
    }
}
