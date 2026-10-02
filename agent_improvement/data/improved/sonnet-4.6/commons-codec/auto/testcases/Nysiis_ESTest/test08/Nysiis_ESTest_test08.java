package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test08 extends Nysiis_ESTest_scaffolding {

    /**
     * Verifies that a string containing non-alpha characters (pipes, digits, punctuation)
     * mixed with alphabetic characters is cleaned and NYSIIS-encoded correctly in strict
     * mode (max 6 characters). The default Nysiis constructor enables strict mode.
     *
     * Input:  "|5tl0=0cuh:63pEY"
     * After cleaning (letters only, uppercased): "TLCUHPEY"
     * Expected NYSIIS code (strict, max 6 chars): "TLCAPY"
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Nysiis nysiis0 = new Nysiis();

        // Default constructor sets strict mode (max 6-char output)
        assertTrue(nysiis0.isStrict());

        // Non-alphabetic characters are stripped by SoundexUtils.clean before encoding
        String encodedResult = nysiis0.nysiis("|5tl0=0cuh:63pEY");
        assertEquals("TLCAPY", encodedResult);
    }
}
