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
public class Entities_ESTest_test11 extends Entities_ESTest_scaffolding {

    /**
     * Verifies that Entities.escape() writes escaped output to a QuietAppendable
     * without modifying the supplied OutputSettings. In particular, the default
     * OutputSettings should still report outline() == false after escaping.
     */
    @Test(timeout = 4000)
    public void escapeDoesNotEnableOutline() throws Throwable {
        String textToEscape = "Zect     _ @          ";

        // Wrap a file writer so escape() has somewhere to emit its output.
        MockFileWriter fileWriter = new MockFileWriter(textToEscape);
        QuietAppendable output = QuietAppendable.wrap(fileWriter);

        Document.OutputSettings outputSettings = new Document.OutputSettings();
        int escapeOptions = -20;

        Entities.escape(output, textToEscape, outputSettings, escapeOptions);

        // Escaping must not toggle the outline flag on the output settings.
        assertFalse(outputSettings.outline());
    }
}
