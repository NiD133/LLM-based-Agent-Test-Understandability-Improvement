package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagSetTest_supplyCustomTagSet {

    @Test
    void supplyCustomTagSet() {
        // Configure a custom tag that preserves whitespace and is treated as a block element
        TagSet tags = TagSet.Html();
        tags.valueOf("custom", NamespaceHtml).set(Tag.PreserveWhitespace).set(Tag.Block);
        Parser parser = Parser.htmlParser().tagSet(tags);

        Document doc = Jsoup.parse("<body><custom>\n\nFoo\n Bar</custom></body>", parser);
        Element custom = doc.expectFirst("custom");

        assertTrue(custom.tag().preserveWhitespace());
        assertTrue(custom.tag().isBlock());
        assertEquals("<custom>\n\nFoo\n Bar</custom>", custom.outerHtml());
    }
}
