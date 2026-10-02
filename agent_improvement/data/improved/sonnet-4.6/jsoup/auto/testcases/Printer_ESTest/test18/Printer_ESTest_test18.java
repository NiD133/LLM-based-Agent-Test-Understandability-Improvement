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
public class Printer_ESTest_test18 extends Printer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18_documentToStringUnaffectedByPreserveWhitespaceFlagChange() throws Throwable {
        // Create a minimal HTML shell document with a quote character as the base URI
        Document shellDocument = Document.createShell("\"");

        // Mutate the static PreserveWhitespace flag; the document output should be unaffected
        Tag.PreserveWhitespace = 3;

        String html = shellDocument.toString();

        assertEquals("<html><head></head><body></body></html>", html);
    }
}
