package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test15 extends RandomStringGenerator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void withinRange_withNullPairs_returnsSameBuilderInstance() throws Throwable {
        RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();

        // withinRange(null) should be a no-op and return the same builder for chaining
        RandomStringGenerator.Builder returnedBuilder = builder.withinRange((char[][]) null);

        assertSame(builder, returnedBuilder);
    }
}
