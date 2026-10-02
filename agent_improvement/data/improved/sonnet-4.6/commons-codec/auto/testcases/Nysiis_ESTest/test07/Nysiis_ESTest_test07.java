package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test07 extends Nysiis_ESTest_scaffolding {

    /**
     * Verifies that NYSIIS correctly encodes a string containing non-alphabetic
     * characters (punctuation, digits, special symbols). The encoder strips all
     * non-letter characters before applying phonetic rules, so the mixed input
     * "B*(EG$;*A+w7oQ" is treated as "BEGAWOQ" and produces "BAGAG" in strict mode.
     *
     * Also confirms that the no-argument constructor creates a strict-mode encoder
     * (encoded result capped at 6 characters).
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Default constructor enables strict mode (max 6-character output)
        Nysiis nysiisEncoder = new Nysiis();

        // Input contains digits, punctuation, and mixed-case letters;
        // non-alphabetic characters are stripped before phonetic encoding
        String encodedResult = nysiisEncoder.nysiis("B*(EG$;*A+w7oQ");

        assertEquals("BAGAG", encodedResult);
        assertTrue(nysiisEncoder.isStrict());
    }
}
