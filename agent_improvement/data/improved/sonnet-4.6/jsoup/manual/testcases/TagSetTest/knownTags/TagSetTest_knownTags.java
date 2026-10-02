package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.Map;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagSetTest_knownTags {

    private static int tagSetNamespaceCount(TagSet tagSet) {
        try {
            Field tagsField = TagSet.class.getDeclaredField("tags");
            tagsField.setAccessible(true);
            Map<?, ?> tags = (Map<?, ?>) tagsField.get(tagSet);
            return tags.size();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Verifies the "known tag" contract: a Tag is considered known only when it has been
     * explicitly registered with a TagSet (via add()), retrieved from one (via get()), or
     * had its flags mutated (via set()/clear()). Tags that arise implicitly through
     * valueOf() for names not already in the set are NOT known.
     */
    @Test
    void knownTags() {
        TagSet tagSet = TagSet.Html();

        // A Tag constructed directly is not known until it is registered with a TagSet.
        Tag custom = new Tag("custom");
        assertEquals("custom", custom.name());
        assertEquals(NamespaceHtml, custom.namespace());
        assertFalse(custom.isKnownTag(), "a freshly constructed Tag should not be known");

        // Tags that are part of the built-in HTML set are known when retrieved via get().
        Tag br = tagSet.get("br", NamespaceHtml);
        assertNotNull(br);
        assertTrue(br.isKnownTag(), "a built-in HTML tag should be known");
        // valueOf() returns the same instance for a tag that is already in the set.
        assertSame(br, tagSet.valueOf("br", NamespaceHtml));

        // valueOf() for an unknown name creates a new Tag, but it is NOT marked known.
        Tag foo = tagSet.valueOf("foo", NamespaceHtml);
        assertFalse(foo.isKnownTag(), "a tag created implicitly by valueOf() should not be known");

        // Explicitly adding a tag to the TagSet marks it known and makes it retrievable.
        tagSet.add(custom);
        assertTrue(custom.isKnownTag(), "tag should become known after add()");
        assertSame(custom, tagSet.get("custom", NamespaceHtml));
        assertSame(custom, tagSet.valueOf("custom", NamespaceHtml));

        // A case-variant looked up via valueOf() is cloned from the known tag, so it is also known.
        Tag capCustom = tagSet.valueOf("Custom", NamespaceHtml);
        assertTrue(capCustom.isKnownTag(), "a clone of a known tag should remain known");

        // Calling set() or clear() on a standalone Tag marks it known as a side-effect.
        Tag bar = new Tag("bar");
        assertFalse(bar.isKnownTag(), "a freshly constructed Tag should not be known");
        bar.set(Tag.Block);
        assertTrue(bar.isKnownTag(), "calling set() should implicitly mark the tag as known");
        bar.clear(Tag.Block);
        assertTrue(bar.isKnownTag(), "clearing a non-Known flag should leave the tag known");
        // Explicitly clearing the Known flag removes the known status.
        bar.clear(Tag.Known);
        assertFalse(bar.isKnownTag(), "explicitly clearing Tag.Known should make the tag unknown");
    }
}
