package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that a type-filtered NodeIterator allows in-place text modification of matched elements
 * without disrupting the traversal order or skipping nodes.
 *
 * The HTML structure used across tests:
 *   <div id=1>
 *     <p>One</p>
 *     <p>Two</p>
 *   </div>
 *   <div id=2>
 *     <p>Three</p>
 *     <p>Four</p>
 *   </div>
 */
public class NodeIteratorTest_canModifyFilteredElements {

    // Two sibling divs, each with two paragraph children that have non-empty text.
    private static final String HTML =
        "<div id=1><p>One<p>Two</div><div id=2><p>Three<p>Four</div>";

    // -------------------------------------------------------------------------
    // Helper: walk an arbitrary Node iterator and build a compact string of
    // visited nodes so tests can assert traversal order with a single assertEquals.
    // -------------------------------------------------------------------------

    /**
     * Iterates {@code it} to exhaustion, appending a token for each node to a
     * StringBuilder, and returns the accumulated string.
     * <p>
     * Token format:
     * <ul>
     *   <li>Element without id → "tagname;"</li>
     *   <li>Element with id    → "tagname#id;"</li>
     *   <li>TextNode           → "text content;"</li>
     *   <li>Any other node     → "nodeName();"</li>
     * </ul>
     * Also asserts that no two consecutive {@code next()} calls return the same
     * object reference, and that no call returns {@code null}.
     */
    static <T extends Node> String collectTraversalTokens(Iterator<T> it) {
        StringBuilder tokens = new StringBuilder();
        Node previousNode = null;
        while (it.hasNext()) {
            Node node = it.next();
            assertNotNull(node, "next() must never return null");
            assertNotSame(previousNode, node, "consecutive next() calls must return distinct nodes");
            appendNodeToken(node, tokens);
            previousNode = node;
        }
        return tokens.toString();
    }

    /** Appends a single descriptive token (ending with ";") for {@code node}. */
    static void appendNodeToken(Node node, StringBuilder out) {
        if (node instanceof Element) {
            Element el = (Element) node;
            out.append(el.tagName());
            if (el.hasAttr("id"))
                out.append("#").append(el.id());
        } else if (node instanceof TextNode) {
            out.append(((TextNode) node).text());
        } else {
            out.append(node.nodeName());
        }
        out.append(";");
    }

    /** Convenience: iterate every node under {@code root} and return the token string. */
    static String collectAllNodes(Element root) {
        return collectTraversalTokens(NodeIterator.from(root));
    }

    // -------------------------------------------------------------------------
    // Test
    // -------------------------------------------------------------------------

    /**
     * A NodeIterator<Element> visits every Element in document order.
     * Modifying the text of an element during iteration (via el.text(...)) must
     * not break the traversal — all elements are still visited exactly once, in
     * document order.
     *
     * Scenario:
     *   1. Parse the HTML and create an Element-only iterator over the document.
     *   2. For each element, if it has non-empty own text, append "++" to that text.
     *   3. Verify that every element was visited (in document order).
     *   4. Re-traverse the full document (nodes + text) to confirm the mutations
     *      actually took effect in the DOM.
     */
    @Test
    void canModifyFilteredElements() {
        Document doc = Jsoup.parse(HTML);

        // Step 1: iterate over Element nodes only.
        NodeIterator<Element> elementIterator = new NodeIterator<>(doc, Element.class);

        // Step 2: mutate text in-place while iterating.
        StringBuilder visitedElements = new StringBuilder();
        while (elementIterator.hasNext()) {
            Element el = elementIterator.next();
            if (!el.ownText().isEmpty()) {
                // Append "++" to elements that directly contain text.
                el.text(el.ownText() + "++");
            }
            appendNodeToken(el, visitedElements);
        }

        // Step 3: all elements must have been visited in document order,
        // regardless of the in-place mutations above.
        assertEquals(
            "#root;html;head;body;div#1;p;p;div#2;p;p;",
            visitedElements.toString(),
            "Element iterator must visit every element in document order"
        );

        // Step 4: a fresh full traversal of the document (Elements + TextNodes)
        // must reflect the "++" mutations in the text nodes.
        assertEquals(
            "#root;html;head;body;div#1;p;One++;p;Two++;div#2;p;Three++;p;Four++;",
            collectAllNodes(doc),
            "Text mutations made during iteration must be present in the final DOM"
        );
    }
}
