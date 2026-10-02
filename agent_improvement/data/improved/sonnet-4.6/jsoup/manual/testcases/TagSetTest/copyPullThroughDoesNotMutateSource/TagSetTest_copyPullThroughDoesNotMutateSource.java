package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that a copied TagSet lazily pulls tags from its source without mutating the source's internal state.
 *
 * <p>A TagSet copy holds a reference to its source for "pull-through": when a tag is requested that
 * the copy hasn't yet loaded, it clones the tag from the source and stores the clone locally.
 * The source must remain unchanged throughout this process.</p>
 */
public class TagSetTest_copyPullThroughDoesNotMutateSource {

    /**
     * Returns the number of namespaces currently materialized in the TagSet's internal {@code tags} map.
     * A namespace entry appears only when at least one tag under that namespace has been loaded into the map.
     */
    private static int materializedNamespaceCount(TagSet tagSet) {
        try {
            Field tagsField = TagSet.class.getDeclaredField("tags");
            tagsField.setAccessible(true);
            Map<?, ?> tags = (Map<?, ?>) tagsField.get(tagSet);
            return tags.size();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void copyPullThroughDoesNotMutateSource() {
        // Arrange: create a source TagSet and a shallow copy that will pull tags from it on demand
        TagSet source = TagSet.Html();
        TagSet copy = new TagSet(source);
        int sourceNamespaceCountBefore = materializedNamespaceCount(source);

        // Act: trigger a pull-through by requesting a tag that the copy hasn't loaded yet;
        // the copy should clone the tag from the source and cache it locally
        assertNotNull(copy.get("div", NamespaceHtml),
            "copy.get() should find 'div' via pull-through from the source");

        // Assert: the source's internal namespace map must be identical to what it was before the pull-through
        int sourceNamespaceCountAfter = materializedNamespaceCount(source);
        assertEquals(sourceNamespaceCountBefore, sourceNamespaceCountAfter,
            "Pull-through on the copy must not add namespace entries to the source TagSet");
    }
}
