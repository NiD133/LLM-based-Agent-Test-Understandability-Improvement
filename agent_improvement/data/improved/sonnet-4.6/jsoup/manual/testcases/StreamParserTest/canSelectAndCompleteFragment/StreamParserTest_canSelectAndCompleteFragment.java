package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_canSelectAndCompleteFragment {

    @Test
    void canSelectAndCompleteFragment() throws IOException {
        // Fragment input: three table rows, each with one cell
        String html = "<tr id=1><td>One</td><tr id=2><td>Two</td></tr><tr id=3><td>Three</td></tr>";
        // Parse as a fragment rooted in a <table> context so the browser-implied <tbody> is created
        Element tableContext = new Element("table");

        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parseFragment(html, tableContext, "")) {
            // Retrieve each <td> in document order; expectNext throws if no match is found
            Element firstTd = parser.expectNext("td");
            assertEquals("One", firstTd.ownText());

            Element secondTd = parser.expectNext("td");
            assertEquals("Two", secondTd.ownText());

            Element thirdTd = parser.expectNext("td");
            assertEquals("Three", thirdTd.ownText());

            // No more <td> elements remain; selectNext returns null instead of throwing
            Element noMoreTd = parser.selectNext("td");
            assertNull(noMoreTd);

            // Finish the parse and retrieve the top-level fragment nodes
            List<Node> fragmentRoots = parser.completeFragment();

            // The HTML parser wraps the <tr> elements in an inferred <tbody>
            assertEquals(1, fragmentRoots.size());
            Node tbody = fragmentRoots.get(0);
            assertEquals("tbody", tbody.nodeName());

            // The <tbody> should contain all three <tr> rows
            List<Node> rows = tbody.childNodes();
            assertEquals(3, rows.size());

            // Verify the first <td> is still attached under the first <tr>
            assertSame(rows.get(0).childNode(0), firstTd);

            // All fragment nodes share the same shell document created by the parser
            assertSame(parser.document(), firstTd.ownerDocument());
        }
    }
}
