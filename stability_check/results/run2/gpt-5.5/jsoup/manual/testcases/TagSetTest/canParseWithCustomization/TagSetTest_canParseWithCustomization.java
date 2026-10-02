package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagSetTest_canParseWithCustomization {

    @Test
    void canParseWithCustomization() {
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (tag.normalName().equals("script")) {
                tag.set(Tag.SelfClose);
            }
        });

        Document doc = Jsoup.parse("<script />Text", parser);

        String expectedHtml = "<html>\n" +
            " <head>\n" +
            "  <script></script>\n" +
            " </head>\n" +
            " <body>Text</body>\n" +
            "</html>";
        assertEquals(expectedHtml, doc.html());
    }
}
