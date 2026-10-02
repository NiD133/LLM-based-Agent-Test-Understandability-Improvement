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
     * Verifies that {@link RandomStringGenerator.Builder#withinRange(char[][])} accepts a
     * well-formed range table (each inner array holds exactly a [min, max] code point pair),
     * and that the builder's default maximum code point constant is Unicode's largest value.
     */
    @Test(timeout = 4000)
    public void withinRangeAcceptsValidMinMaxPair() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        // A single [min, max] pair; a length-2 inner array is the only valid shape accepted.
        char[][] codePointRanges = new char[1][];
        codePointRanges[0] = new char[2];

        builder.withinRange(codePointRanges);

        assertEquals(1114111, RandomStringGenerator.Builder.DEFAULT_MAXIMUM_CODE_POINT);
    }
}
