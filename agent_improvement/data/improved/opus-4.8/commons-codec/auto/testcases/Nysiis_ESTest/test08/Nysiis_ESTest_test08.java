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
     * Verifies that the default Nysiis encoder runs in strict mode and that
     * encoding a noisy input (digits and symbols mixed with letters) yields the
     * NYSIIS code derived only from its letters, capped at the strict length of 6.
     */
    @Test(timeout = 4000)
    public void encodingNoisyInputInStrictModeKeepsOnlyLetters() throws Throwable {
        Nysiis defaultEncoder = new Nysiis();

        String nysiisCode = defaultEncoder.nysiis("|5tl0=0cuh:63pEY");

        assertTrue("Default encoder should be in strict mode", defaultEncoder.isStrict());
        assertEquals("TLCAPY", nysiisCode);
    }
}
