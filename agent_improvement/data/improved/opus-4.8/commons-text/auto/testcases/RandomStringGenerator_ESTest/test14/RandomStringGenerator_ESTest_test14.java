package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test14 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * {@link RandomStringGenerator.Builder#withinRange(char[][])} expects each
     * entry to be a [minimum, maximum] code point pair, i.e. an array of length 2.
     * Here the single pair has length 6, so the builder must reject it with an
     * {@link IllegalArgumentException} raised by Apache Commons {@code Validate}.
     */
    @Test(timeout = 4000)
    public void withinRangeRejectsPairWhoseLengthIsNotTwo() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        // One "pair" that is not actually a min/max pair: it has 6 elements, not 2.
        char[][] invalidPairs = new char[1][6];

        try {
            builder.withinRange(invalidPairs);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "Each pair must contain minimum and maximum code point"
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
