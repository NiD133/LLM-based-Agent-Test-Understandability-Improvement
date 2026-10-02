package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test09 extends Nysiis_ESTest_scaffolding {

    /**
     * Verifies that the NYSIIS encoder (in default strict mode, max 6 chars) correctly
     * encodes a mixed-case alphanumeric string containing special characters and digits.
     *
     * Input "dIpYze0FuZfr/K1EPh%" — after cleaning (non-alpha stripped, uppercased)
     * becomes "DIPYZEFUZFRKEEPH", which the NYSIIS algorithm encodes to "DAPYSA".
     */
    @Test(timeout = 4000)
    public void test09_nysiisEncodesAlphanumericStringWithSpecialChars() throws Throwable {
        Nysiis nysiis = new Nysiis(); // strict mode: output capped at 6 characters

        String encoded = nysiis.nysiis("dIpYze0FuZfr/K1EPh%");

        assertEquals("DAPYSA", encoded);
    }
}
