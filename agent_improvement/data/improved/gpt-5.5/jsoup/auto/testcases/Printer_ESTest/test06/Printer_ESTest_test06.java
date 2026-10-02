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

    private static final String SVG_NAMESPACE = "http://www.w3.org/2000/svg";
    private static final String XML_NAMESPACE = "http://www.w3.org/XML/1998/namespace";
    private static final String BLANK_TEXT = "             ";
    private static final String BEFORE_HTML = ".A4qu.4yCsmU>Y";

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Parser parser = Parser.xmlParser();
        Document document = parser.parseInput(SVG_NAMESPACE, XML_NAMESPACE);
        Document appendedDocument = (Document) document.appendText(XML_NAMESPACE);

        document.appendText(BLANK_TEXT);

        CDataNode namespaceCData = new CDataNode(XML_NAMESPACE);
        CDataNode emptyCData = new CDataNode("");
        Node[] children = new Node[3];
        children[0] = (Node) namespaceCData;
        children[1] = (Node) document;
        children[2] = (Node) emptyCData;
        document.addChildren(children);

        appendedDocument.before(BEFORE_HTML);

        boolean hasNonTextNodes = Printer.Pretty.hasNonTextNodes(document);

        assertFalse(hasNonTextNodes);
    }
}
