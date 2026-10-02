package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagSetTest_canParseWithCustomization {

    @Test
    void canParseWithCustomization() {
        // Configure the parser so that any <script> tag is treated as self-closing.
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (tag.normalName().equals("script"))
                tag.set(Tag.SelfClose);
        });

        // Parse HTML that uses the self-closing script syntax with trailing text.
        Document doc = Jsoup.parse("<script />Text", parser);

        // The self-closing customization still yields well-formed HTML:
        // the script is emitted as an empty element and the text moves into the body.
        String expectedHtml =
            "<html>\n" +
            " <head>\n" +
            "  <script></script>\n" +
            " </head>\n" +
            " <body>Text</body>\n" +
            "</html>";
        assertEquals(expectedHtml, doc.html());
    }
}
