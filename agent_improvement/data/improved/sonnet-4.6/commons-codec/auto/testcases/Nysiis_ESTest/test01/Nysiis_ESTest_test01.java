package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test01 extends Nysiis_ESTest_scaffolding {

    /**
     * Verifies that the NYSIIS encoder (strict mode, max 6 chars) correctly encodes
     * a string containing special characters and mixed case. The encoder strips
     * non-alphabetic characters and applies phonetic transcoding rules, so
     * "&:ZN(sDK;@X'DhCe" is cleaned to "ZNSDKXDHCE" and phonetically encoded to "ZNSDCX".
     */
    @Test(timeout = 4000)
    public void test_nysiis_encodesInputWithSpecialCharacters_returnsPhoneticCode() throws Throwable {
        Nysiis nysiis = new Nysiis();
        String encodedResult = nysiis.nysiis("&:ZN(sDK;@X'DhCe");
        assertEquals("ZNSDCX", encodedResult);
    }
}
