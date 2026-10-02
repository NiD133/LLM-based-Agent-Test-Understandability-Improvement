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

public class TagSetTest_canParseWithCustomization {

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
    void canParseWithCustomization() {
        // really would use tag.valueOf("script"); just a test example here
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (tag.normalName().equals("script"))
                tag.set(Tag.SelfClose);
        });
        Document doc = Jsoup.parse("<script />Text", parser);
        assertEquals("<html>\n <head>\n  <script></script>\n </head>\n <body>Text</body>\n</html>", doc.html());
        // self closing bit still produces valid HTML
    }
}
