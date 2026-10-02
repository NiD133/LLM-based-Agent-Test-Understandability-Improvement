package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_emitsOnlyOnce {

    /**
     * Builds a compact string token for an element to record which elements were visited and in what order.
     *
     * Format per element: {@code tagName[#id][ownText][+];}
     * <ul>
     *   <li>{@code tagName} — the element's tag, e.g. {@code head}, {@code a}, {@code #root}</li>
     *   <li>{@code #id}     — appended only when the element carries an {@code id} attribute</li>
     *   <li>{@code [text]}  — appended only when the element has non-empty own text</li>
     *   <li>{@code +}       — appended when the element has a next element sibling</li>
     *   <li>{@code ;}       — always appended as a delimiter</li>
     * </ul>
     */
    static void trackSeen(Element el, StringBuilder visitLog) {
        visitLog.append(el.tagName());
        if (el.hasAttr("id"))
            visitLog.append("#").append(el.id());
        if (!el.ownText().isEmpty())
            visitLog.append("[").append(el.ownText()).append("]");
        if (el.nextElementSibling() != null)
            visitLog.append("+");
        visitLog.append(";");
    }

    /**
     * Expected visit order for a minimal HTML document containing a single {@code <a>} element.
     * Elements are emitted bottom-up (children before parents) as each element is closed:
     * {@code head} (with next sibling) → {@code a[Link]} → {@code body} → {@code html} → {@code #root}
     */
    private static final String EXPECTED_VISIT_LOG = "head+;a[Link];body;html;#root;";

    /**
     * Verifies that each element in the stream is emitted exactly once, regardless of whether the
     * HTML is well-formed or has implicit/explicit closing tags.
     *
     * Regression test for https://github.com/jhy/jsoup/issues/2295 — closing {@code </body>} or
     * {@code </html>} tags were previously causing duplicate emissions because a synthetic
     * {@code onNodeClosed} call fired twice to track source positions.
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "<html><body><a>Link</a></body></html>",  // fully closed, well-formed
        "<html><body><a>Link</a>",               // missing </body></html>
        "<a>Link</a></body></html>",             // missing <html><body>
        "<a>Link</a>",                           // bare element only
        "<a>Link",                               // unclosed <a>
        "<a>Link</body>"                         // mismatched closing tag
    })
    void emitsOnlyOnce(String html) {
        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "")) {
            StringBuilder visitLog = new StringBuilder();
            parser.stream().forEach(el -> trackSeen(el, visitLog));
            assertEquals(EXPECTED_VISIT_LOG, visitLog.toString());
        }
    }
}
