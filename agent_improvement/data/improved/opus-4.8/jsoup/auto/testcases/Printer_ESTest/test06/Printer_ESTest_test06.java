package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.parser.Parser;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test06 extends Printer_ESTest_scaffolding {

    /**
     * Verifies that {@link Printer.Pretty#hasNonTextNodes(Element)} returns
     * {@code false} for a Document whose first child nodes are text nodes.
     *
     * The document is parsed with the XML parser, has a couple of text nodes
     * appended, and then has a mix of CDATA nodes and itself added as children.
     */
    @Test(timeout = 4000)
    public void hasNonTextNodes_returnsFalseForDocumentLedByTextNodes() throws Throwable {
        // Parse an arbitrary input as XML to obtain a Document to work with.
        Parser xmlParser = Parser.xmlParser();
        Document document = xmlParser.parseInput(
                "http://www.w3.org/2000/svg",
                "http://www.w3.org/XML/1998/namespace");

        // Append text nodes to the document.
        Document sameDocument = (Document) document.appendText("http://www.w3.org/XML/1998/namespace");
        document.appendText("             ");

        // Build a batch of children: two CDATA nodes plus the document itself.
        CDataNode cDataNode = new CDataNode("http://www.w3.org/XML/1998/namespace");
        CDataNode emptyCDataNode = new CDataNode("");
        Node[] childrenToAdd = new Node[3];
        childrenToAdd[0] = cDataNode;
        childrenToAdd[1] = document;
        childrenToAdd[2] = emptyCDataNode;
        document.addChildren(childrenToAdd);

        // Insert a sibling node before the document.
        sameDocument.before(".A4qu.4yCsmU>Y");

        boolean hasNonTextNodes = Printer.Pretty.hasNonTextNodes(document);

        assertFalse(hasNonTextNodes);
    }
}
