package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test03 extends TokenQueue_ESTest_scaffolding {

    /**
     * A plain CSS identifier (letters, digits, and underscores, with no escape
     * sequences) should be consumed verbatim and returned unchanged.
     */
    @Test(timeout = 4000)
    public void consumeCssIdentifier_returnsPlainIdentifierUnchanged() throws Throwable {
        TokenQueue queue = new TokenQueue("dQ_7Q");

        String identifier = queue.consumeCssIdentifier();

        assertEquals("dQ_7Q", identifier);
    }
}
