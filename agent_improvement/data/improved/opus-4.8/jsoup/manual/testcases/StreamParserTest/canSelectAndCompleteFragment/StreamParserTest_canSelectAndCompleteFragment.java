package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class StreamParserTest_canSelectAndCompleteFragment {

    /**
     * Streams a table-row fragment through a {@link StreamParser}: each {@code <td>} should be
     * emitted in document order, and {@link StreamParser#completeFragment()} should return the
     * finished fragment with the {@code <tbody>} that the HTML parser infers for table rows.
     */
    @Test
    void canSelectAndCompleteFragment() throws IOException {
        // Three table rows, each holding one cell. There is no <table>/<tbody> wrapper in the
        // input; the fragment is parsed in the context of a <table>, so the parser infers a <tbody>.
        String html = "<tr id=1><td>One</td><tr id=2><td>Two</td></tr><tr id=3><td>Three</td></tr>";
        Element tableContext = new Element("table");

        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parseFragment(html, tableContext, "")) {
            // Stream the cells one at a time; they arrive in document order.
            Element firstCell = parser.expectNext("td");
            assertEquals("One", firstCell.ownText());

            Element secondCell = parser.expectNext("td");
            assertEquals("Two", secondCell.ownText());

            Element thirdCell = parser.expectNext("td");
            assertEquals("Three", thirdCell.ownText());

            // No further <td> remains in the input.
            assertNull(parser.selectNext("td"));

            // Finish the parse and inspect the resulting fragment tree.
            List<Node> fragmentRoots = parser.completeFragment();

            // The single root node is the <tbody> inferred by the table-aware parser.
            assertEquals(1, fragmentRoots.size());
            Node tbody = fragmentRoots.get(0);
            assertEquals("tbody", tbody.nodeName());

            // The <tbody> holds the three <tr> rows.
            List<Node> rows = tbody.childNodes();
            assertEquals(3, rows.size());

            // The first row's first child is the very <td> element streamed earlier (same instance).
            assertSame(rows.get(0).childNode(0), firstCell);

            // Streamed elements belong to the fragment's shell document.
            assertSame(parser.document(), firstCell.ownerDocument());
        }
    }
}
