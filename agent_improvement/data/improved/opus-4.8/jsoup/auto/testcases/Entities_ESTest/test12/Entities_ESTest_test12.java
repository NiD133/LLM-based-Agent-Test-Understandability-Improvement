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
public class Entities_ESTest_test12 extends Entities_ESTest_scaffolding {

    /**
     * Escaping a whitespace-only string should not change the output settings.
     * In particular, the syntax stays at its default value of HTML after the call.
     */
    @Test(timeout = 4000)
    public void escapeDoesNotChangeOutputSettingsSyntax() throws Throwable {
        // Target that the escaped characters are written to.
        MockFileWriter fileWriter = new MockFileWriter("         ");
        QuietAppendable output = QuietAppendable.wrap(fileWriter);

        // Output settings start with the default HTML syntax.
        Document.OutputSettings outputSettings = new Document.OutputSettings();

        // Escape a whitespace-only string. The escape options value (-67) is passed
        // through unchanged to preserve the original test's behaviour.
        Entities.escape(output, "         ", outputSettings, -67);

        // Escaping reads from, but never mutates, the output settings' syntax.
        assertEquals(Document.OutputSettings.Syntax.html, outputSettings.syntax());
    }
}
