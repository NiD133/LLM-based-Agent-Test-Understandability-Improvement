package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_canStreamFragmentXml {

    /**
     * Appends a compact textual signature of {@code element} to {@code log}, so the order and shape of the
     * streamed elements can be asserted against a single expected string.
     * <p>The signature is: {@code tagName}, then {@code #id} if the element has an id, then {@code [ownText]}
     * if it has own text, then {@code +} if it currently has a next sibling, finally terminated with {@code ;}.</p>
     */
    private static void appendSignature(Element element, StringBuilder log) {
        log.append(element.tagName());
        if (element.hasAttr("id"))
            log.append("#").append(element.id());
        if (!element.ownText().isEmpty())
            log.append("[").append(element.ownText()).append("]");
        if (element.nextElementSibling() != null)
            log.append("+");
        log.append(";");
    }

    /**
     * Returns true once the parser has fully consumed its input and released the backing reader.
     * <p>Reaching into the tree builder's reader is the only way to observe that the stream has been closed.</p>
     */
    private static boolean isClosed(StreamParser parser) {
        return parser.document().parser().getTreeBuilder().reader == null;
    }

    @Test
    void canStreamFragmentXml() throws IOException {
        String xml = "<tr id=1><td>One</td></tr><tr id=2><td>Two</td></tr><tr id=3><td>Three</td></tr>";
        Element context = new Element("Other");

        try (StreamParser parser = new StreamParser(Parser.xmlParser()).parseFragment(xml, context, "")) {
            // Stream every element as it is closed and record a signature of each one.
            StringBuilder streamed = new StringBuilder();
            parser.stream().forEachOrdered(element -> appendSignature(element, streamed));

            // Elements are emitted in document order (children before parents). The trailing "+" on a signature
            // means the element had a next sibling at the moment it was emitted. We get only the fragment's
            // elements plus the synthetic #root, not a full HTML document.
            assertEquals("td[One];tr#1+;td[Two];tr#2+;td[Three];tr#3;#root;", streamed.toString());

            // Streaming to completion consumes the whole input, which closes the underlying reader.
            assertTrue(isClosed(parser));

            // The fragment parses into the three <tr> rows.
            List<Node> fragmentNodes = parser.completeFragment();
            assertEquals(3, fragmentNodes.size());
            assertEquals("tr", fragmentNodes.get(0).nodeName());
        }
    }
}
