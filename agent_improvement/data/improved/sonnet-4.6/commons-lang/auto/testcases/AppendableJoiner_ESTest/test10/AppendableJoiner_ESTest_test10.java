package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AppendableJoiner_ESTest_test10 extends AppendableJoiner_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        StringBuilder target = new StringBuilder();
        ArrayList<StringBuilder> emptyElements = new ArrayList<StringBuilder>();

        AppendableJoiner.Builder<StringBuilder> builder = AppendableJoiner.builder();
        AppendableJoiner<StringBuilder> joiner = builder.get();

        // Joining an empty iterable into a StringBuilder should produce an empty string
        StringBuilder result = joiner.joinA(target, (Iterable<StringBuilder>) emptyElements);

        assertEquals("", result.toString());
    }
}
