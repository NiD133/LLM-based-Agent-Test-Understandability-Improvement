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
        TagSet tagSet = doc.parser().tagSet();

        Tag knownMetaTag = tagSet.get("meta", NamespaceHtml);
        assertNotNull(knownMetaTag);
        assertTrue(knownMetaTag.isKnownTag());

        Element paragraph = doc.expectFirst("p");
        assertTrue(paragraph.tag().isKnownTag());
        assertNull(tagSet.get("FOO", NamespaceHtml));

        paragraph.tagName("FOO");
        Tag unknownFooTag = paragraph.tag();

        assertEquals("foo", unknownFooTag.name());
        assertEquals("foo", unknownFooTag.normalName());
        assertEquals(NamespaceHtml, unknownFooTag.namespace());
        assertFalse(unknownFooTag.isKnownTag());
        assertSame(unknownFooTag, tagSet.get("foo", NamespaceHtml));
        assertSame(unknownFooTag, tagSet.valueOf("FOO", NamespaceHtml, doc.parser().settings()));
        assertNull(tagSet.get("foo", "SomeOtherNamespace"));
    }
}
