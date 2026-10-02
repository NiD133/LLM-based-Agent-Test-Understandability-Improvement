package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagSetTest_supplyCustomTagSet {
    private static final String CUSTOM_TAG = "custom";
    private static final String SOURCE_HTML = "<body><custom>\n\nFoo\n Bar</custom></body>";
    private static final String EXPECTED_CUSTOM_HTML = "<custom>\n\nFoo\n Bar</custom>";

    @Test
    void supplyCustomTagSet() {
        TagSet tags = TagSet.Html();
        tags.valueOf(CUSTOM_TAG, NamespaceHtml)
            .set(Tag.PreserveWhitespace)
            .set(Tag.Block);

        Parser parser = Parser.htmlParser().tagSet(tags);
        Document doc = Jsoup.parse(SOURCE_HTML, parser);
        Element custom = doc.expectFirst(CUSTOM_TAG);

        assertTrue(custom.tag().preserveWhitespace());
        assertTrue(custom.tag().isBlock());
        assertEquals(EXPECTED_CUSTOM_HTML, custom.outerHtml());
    }
}
