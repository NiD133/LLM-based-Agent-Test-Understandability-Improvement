package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.jsoup.internal.QuietAppendable;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test06 extends Entities_ESTest_scaffolding {

    /**
     * Verifies that escaping a string into a (file-backed) appendable does not
     * disturb the OutputSettings: the default syntax remains HTML.
     */
    @Test(timeout = 4000)
    public void escapingIntoFileWriterLeavesSyntaxAsHtml() throws Throwable {
        // Wrap a mock file writer so Entities.escape has somewhere to write output.
        MockFileWriter fileWriter = new MockFileWriter("20,HVe0[Tl&apos;l&gt;TR");
        QuietAppendable output = QuietAppendable.wrap(fileWriter);

        // Fresh settings start out with the default HTML syntax.
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        String textToEscape = "20,HVe0[Tl'l>TR";
        int escapeOptions = -991;

        Entities.escape(output, textToEscape, outputSettings, escapeOptions);

        // Escaping reads from the settings but must not change the syntax.
        assertEquals(Document.OutputSettings.Syntax.html, outputSettings.syntax());
    }
}
