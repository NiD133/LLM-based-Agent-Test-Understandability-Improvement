package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.jsoup.internal.QuietAppendable;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test10 extends Entities_ESTest_scaffolding {

    /**
     * Escaping to an arbitrary appendable must not change the OutputSettings'
     * escape mode, which stays at its default value of {@code base}.
     */
    @Test(timeout = 4000)
    public void escapeDoesNotChangeOutputSettingsEscapeMode() throws Throwable {
        MockPrintStream printStream = new MockPrintStream("[:9;Wd@P3x0sfFM/");
        QuietAppendable output = QuietAppendable.wrap(printStream);
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        int escapeOptions = -249532396;

        Entities.escape(output, "Must be false", outputSettings, escapeOptions);

        assertEquals(Entities.EscapeMode.base, outputSettings.escapeMode());
    }
}
