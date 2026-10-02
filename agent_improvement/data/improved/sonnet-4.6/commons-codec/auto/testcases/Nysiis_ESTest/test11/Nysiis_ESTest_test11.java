package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test11 extends Nysiis_ESTest_scaffolding {

    // Verifies that non-alphabetic characters (digits, punctuation) are stripped before NYSIIS
    // encoding, and the remaining letters are encoded correctly in strict mode (max 6 chars).
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Nysiis nysiis = new Nysiis();
        // Input contains letters mixed with special chars/digits; only letters contribute to the code
        String encoded = nysiis.nysiis("Bd}F:LH6ciJkN{]w^1");
        assertEquals("BDFLCA", encoded);
        assertNotNull(encoded);
    }
}
