package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test17 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that configuring a builder with accumulation enabled and an empty
     * character selection completes without altering the default minimum code point.
     */
    @Test(timeout = 4000)
    public void selectFromEmptyArrayKeepsDefaultMinimumCodePoint() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
        char[] noSelectedChars = new char[0];

        builder.setAccumulate(true);
        RandomStringGenerator.Builder returnedBuilder = builder.selectFrom(noSelectedChars);

        assertEquals(0, RandomStringGenerator.Builder.DEFAULT_MINIMUM_CODE_POINT);
    }
}
