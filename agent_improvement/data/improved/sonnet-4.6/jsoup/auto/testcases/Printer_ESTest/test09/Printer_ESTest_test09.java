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
     * Verifies that {@link Printer.Pretty#addHead} throws {@link IllegalArgumentException}
     * when called with a negative depth value. A negative depth causes
     * {@code StringUtil.padding()} to receive a negative width, which is rejected
     * by {@code Validate.isTrue} with the message "width must be >= 0".
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Build a TextNode as the printer's root node; its output settings will
        // default to pretty-print mode, so printerFor() returns a Printer.Pretty.
        TextNode rootTextNode = TextNode.createFromEncoded(" />");
        MockFileWriter fileWriter = new MockFileWriter(" />");
        QuietAppendable output = QuietAppendable.wrap(fileWriter);
        Printer.Pretty prettyPrinter = (Printer.Pretty) Printer.printerFor(rootTextNode, output);

        // Provide a real Element to pass to addHead; the body element of a
        // freshly created document is a suitable block-level element.
        Document document = new Document(" />", " />");
        Element bodyElement = document.body();

        // A negative depth is invalid: padding() computes (depth * indentAmount)
        // which yields a negative width and triggers the validation failure.
        try {
            prettyPrinter.addHead(bodyElement, -1);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
