package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test07 extends TokenQueue_ESTest_scaffolding {

    /**
     * Verifies that escapeCssIdentifier:
     *  - escapes the DEL control character (U+007F) as the hex code-point sequence "\7f "
     *  - escapes non-identifier ASCII punctuation ('$', '&') with a leading backslash
     *  - leaves alphanumeric characters unchanged
     *
     * Input breakdown: "p8moD0M" + DEL(U+007F) + "0$t5mTH&"
     * Expected output: "p8moD0M\7f 0\$t5mTH\&"  (Java literal uses \\ for each single backslash)
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // The input string contains a DEL character (U+007F) between 'M' and '0',
        // along with the special punctuation characters '$' and '&'.
        String escapedIdentifier = TokenQueue.escapeCssIdentifier("p8moD0M0$t5mTH&");
        assertEquals("p8moD0M\\7f 0\\$t5mTH\\&", escapedIdentifier);
    }
}
