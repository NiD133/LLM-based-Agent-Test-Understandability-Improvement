package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test12 extends TokenQueue_ESTest_scaffolding {

    /**
     * Escaping an empty CSS identifier should return the empty string unchanged,
     * since there are no characters that require escaping.
     */
    @Test(timeout = 4000)
    public void escapeCssIdentifier_withEmptyInput_returnsEmptyString() {
        String escaped = TokenQueue.escapeCssIdentifier("");

        assertEquals("", escaped);
    }
}
