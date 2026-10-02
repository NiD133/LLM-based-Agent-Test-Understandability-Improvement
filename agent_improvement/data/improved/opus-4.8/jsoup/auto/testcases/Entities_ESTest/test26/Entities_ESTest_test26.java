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
public class Entities_ESTest_test26 extends Entities_ESTest_scaffolding {

    /**
     * Escapes a string into an appendable backed by a file writer and verifies that the
     * default OutputSettings are left untouched (indent amount stays at its default of 1).
     */
    @Test(timeout = 4000)
    public void escapeWritesToAppendableAndLeavesDefaultIndentUnchanged() throws Throwable {
        MockFileWriter fileWriter = new MockFileWriter("e\nY]<]>");
        QuietAppendable output = QuietAppendable.wrap(fileWriter);
        Document.OutputSettings outputSettings = new Document.OutputSettings();

        String inputToEscape = "v{}++=\"D1mJ";
        int escapeOptions = 76;
        Entities.escape(output, inputToEscape, outputSettings, escapeOptions);

        assertEquals(1, outputSettings.indentAmount());
    }
}
