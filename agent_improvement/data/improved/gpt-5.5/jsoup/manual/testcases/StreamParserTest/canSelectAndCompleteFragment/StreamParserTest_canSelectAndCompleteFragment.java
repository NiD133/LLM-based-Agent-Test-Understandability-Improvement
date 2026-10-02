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
    private static final String TABLE_ROWS_FRAGMENT =
        "<tr id=1><td>One</td><tr id=2><td>Two</td></tr><tr id=3><td>Three</td></tr>";

    @Test
    void canSelectAndCompleteFragment() throws IOException {
        Element tableContext = new Element("table");

        try (StreamParser parser = new StreamParser(Parser.htmlParser())
            .parseFragment(TABLE_ROWS_FRAGMENT, tableContext, "")) {

            Element firstCell = parser.expectNext("td");
            assertEquals("One", firstCell.ownText());

            Element secondCell = parser.expectNext("td");
            assertEquals("Two", secondCell.ownText());

            Element thirdCell = parser.expectNext("td");
            assertEquals("Three", thirdCell.ownText());

            Element noMoreCells = parser.selectNext("td");
            assertNull(noMoreCells);

            List<Node> fragmentNodes = parser.completeFragment();
            assertEquals(1, fragmentNodes.size());

            Node inferredTbody = fragmentNodes.get(0);
            assertEquals("tbody", inferredTbody.nodeName());

            List<Node> tableRows = inferredTbody.childNodes();
            assertEquals(3, tableRows.size());
            assertSame(tableRows.get(0).childNode(0), firstCell);
            assertSame(parser.document(), firstCell.ownerDocument());
        }
    }
}
