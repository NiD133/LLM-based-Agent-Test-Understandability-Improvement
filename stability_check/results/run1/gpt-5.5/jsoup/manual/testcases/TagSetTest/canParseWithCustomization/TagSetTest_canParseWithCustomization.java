package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagSetTest_canParseWithCustomization {
    private static final String SELF_CLOSING_SCRIPT_HTML = "<script />Text";
    private static final String EXPECTED_HTML = "<html>\n"
        + " <head>\n"
        + "  <script></script>\n"
        + " </head>\n"
        + " <body>Text</body>\n"
        + "</html>";

    @Test
    void canParseWithCustomization() {
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (tag.normalName().equals("script")) {
                tag.set(Tag.SelfClose);
            }
        });

        Document doc = Jsoup.parse(SELF_CLOSING_SCRIPT_HTML, parser);

        assertEquals(EXPECTED_HTML, doc.html());
    }
}
