package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AppendableJoiner_ESTest_test10 extends AppendableJoiner_ESTest_scaffolding {

    /**
     * Joining an empty Iterable into an empty StringBuilder (using a default
     * joiner with no prefix, suffix or delimiter) should leave the target empty.
     */
    @Test(timeout = 4000)
    public void joinA_emptyIterable_intoEmptyStringBuilder_appendsNothing() throws Throwable {
        StringBuilder target = new StringBuilder();
        ArrayList<StringBuilder> noElements = new ArrayList<StringBuilder>();

        AppendableJoiner<StringBuilder> joiner = AppendableJoiner.<StringBuilder>builder().get();
        StringBuilder result = joiner.joinA(target, (Iterable<StringBuilder>) noElements);

        assertEquals("", result.toString());
    }
}
