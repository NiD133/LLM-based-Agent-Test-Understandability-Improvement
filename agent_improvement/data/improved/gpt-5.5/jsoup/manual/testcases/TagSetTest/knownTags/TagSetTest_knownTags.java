package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagSetTest_knownTags {

    @Test
    void knownTags() {
        TagSet tags = TagSet.Html();

        Tag custom = new Tag("custom");
        assertNewCustomTagStartsUnknown(custom);

        Tag br = tags.get("br", NamespaceHtml);
        assertHtmlDefaultTagIsKnown(tags, br);

        Tag foo = tags.valueOf("foo", NamespaceHtml);
        assertFalse(foo.isKnownTag());

        tags.add(custom);
        assertExplicitlyAddedTagIsKnown(tags, custom);

        Tag capCustom = tags.valueOf("Custom", NamespaceHtml);
        assertTrue(capCustom.isKnownTag());

        Tag c1 = new Tag("bar");
        assertKnownFlagFollowsDirectOptionMutations(c1);
    }

    private static void assertNewCustomTagStartsUnknown(Tag custom) {
        assertEquals("custom", custom.name());
        assertEquals(NamespaceHtml, custom.namespace());
        assertFalse(custom.isKnownTag());
    }

    private static void assertHtmlDefaultTagIsKnown(TagSet tags, Tag br) {
        assertNotNull(br);
        assertTrue(br.isKnownTag());
        assertSame(br, tags.valueOf("br", NamespaceHtml));
    }

    private static void assertExplicitlyAddedTagIsKnown(TagSet tags, Tag custom) {
        assertTrue(custom.isKnownTag());
        assertSame(custom, tags.get("custom", NamespaceHtml));
        assertSame(custom, tags.valueOf("custom", NamespaceHtml));
    }

    private static void assertKnownFlagFollowsDirectOptionMutations(Tag tag) {
        assertFalse(tag.isKnownTag());
        tag.set(Tag.Block);
        assertTrue(tag.isKnownTag());
        tag.clear(Tag.Block);
        assertTrue(tag.isKnownTag());
        tag.clear(Tag.Known);
        assertFalse(tag.isKnownTag());
    }
}
