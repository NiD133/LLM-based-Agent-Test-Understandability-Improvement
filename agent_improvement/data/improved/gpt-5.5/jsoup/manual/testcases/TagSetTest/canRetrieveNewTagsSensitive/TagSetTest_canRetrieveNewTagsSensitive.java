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

public class TagSetTest_canRetrieveNewTagsSensitive {
    private static final String HTML = "<div><p>One</p></div>";
    private static final String BASE_URI = "";
    private static final String EXISTING_TAG = "meta";
    private static final String RENAMED_TAG = "FOO";
    private static final String RENAMED_NORMAL_NAME = "foo";
    private static final String OTHER_NAMESPACE = "SomeOtherNamespace";

    @Test
    void canRetrieveNewTagsSensitive() {
        Document doc = Jsoup.parse(HTML, BASE_URI, Parser.htmlParser().settings(ParseSettings.preserveCase));
        TagSet tags = doc.parser().tagSet();

        Tag meta = tags.get(EXISTING_TAG, NamespaceHtml);
        assertNotNull(meta);
        assertTrue(meta.isKnownTag());

        Element p = doc.expectFirst("p");
        assertTrue(p.tag().isKnownTag());
        assertNull(tags.get(RENAMED_TAG, NamespaceHtml));

        p.tagName(RENAMED_TAG);
        Tag foo = p.tag();

        assertEquals(RENAMED_TAG, foo.name());
        assertEquals(RENAMED_NORMAL_NAME, foo.normalName());
        assertEquals(NamespaceHtml, foo.namespace());
        assertFalse(foo.isKnownTag());
        assertSame(foo, tags.get(RENAMED_TAG, NamespaceHtml));
        assertSame(foo, tags.valueOf(RENAMED_TAG, NamespaceHtml));
        assertNull(tags.get(RENAMED_TAG, OTHER_NAMESPACE));
    }
}
