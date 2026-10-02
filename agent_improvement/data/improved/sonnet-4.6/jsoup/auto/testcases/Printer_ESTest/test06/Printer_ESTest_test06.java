package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test06 extends Printer_ESTest_scaffolding {

    /**
     * Verifies that {@link Printer.Pretty#hasNonTextNodes} returns false for a document
     * whose children consist only of CDataNodes (which extend TextNode) and text nodes,
     * even after adding a self-referential child and triggering a sibling-insertion call.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Parse an SVG-namespace URL string as XML to create a base document
        Parser xmlParser = Parser.xmlParser();
        Document baseDocument = xmlParser.parseInput("http://www.w3.org/2000/svg", "http://www.w3.org/XML/1998/namespace");

        // appendText returns the node itself cast to Node; keep a second reference to it
        Document documentWithAppendedText = (Document) baseDocument.appendText("http://www.w3.org/XML/1998/namespace");

        // Append whitespace-only text to the base document
        baseDocument.appendText("             ");

        // Create two CDataNodes (CDataNode extends TextNode, so both count as text nodes)
        CDataNode cdataWithContent = new CDataNode("http://www.w3.org/XML/1998/namespace");
        CDataNode cdataEmpty = new CDataNode("");

        // Build a mixed child array: two CDataNodes surrounding a self-reference to baseDocument
        Node[] mixedChildren = new Node[3];
        mixedChildren[0] = (Node) cdataWithContent;
        mixedChildren[1] = (Node) baseDocument;   // self-referential add
        mixedChildren[2] = (Node) cdataEmpty;
        baseDocument.addChildren(mixedChildren);

        // Insert a sibling CSS-selector string before documentWithAppendedText (does not affect hasNonTextNodes)
        documentWithAppendedText.before(".A4qu.4yCsmU>Y");

        // All children visible to hasNonTextNodes are TextNode instances (CDataNode is a TextNode subtype),
        // so the method must return false
        boolean hasNonText = Printer.Pretty.hasNonTextNodes(baseDocument);
        assertFalse(hasNonText);
    }
}
