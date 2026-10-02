package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagSetTest_canParseWithCustomization {

    @Test
    void canParseWithCustomization() {
        // Register a customizer that marks every <script> tag as self-closing as it is
        // discovered during the parse. (A real customizer would typically use tag.valueOf("script");
        // this inline lambda keeps the example self-contained.)
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (tag.normalName().equals("script"))
                tag.set(Tag.SelfClose);
        });

        // Parse a self-closed <script /> followed by text using the customized parser.
        Document doc = Jsoup.parse("<script />Text", parser);

        // The self-closing flag still yields valid, well-formed HTML: the script element is
        // emitted as an empty element and the trailing text lands in the body.
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
