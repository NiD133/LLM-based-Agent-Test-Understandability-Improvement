package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.BufferedOutputStream;
import java.io.PipedOutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.jsoup.internal.QuietAppendable;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test05 extends Entities_ESTest_scaffolding {

    /**
     * Escaping with a negative options bitmask still completes without error and
     * leaves the OutputSettings' syntax untouched (escape only reads the settings,
     * it never mutates them). The default syntax is HTML.
     */
    @Test(timeout = 4000)
    public void escapeWithNegativeOptionsLeavesSyntaxAsHtml() throws Throwable {
        Document.OutputSettings outputSettings = new Document.OutputSettings();

        // Build a QuietAppendable backed by a print stream that discards its output;
        // the test only cares that escape() runs, not what it writes.
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(pipedOutputStream);
        MockPrintStream printStream = new MockPrintStream(bufferedOutputStream, false);
        QuietAppendable output = QuietAppendable.wrap(printStream);

        int negativeOptions = -1814;
        Entities.escape(output, "20,HVe0[Tl'l>TR", outputSettings, negativeOptions);

        assertEquals(Document.OutputSettings.Syntax.html, outputSettings.syntax());
    }
}
