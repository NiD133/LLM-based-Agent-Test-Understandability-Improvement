package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagSetTest_customTextBoundaryTagsAffectTextExtraction {

    /**
     * Verifies that registering a custom tag as TextBoundary causes doc.text() to insert
     * whitespace around it (like a known widget/control element), while doc.wholeText()
     * still returns the raw concatenated text without extra spaces.
     */
    @Test
    void customTextBoundaryTagsAffectTextExtraction() {
        TagSet tags = TagSet.Html();
        tags.valueOf("custom-widget", NamespaceHtml).set(Tag.TextBoundary);

        Parser parser = Parser.htmlParser().tagSet(tags);
        Document doc = Jsoup.parse("<p>One<custom-widget>Two</custom-widget>Three</p>", parser);

        // TextBoundary causes word-break spacing in normalized text output
        assertEquals("One Two Three", doc.text());
        // wholeText preserves the raw content with no injected spaces
        assertEquals("OneTwoThree", doc.wholeText());
    }
}
