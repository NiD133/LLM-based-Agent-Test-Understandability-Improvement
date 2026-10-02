package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AppendableJoiner_ESTest_test05 extends AppendableJoiner_ESTest_scaffolding {

    /**
     * Verifies that {@link AppendableJoiner.Builder#setSuffix(CharSequence)} follows the
     * fluent-builder contract by returning a (non-null) builder instance the caller can chain on.
     */
    @Test(timeout = 4000)
    public void setSuffix_returnsBuilderForChaining() throws Throwable {
        AppendableJoiner.Builder<StringBuilder> builder = new AppendableJoiner.Builder<StringBuilder>();

        StringBuilder suffix = new StringBuilder();
        AppendableJoiner.Builder<StringBuilder> returnedBuilder = builder.setSuffix(suffix);

        assertNotNull(returnedBuilder);
    }
}
