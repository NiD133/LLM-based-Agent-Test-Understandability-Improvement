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
     * Verifies that a default (strict) {@link Nysiis} encoder strips out
     * punctuation/symbols and encodes the remaining letters into a 6-character
     * NYSIIS code. The input mixes letters with assorted symbols, which the
     * encoder cleans away before applying the algorithm.
     */
    @Test(timeout = 4000)
    public void nysiisIgnoresSymbolsAndEncodesLetters() throws Throwable {
        Nysiis encoder = new Nysiis();

        String code = encoder.nysiis("&:ZN(sDK;@X'DhCe");

        assertEquals("ZNSDCX", code);
    }
}
