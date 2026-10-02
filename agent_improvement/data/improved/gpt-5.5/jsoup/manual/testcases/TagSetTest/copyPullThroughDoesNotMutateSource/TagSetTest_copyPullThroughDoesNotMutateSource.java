package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TagSetTest_copyPullThroughDoesNotMutateSource {

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

    @Test
    void copyPullThroughDoesNotMutateSource() {
        TagSet source = TagSet.Html();
        TagSet copy = new TagSet(source);

        int namespaceCountBeforePullThrough = namespaceCount(source);

        Tag copiedDivTag = copy.get("div", NamespaceHtml);
        assertNotNull(copiedDivTag);

        int namespaceCountAfterPullThrough = namespaceCount(source);
        assertEquals(namespaceCountBeforePullThrough, namespaceCountAfterPullThrough);
    }
}
