package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.parser.Tag;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test18 extends Printer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_documentToStringProducesExpectedHtmlStructure_whenPreserveWhitespaceIsSet() throws Throwable {
        // Create a minimal document shell; the URL contains a literal double-quote character
        Document document = Document.createShell("\"");

        // Override the static PreserveWhitespace tag option to verify it does not affect basic toString output
        Tag.PreserveWhitespace = 3;

        String html = document.toString();

        assertEquals("<html><head></head><body></body></html>", html);
    }
}
