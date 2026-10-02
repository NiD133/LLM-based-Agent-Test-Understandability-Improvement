package org.apache.commons.lang3;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AppendableJoiner_ESTest_test00 extends AppendableJoiner_ESTest_scaffolding {

    /**
     * Verifies that a Builder with an empty delimiter still produces a usable
     * (non-null) AppendableJoiner instance via {@code get()}.
     */
    @Test(timeout = 4000)
    public void buildJoinerWithEmptyDelimiterReturnsNonNullInstance() throws Throwable {
        AppendableJoiner.Builder<StringBuilder> builder = new AppendableJoiner.Builder<StringBuilder>();
        builder.setDelimiter("");

        AppendableJoiner<StringBuilder> joiner = builder.get();

        assertNotNull(joiner);
    }
}
