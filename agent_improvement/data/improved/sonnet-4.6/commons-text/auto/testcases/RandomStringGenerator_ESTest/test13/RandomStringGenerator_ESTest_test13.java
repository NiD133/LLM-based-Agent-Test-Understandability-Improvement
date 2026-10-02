package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test13 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that calling withinRange with a single null-character pair {'\0', '\0'}
     * succeeds and that DEFAULT_MAXIMUM_CODE_POINT equals Unicode's maximum code point (U+10FFFF = 1114111).
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        // Construct a single char pair where both endpoints are the null character '\0'
        char[] nullCharPair = new char[2]; // elements default to '\0', giving range ['\0', '\0']
        char[][] charRangePairs = new char[1][];
        charRangePairs[0] = nullCharPair;

        builder.withinRange(charRangePairs);

        assertEquals(1114111, RandomStringGenerator.Builder.DEFAULT_MAXIMUM_CODE_POINT);
    }
}
