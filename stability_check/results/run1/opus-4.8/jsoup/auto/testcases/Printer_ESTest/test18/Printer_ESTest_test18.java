package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.parser.Tag;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test18 extends Printer_ESTest_scaffolding {

    /**
     * A shell document should print the standard empty HTML skeleton,
     * regardless of the Tag.PreserveWhitespace option flag value.
     */
    @Test(timeout = 4000)
    public void shellDocumentPrintsEmptyHtmlSkeleton() throws Throwable {
        Document shellDocument = Document.createShell("\"");
        Tag.PreserveWhitespace = 3;

        String html = shellDocument.toString();

        assertEquals("<html><head></head><body></body></html>", html);
    }
}
