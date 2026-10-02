package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AppendableJoiner_ESTest_test08 extends AppendableJoiner_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08_joinEmptyIterableProducesEmptyString() throws Throwable {
        // Build a joiner with all-default settings (no prefix, suffix, or delimiter)
        AppendableJoiner<StringBuilder> joiner = new AppendableJoiner.Builder<StringBuilder>().get();

        StringBuilder target = new StringBuilder();
        HashSet<StringBuilder> emptyElements = new HashSet<StringBuilder>();

        // Joining an empty collection should leave the target unchanged
        StringBuilder result = joiner.join(target, (Iterable<StringBuilder>) emptyElements);

        assertEquals("", result.toString());
    }
}
