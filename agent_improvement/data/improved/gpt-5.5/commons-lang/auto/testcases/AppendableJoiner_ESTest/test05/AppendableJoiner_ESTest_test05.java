package org.apache.commons.lang3;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.assertNotNull;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AppendableJoiner_ESTest_test05 extends AppendableJoiner_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        StringBuilder suffix = new StringBuilder();
        AppendableJoiner.Builder<StringBuilder> builder = new AppendableJoiner.Builder<StringBuilder>();

        AppendableJoiner.Builder<StringBuilder> returnedBuilder = builder.setSuffix(suffix);

        assertNotNull(returnedBuilder);
    }
}
