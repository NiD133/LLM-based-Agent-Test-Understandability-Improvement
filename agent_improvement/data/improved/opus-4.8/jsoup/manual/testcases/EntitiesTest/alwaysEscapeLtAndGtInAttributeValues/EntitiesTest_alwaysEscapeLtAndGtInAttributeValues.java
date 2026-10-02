package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Entities.EscapeMode.base;
import static org.jsoup.nodes.Entities.EscapeMode.xhtml;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_alwaysEscapeLtAndGtInAttributeValues {

    /**
     * The {@code <} and {@code >} characters inside an attribute value must always be escaped as
     * {@code &lt;} and {@code &gt;}, regardless of the active escape mode.
     *
     * @see <a href="https://github.com/jhy/jsoup/issues/2337">jsoup issue #2337</a>
     */
    @Test
    public void alwaysEscapeLtAndGtInAttributeValues() {
        // An anchor whose title attribute contains raw markup ("<p>One</p>").
        String inputHtml = "<a title='<p>One</p>'>One</a>";
        // The expected output: the angle brackets in the title attribute are escaped.
        String expectedHtml = "<a title=\"&lt;p&gt;One&lt;/p&gt;\">One</a>";

        Document doc = Jsoup.parse(inputHtml);
        Element anchor = doc.select("a").first();

        // The base (default HTML) escape mode must escape the angle brackets.
        doc.outputSettings().escapeMode(base);
        assertEquals(expectedHtml, anchor.outerHtml());

        // The xhtml escape mode must escape the angle brackets in the same way.
        doc.outputSettings().escapeMode(xhtml);
        assertEquals(expectedHtml, anchor.outerHtml());
    }
}
