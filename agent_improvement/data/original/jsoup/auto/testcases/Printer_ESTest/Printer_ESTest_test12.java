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
        TextNode textNode0 = TextNode.createFromEncoded("vSq8d/x nu<U@frER");
        MockFileWriter mockFileWriter0 = new MockFileWriter("vSq8d/x nu<U@frER");
        QuietAppendable quietAppendable0 = QuietAppendable.wrap(mockFileWriter0);
        Document.OutputSettings document_OutputSettings0 = new Document.OutputSettings();
        Printer.Pretty printer_Pretty0 = new Printer.Pretty(textNode0, quietAppendable0, document_OutputSettings0);
        CDataNode cDataNode0 = new CDataNode("");
        Node node0 = cDataNode0.traverse(printer_Pretty0);
        assertSame(node0, cDataNode0);
    }
}
