package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that {@link TagSet#onNewTag} customizers can modify tag behaviour during parsing.
 */
public class TagSetTest_canParseWithCustomization {

    @Test
    void canParseWithCustomization() {
        // Register a customizer that makes <script> self-closing when it is first encountered
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (tag.normalName().equals("script"))
                tag.set(Tag.SelfClose);
        });

        // The self-closing slash is accepted, and the output is still valid HTML
        Document doc = Jsoup.parse("<script />Text", parser);
        assertEquals(
            "<html>\n <head>\n  <script></script>\n </head>\n <body>Text</body>\n</html>",
            doc.html()
        );
    }
}
