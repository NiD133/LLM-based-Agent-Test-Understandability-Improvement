package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test15 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Passing a {@code null} char-pair array to {@link RandomStringGenerator.Builder#withinRange(char[][])}
     * should be a no-op that still returns the same builder, enabling fluent method chaining.
     */
    @Test(timeout = 4000)
    public void withinRangeWithNullPairsReturnsSameBuilderForChaining() throws Throwable {
        RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();

        RandomStringGenerator.Builder returnedBuilder = builder.withinRange((char[][]) null);

        assertSame(builder, returnedBuilder);
    }
}
