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
public class AppendableJoiner_ESTest_test00 extends AppendableJoiner_ESTest_scaffolding {

    /**
     * Verifies that a Builder configured with an empty delimiter successfully builds
     * a non-null AppendableJoiner instance.
     */
    @Test(timeout = 4000)
    public void test00_builderWithEmptyDelimiterProducesNonNullJoiner() throws Throwable {
        AppendableJoiner.Builder<StringBuilder> builder = new AppendableJoiner.Builder<StringBuilder>();
        builder.setDelimiter("");
        AppendableJoiner<StringBuilder> joiner = builder.get();
        assertNotNull(joiner);
    }
}
