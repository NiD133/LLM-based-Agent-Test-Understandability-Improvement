package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagSetTest_customTextBoundaryTagsAffectTextExtraction {
    private static final String CUSTOM_TEXT_BOUNDARY_TAG = "custom-widget";
    private static final String HTML_WITH_CUSTOM_BOUNDARY_TAG =
        "<p>One<custom-widget>Two</custom-widget>Three</p>";

    @Test
    void customTextBoundaryTagsAffectTextExtraction() {
        TagSet tags = TagSet.Html();
        tags.valueOf(CUSTOM_TEXT_BOUNDARY_TAG, NamespaceHtml).set(Tag.TextBoundary);

        Parser parser = Parser.htmlParser().tagSet(tags);
        Document doc = Jsoup.parse(HTML_WITH_CUSTOM_BOUNDARY_TAG, parser);

        assertEquals("One Two Three", doc.text());
        assertEquals("OneTwoThree", doc.wholeText());
    }
}
