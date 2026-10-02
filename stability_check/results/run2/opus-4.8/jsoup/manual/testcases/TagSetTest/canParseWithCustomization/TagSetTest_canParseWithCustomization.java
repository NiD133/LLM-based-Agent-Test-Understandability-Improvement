package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that a caller can customize how tags are parsed by registering an
 * {@link TagSet#onNewTag(java.util.function.Consumer)} callback on the parser's
 * {@link TagSet}. Here, every newly encountered {@code <script>} tag is flagged
 * as self-closing, and we confirm the parser still emits well-formed HTML.
 */
public class TagSetTest_canParseWithCustomization {

    @Test
    void canParseWithCustomization() {
        // Register a customizer that marks <script> tags as self-closing as they
        // are discovered during the parse. (A real use would prefer Tag.valueOf("script");
        // this inline callback is just illustrative for the test.)
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (tag.normalName().equals("script"))
                tag.set(Tag.SelfClose);
        });

        // Parse a self-closed <script /> followed by text using the customized parser.
        Document doc = Jsoup.parse("<script />Text", parser);

        // The self-closing script tag is honored, and the result is still valid HTML.
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
