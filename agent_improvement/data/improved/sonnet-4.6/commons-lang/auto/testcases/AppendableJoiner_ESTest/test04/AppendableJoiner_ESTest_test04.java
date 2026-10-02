package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AppendableJoiner_ESTest_test04 extends AppendableJoiner_ESTest_scaffolding {

    /**
     * Verifies that {@link AppendableJoiner.Builder#setDelimiter(CharSequence)} returns
     * the same builder instance, supporting the fluent builder pattern.
     */
    @Test(timeout = 4000)
    public void test04_setDelimiter_returnsSameBuilderInstance() throws Throwable {
        AppendableJoiner.Builder<Object> builder = AppendableJoiner.builder();
        StringBuilder delimiter = new StringBuilder(457);

        AppendableJoiner.Builder<Object> builderAfterSetDelimiter = builder.setDelimiter(delimiter);

        assertSame(builderAfterSetDelimiter, builder);
    }
}
