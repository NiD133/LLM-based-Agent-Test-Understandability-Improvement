package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests that {@code <} and {@code >} inside HTML attribute values are always escaped as
 * {@code &lt;} and {@code &gt;}, regardless of the document's escape mode.
 *
 * <p>Regression for <a href="https://github.com/jhy/jsoup/issues/2337">jsoup #2337</a>:
 * raw angle brackets in attribute values produced invalid HTML output.
 */
public class EntitiesTest_alwaysEscapeLtAndGtInAttributeValues {

    // HTML attribute value that contains raw '<' and '>' characters.
    // After parsing, jsoup must always re-serialize them as &lt; / &gt;
    // so the output remains valid HTML.
    private static final String INPUT_WITH_ANGLE_BRACKETS_IN_ATTR =
            "<a title='<p>One</p>'>One</a>";

    // The correctly escaped form: attribute value uses &lt;/&gt; and the
    // element is wrapped in double quotes (jsoup's standard output style).
    private static final String EXPECTED_ESCAPED_OUTPUT =
            "<a title=\"&lt;p&gt;One&lt;/p&gt;\">One</a>";

    @Test
    public void alwaysEscapeLtAndGtInAttributeValues() {
        Document doc = Jsoup.parse(INPUT_WITH_ANGLE_BRACKETS_IN_ATTR);
        Element anchorElement = doc.select("a").first();
        OutputSettings outputSettings = doc.outputSettings();

        // Verify escaping with 'base' escape mode (standard HTML entities).
        outputSettings.escapeMode(base);
        assertEquals(EXPECTED_ESCAPED_OUTPUT, anchorElement.outerHtml(),
                "Angle brackets in attribute values must be escaped in 'base' mode");

        // Verify escaping with 'xhtml' escape mode (restricted entity set).
        outputSettings.escapeMode(xhtml);
        assertEquals(EXPECTED_ESCAPED_OUTPUT, anchorElement.outerHtml(),
                "Angle brackets in attribute values must be escaped in 'xhtml' mode");
    }
}
