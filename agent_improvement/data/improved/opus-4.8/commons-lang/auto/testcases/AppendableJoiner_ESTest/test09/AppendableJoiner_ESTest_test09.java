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
public class AppendableJoiner_ESTest_test09 extends AppendableJoiner_ESTest_scaffolding {

    /**
     * Joining a {@code null} element array into an empty StringBuilder should leave
     * the target unchanged: with no prefix, suffix, or elements configured, nothing
     * is appended and the result is the empty string.
     */
    @Test(timeout = 4000)
    public void joinNullArrayLeavesTargetEmpty() throws Throwable {
        AppendableJoiner<StringBuilder> joiner = new AppendableJoiner.Builder<StringBuilder>().get();
        StringBuilder target = new StringBuilder();

        StringBuilder result = joiner.join(target, (StringBuilder[]) null);

        assertEquals("", result.toString());
    }
}
