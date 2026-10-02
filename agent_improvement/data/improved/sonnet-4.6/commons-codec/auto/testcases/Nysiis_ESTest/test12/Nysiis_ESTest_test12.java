package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test12 extends Nysiis_ESTest_scaffolding {

    /**
     * Verifies that Nysiis encodes a mixed alphanumeric/special-character string by
     * stripping non-letter characters, applying phonetic transcoding rules, and
     * truncating to the 6-character strict-mode limit.
     *
     * Input "tl1[CoH5>Aeu)UA;J." contains digits, brackets, angle-brackets, and a
     * period that are removed during cleaning. The remaining letters "tlCoHAeuUAJ"
     * are then phonetically encoded to "TLCAHA".
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        // Default constructor uses strict mode (max 6 characters in the output)
        Nysiis nysiis = new Nysiis();

        String encoded = nysiis.nysiis("tl1[CoH5>Aeu)UA;J.");

        assertNotNull(encoded);
        assertEquals("TLCAHA", encoded);
    }
}
