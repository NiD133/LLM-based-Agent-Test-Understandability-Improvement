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
     * Builder.setSuffix returns the same builder instance, enabling a fluent
     * call chain. Here we verify the returned builder is non-null.
     */
    @Test(timeout = 4000)
    public void setSuffixReturnsBuilder() throws Throwable {
        StringBuilder suffix = new StringBuilder();
        AppendableJoiner.Builder<StringBuilder> builder = new AppendableJoiner.Builder<StringBuilder>();

        AppendableJoiner.Builder<StringBuilder> returnedBuilder = builder.setSuffix(suffix);

        assertNotNull(returnedBuilder);
    }
}
