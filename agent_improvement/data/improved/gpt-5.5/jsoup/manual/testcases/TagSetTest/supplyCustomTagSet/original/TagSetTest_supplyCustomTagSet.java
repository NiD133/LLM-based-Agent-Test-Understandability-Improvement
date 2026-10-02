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

public class TagSetTest_supplyCustomTagSet {

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
    void supplyCustomTagSet() {
        TagSet tags = TagSet.Html();
        tags.valueOf("custom", NamespaceHtml).set(Tag.PreserveWhitespace).set(Tag.Block);
        Parser parser = Parser.htmlParser().tagSet(tags);
        Document doc = Jsoup.parse("<body><custom>\n\nFoo\n Bar</custom></body>", parser);
        Element custom = doc.expectFirst("custom");
        assertTrue(custom.tag().preserveWhitespace());
        assertTrue(custom.tag().isBlock());
        assertEquals("<custom>\n" + "\n" + "Foo\n" + " Bar" + "</custom>", custom.outerHtml());
    }
}
