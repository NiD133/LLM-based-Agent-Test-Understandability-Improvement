package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagSetTest_customTextBoundaryTagsAffectTextExtraction {

    /**
     * A tag flagged as {@link Tag#TextBoundary} should act as a word boundary when extracting text. Here we mark a
     * custom element as a text boundary and confirm it changes how its surrounding text is joined:
     * <ul>
     *   <li>{@code text()} inserts a space at the boundary ("One Two Three").</li>
     *   <li>{@code wholeText()} ignores the boundary and concatenates verbatim ("OneTwoThree").</li>
     * </ul>
     */
    @Test
    void customTextBoundaryTagsAffectTextExtraction() {
        // Register a custom element "custom-widget" as a text boundary in the HTML tag set.
        TagSet tags = TagSet.Html();
        tags.valueOf("custom-widget", NamespaceHtml).set(Tag.TextBoundary);

        // Parse markup that wraps "Two" inside the custom-widget element.
        Parser parser = Parser.htmlParser().tagSet(tags);
        Document doc = Jsoup.parse("<p>One<custom-widget>Two</custom-widget>Three</p>", parser);

        // text() treats the boundary as a separator; wholeText() preserves the raw concatenation.
        assertEquals("One Two Three", doc.text());
        assertEquals("OneTwoThree", doc.wholeText());
    }
}
