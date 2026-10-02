package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.parser.Tag;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test18 extends Printer_ESTest_scaffolding {

    /**
     * A shell document (html > head + body) renders as the canonical empty
     * HTML skeleton, even after the Tag.PreserveWhitespace flag is changed.
     */
    @Test(timeout = 4000)
    public void shellDocumentRendersEmptyHtmlSkeleton() throws Throwable {
        Document shellDocument = Document.createShell("\"");
        Tag.PreserveWhitespace = 3;

        String renderedHtml = shellDocument.toString();

        assertEquals("<html><head></head><body></body></html>", renderedHtml);
    }
}
