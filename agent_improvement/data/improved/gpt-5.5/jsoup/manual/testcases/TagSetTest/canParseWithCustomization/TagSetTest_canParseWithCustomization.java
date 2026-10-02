package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagSetTest_canParseWithCustomization {
    private static final String SELF_CLOSING_SCRIPT_FOLLOWED_BY_TEXT = "<script />Text";
    private static final String EXPECTED_HTML_AFTER_SCRIPT_TAG_CUSTOMIZATION =
        "<html>\n" +
        " <head>\n" +
        "  <script></script>\n" +
        " </head>\n" +
        " <body>Text</body>\n" +
        "</html>";

    @Test
    void canParseWithCustomization() {
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (tag.normalName().equals("script"))
                tag.set(Tag.SelfClose);
        });

        Document document = Jsoup.parse(SELF_CLOSING_SCRIPT_FOLLOWED_BY_TEXT, parser);

        assertEquals(EXPECTED_HTML_AFTER_SCRIPT_TAG_CUSTOMIZATION, document.html());
    }
}
