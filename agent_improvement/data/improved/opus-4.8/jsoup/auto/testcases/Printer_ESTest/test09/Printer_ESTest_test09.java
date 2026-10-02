package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.jsoup.internal.QuietAppendable;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test09 extends Printer_ESTest_scaffolding {

    /**
     * A pretty printer indents an element by its depth before writing it. When asked to print
     * an element at a negative depth, the indentation step computes a negative padding width,
     * which Validate rejects with "width must be >= 0".
     */
    @Test(timeout = 4000)
    public void addHeadWithNegativeDepthRejectsNegativeWidth() throws Throwable {
        // Build a pretty printer that writes the rendered HTML into a file.
        TextNode root = TextNode.createFromEncoded(" />");
        MockFileWriter output = new MockFileWriter(" />");
        QuietAppendable destination = QuietAppendable.wrap(output);
        Printer.Pretty prettyPrinter = (Printer.Pretty) Printer.printerFor(root, destination);

        // The element to print: the <body> of a freshly created document.
        Document document = new Document(" />", " />");
        Element body = document.body();

        // A negative depth makes the indentation step request a negative padding width.
        int negativeDepth = -1;
        try {
            prettyPrinter.addHead(body, negativeDepth);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // width must be >= 0
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
