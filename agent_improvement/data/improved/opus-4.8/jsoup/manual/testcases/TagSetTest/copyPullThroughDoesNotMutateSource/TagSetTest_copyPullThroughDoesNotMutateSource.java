package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TagSetTest_copyPullThroughDoesNotMutateSource {

    /**
     * Reads the private {@code tags} map of a TagSet via reflection and returns how many
     * namespaces it currently holds. The map is keyed by namespace, so its size equals the
     * number of namespaces that have had at least one tag added to them.
     */
    private static int namespaceCount(TagSet tagSet) {
        try {
            Field tagsField = TagSet.class.getDeclaredField("tags");
            tagsField.setAccessible(true);
            Map<?, ?> tagsByNamespace = (Map<?, ?>) tagsField.get(tagSet);
            return tagsByNamespace.size();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A copy made via {@code new TagSet(source)} resolves unknown tags lazily by pulling them
     * through from its own root source. Looking up a tag on the copy must therefore not add any
     * entries back into the original source TagSet.
     */
    @Test
    void copyPullThroughDoesNotMutateSource() {
        TagSet source = TagSet.Html();
        TagSet copy = new TagSet(source);

        int sourceNamespacesBeforeLookup = namespaceCount(source);

        // Trigger a lazy pull-through lookup on the copy, not on the source.
        assertNotNull(copy.get("div", NamespaceHtml));

        int sourceNamespacesAfterLookup = namespaceCount(source);
        assertEquals(sourceNamespacesBeforeLookup, sourceNamespacesAfterLookup,
            "looking up a tag on the copy must not mutate the source TagSet");
    }
}
