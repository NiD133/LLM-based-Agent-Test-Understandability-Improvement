package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TagSetTest_canParseWithCustomization {

    @Test
    void canParseWithCustomization() {
        // Register a customizer that makes <script> self-closing during parsing
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (tag.normalName().equals("script"))
                tag.set(Tag.SelfClose);
        });

        // Parse HTML with a self-closing <script /> — the customizer should apply
        Document doc = Jsoup.parse("<script />Text", parser);

        // Self-closing <script> still produces valid HTML output (empty tag, no self-close slash)
        assertEquals(
            "<html>\n <head>\n  <script></script>\n </head>\n <body>Text</body>\n</html>",
            doc.html()
        );
    }
}
