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
public class Printer_ESTest_test12 extends Printer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        String encodedText = "vSq8d/x nu<U@frER";
        TextNode rootText = TextNode.createFromEncoded(encodedText);

        MockFileWriter writer = new MockFileWriter(encodedText);
        QuietAppendable output = QuietAppendable.wrap(writer);
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        Printer.Pretty prettyPrinter = new Printer.Pretty(rootText, output, outputSettings);

        CDataNode emptyCDataNode = new CDataNode("");
        Node traversedNode = emptyCDataNode.traverse(prettyPrinter);

        assertSame(traversedNode, emptyCDataNode);
    }
}
