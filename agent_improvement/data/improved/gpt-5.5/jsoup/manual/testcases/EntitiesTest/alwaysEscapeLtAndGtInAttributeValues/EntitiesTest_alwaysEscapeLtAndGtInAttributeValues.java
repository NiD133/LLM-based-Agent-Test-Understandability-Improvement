package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Entities.EscapeMode.base;
import static org.jsoup.nodes.Entities.EscapeMode.xhtml;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_alwaysEscapeLtAndGtInAttributeValues {
    private static final String HTML_WITH_TAG_LIKE_ATTRIBUTE_VALUE = "<a title='<p>One</p>'>One</a>";
    private static final String HTML_WITH_ESCAPED_ATTRIBUTE_VALUE = "<a title=\"&lt;p&gt;One&lt;/p&gt;\">One</a>";
    private static final String ANCHOR_SELECTOR = "a";

    @Test
    public void alwaysEscapeLtAndGtInAttributeValues() {
        // https://github.com/jhy/jsoup/issues/2337
        Document doc = Jsoup.parse(HTML_WITH_TAG_LIKE_ATTRIBUTE_VALUE);
        Element element = doc.select(ANCHOR_SELECTOR).first();

        assertLtAndGtAreEscapedInAttributeValue(doc, element, base);
        assertLtAndGtAreEscapedInAttributeValue(doc, element, xhtml);
    }

    private static void assertLtAndGtAreEscapedInAttributeValue(
        Document doc,
        Element element,
        Entities.EscapeMode escapeMode
    ) {
        doc.outputSettings().escapeMode(escapeMode);
        assertEquals(HTML_WITH_ESCAPED_ATTRIBUTE_VALUE, element.outerHtml());
    }
}
