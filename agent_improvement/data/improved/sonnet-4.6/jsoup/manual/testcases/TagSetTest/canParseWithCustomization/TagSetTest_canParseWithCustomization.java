package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TagSetTest_canParseWithCustomization {

    @Test
    void canParseWithCustomization() {
        // Register a customizer that makes <script> self-closing when it is encountered during parsing
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (tag.normalName().equals("script"))
                tag.set(Tag.SelfClose);
        });

        // Self-closing <script /> is still serialized as valid HTML (open + close tags)
        Document doc = Jsoup.parse("<script />Text", parser);
        assertEquals(
            "<html>\n <head>\n  <script></script>\n </head>\n <body>Text</body>\n</html>",
            doc.html()
        );
    }
}
