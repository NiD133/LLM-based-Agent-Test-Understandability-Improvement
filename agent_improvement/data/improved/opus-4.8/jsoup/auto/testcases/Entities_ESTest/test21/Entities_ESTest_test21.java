package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test21 extends Entities_ESTest_scaffolding {

    /**
     * The base escape mode should resolve codepoint 38 ('&') to the
     * HTML entity name "amp".
     */
    @Test(timeout = 4000)
    public void nameForCodepoint_ampersand_returnsAmp() throws Throwable {
        Entities.EscapeMode baseEscapeMode = Entities.EscapeMode.base;

        int ampersandCodepoint = 38; // '&'
        String entityName = baseEscapeMode.nameForCodepoint(ampersandCodepoint);

        assertEquals("amp", entityName);
    }
}
