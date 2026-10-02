package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AppendableJoiner_ESTest_test05 extends AppendableJoiner_ESTest_scaffolding {

    /**
     * Verifies that {@link AppendableJoiner.Builder#setSuffix(CharSequence)} supports
     * method chaining by returning the same builder instance (not null).
     */
    @Test(timeout = 4000)
    public void test05_setSuffix_returnsBuilderForChaining() throws Throwable {
        StringBuilder emptySuffix = new StringBuilder();
        AppendableJoiner.Builder<StringBuilder> builder = new AppendableJoiner.Builder<StringBuilder>();

        AppendableJoiner.Builder<StringBuilder> builderAfterSetSuffix = builder.setSuffix(emptySuffix);

        assertNotNull(builderAfterSetSuffix);
    }
}
