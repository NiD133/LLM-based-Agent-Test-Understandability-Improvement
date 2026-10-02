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
public class Printer_ESTest_test09 extends Printer_ESTest_scaffolding {

    private static final String ENCODED_TEXT = " />";
    private static final int NEGATIVE_WIDTH = -1;

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        TextNode rootTextNode = TextNode.createFromEncoded(ENCODED_TEXT);
        MockFileWriter writer = new MockFileWriter(ENCODED_TEXT);
        QuietAppendable appendable = QuietAppendable.wrap(writer);
        Printer.Pretty printer = (Printer.Pretty) Printer.printerFor(rootTextNode, appendable);

        Document document = new Document(ENCODED_TEXT, ENCODED_TEXT);
        Element body = document.body();

        try {
            printer.addHead(body, NEGATIVE_WIDTH);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
