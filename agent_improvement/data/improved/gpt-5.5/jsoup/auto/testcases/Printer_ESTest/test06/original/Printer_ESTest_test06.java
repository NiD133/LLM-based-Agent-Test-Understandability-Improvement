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

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Parser parser0 = Parser.xmlParser();
        Document document0 = parser0.parseInput("http://www.w3.org/2000/svg", "http://www.w3.org/XML/1998/namespace");
        Document document1 = (Document) document0.appendText("http://www.w3.org/XML/1998/namespace");
        document0.appendText("             ");
        CDataNode cDataNode0 = new CDataNode("http://www.w3.org/XML/1998/namespace");
        CDataNode cDataNode1 = new CDataNode("");
        Node[] nodeArray0 = new Node[3];
        nodeArray0[0] = (Node) cDataNode0;
        nodeArray0[1] = (Node) document0;
        nodeArray0[2] = (Node) cDataNode1;
        document0.addChildren(nodeArray0);
        document1.before(".A4qu.4yCsmU>Y");
        boolean boolean0 = Printer.Pretty.hasNonTextNodes(document0);
        assertFalse(boolean0);
    }
}
