package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagSetTest_customizersArePreservedInSource {

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

    @Test
    void customizersArePreservedInSource() {
        TagSet source = TagSet.Html();
        source.onNewTag(tag -> tag.set(Tag.RcData));
        TagSet copy = new TagSet(source);
        assertTrue(copy.valueOf("script", NamespaceHtml).is(Tag.RcData));
        assertTrue(source.valueOf("script", NamespaceHtml).is(Tag.RcData));
        copy.onNewTag(tag -> tag.set(Tag.Void));
        assertTrue(copy.valueOf("custom-tag", NamespaceHtml).is(Tag.Void));
        assertFalse(source.valueOf("custom-tag", NamespaceHtml).is(Tag.Void));
    }
}
