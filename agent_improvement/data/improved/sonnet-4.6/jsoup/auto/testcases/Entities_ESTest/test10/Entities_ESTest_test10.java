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

    // Verifies that calling Entities.escape() does not modify the escapeMode
    // of the OutputSettings passed to it — it should remain at its default value (base).
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        MockPrintStream printStream = new MockPrintStream("[:9;Wd@P3x0sfFM/");
        QuietAppendable appendable = QuietAppendable.wrap(printStream);
        Document.OutputSettings outputSettings = new Document.OutputSettings();

        Entities.escape(appendable, "Must be false", outputSettings, (-249532396));

        assertEquals(Entities.EscapeMode.base, outputSettings.escapeMode());
    }
}
