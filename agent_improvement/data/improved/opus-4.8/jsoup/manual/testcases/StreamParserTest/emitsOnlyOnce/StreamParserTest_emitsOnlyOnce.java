package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_emitsOnlyOnce {

    /**
     * Appends a compact, human-readable summary of {@code el} to {@code summary}, so the order and
     * identity of the elements emitted by the stream can be asserted as a single string.
     * <p>The format per element is: {@code tagName[#id][text][+];}, where the optional parts are:</p>
     * <ul>
     *     <li>{@code #id} - present when the element has an {@code id} attribute</li>
     *     <li>{@code [text]} - present when the element has its own (non-child) text</li>
     *     <li>{@code +} - present when the element has a following sibling element</li>
     * </ul>
     */
    static void appendSummary(Element el, StringBuilder summary) {
        summary.append(el.tagName());
        if (el.hasAttr("id"))
            summary.append("#").append(el.id());
        if (!el.ownText().isEmpty())
            summary.append("[").append(el.ownText()).append("]");
        if (el.nextElementSibling() != null)
            summary.append("+");
        summary.append(";");
    }

    /**
     * Reproduces https://github.com/jhy/jsoup/issues/2295: when the input contained a closing
     * {@code </body>} or {@code </html>} tag, those elements were being emitted twice, because a
     * fake onNodeClosed event was fired to track their source positions. Each variation below
     * (with and without the explicit closing tags) must emit every element exactly once.
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "<html><body><a>Link</a></body></html>",
        "<html><body><a>Link</a>",
        "<a>Link</a></body></html>",
        "<a>Link</a>",
        "<a>Link",
        "<a>Link</body>"
    })
    void emitsOnlyOnce(String html) {
        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "")) {
            StringBuilder emitted = new StringBuilder();
            parser.stream().forEach(el -> appendSummary(el, emitted));

            assertEquals("head+;a[Link];body;html;#root;", emitted.toString());
        }
    }
}
