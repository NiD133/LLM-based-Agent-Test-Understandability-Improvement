package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test33 extends Validate_ESTest_scaffolding {

    // An empty array trivially satisfies the "no null elements" constraint,
    // so noNullElements should complete without throwing.
    @Test(timeout = 4000)
    public void test_noNullElements_emptyArray_doesNotThrow() throws Throwable {
        Object[] emptyArray = new Object[0];

        Validate.noNullElements(emptyArray);

        assertEquals(0, emptyArray.length);
    }
}
