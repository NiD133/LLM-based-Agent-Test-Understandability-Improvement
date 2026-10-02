package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AppendableJoiner_ESTest_test08 extends AppendableJoiner_ESTest_scaffolding {

    /**
     * Joining an empty Iterable with a default (no prefix/suffix/delimiter) joiner
     * leaves the target StringBuilder unchanged, so the result is the empty string.
     */
    @Test(timeout = 4000)
    public void joinEmptyIterableProducesEmptyString() throws Throwable {
        AppendableJoiner<StringBuilder> joiner = new AppendableJoiner.Builder<StringBuilder>().get();
        StringBuilder target = new StringBuilder();
        HashSet<StringBuilder> emptyElements = new HashSet<StringBuilder>();

        StringBuilder result = joiner.join(target, (Iterable<StringBuilder>) emptyElements);

        assertEquals("", result.toString());
    }
}
