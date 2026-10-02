package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagSetTest_supplyCustomTagSet {

    /**
     * A custom tag registered on the parser's TagSet should keep the configured behaviour
     * (here: block layout and whitespace preservation) during parsing and serialization.
     */
    @Test
    void supplyCustomTagSet() {
        // Start from the default HTML tags and define a "custom" tag that is a block
        // element and preserves whitespace.
        TagSet tags = TagSet.Html();
        tags.valueOf("custom", NamespaceHtml)
            .set(Tag.PreserveWhitespace)
            .set(Tag.Block);

        // Parse a document using a parser configured with the custom tag set.
        Parser parser = Parser.htmlParser().tagSet(tags);
        String html = "<body><custom>\n\nFoo\n Bar</custom></body>";
        Document doc = Jsoup.parse(html, parser);

        // The parsed <custom> element should reflect the configured behaviour.
        Element custom = doc.expectFirst("custom");
        assertTrue(custom.tag().preserveWhitespace(), "custom tag should preserve whitespace");
        assertTrue(custom.tag().isBlock(), "custom tag should be a block element");

        // Because whitespace is preserved, the original inner text is serialized verbatim.
        String expectedHtml = "<custom>\n\nFoo\n Bar</custom>";
        assertEquals(expectedHtml, custom.outerHtml());
    }
}
