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

public class TagSetTest_customTextBoundaryTagsAffectTextExtraction {

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
    void customTextBoundaryTagsAffectTextExtraction() {
        TagSet tags = TagSet.Html();
        tags.valueOf("custom-widget", NamespaceHtml).set(Tag.TextBoundary);
        Parser parser = Parser.htmlParser().tagSet(tags);
        Document doc = Jsoup.parse("<p>One<custom-widget>Two</custom-widget>Three</p>", parser);
        assertEquals("One Two Three", doc.text());
        assertEquals("OneTwoThree", doc.wholeText());
    }
}
