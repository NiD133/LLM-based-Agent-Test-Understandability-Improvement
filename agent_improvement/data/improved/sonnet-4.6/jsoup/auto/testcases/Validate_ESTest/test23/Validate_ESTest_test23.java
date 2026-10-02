package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test23 extends Validate_ESTest_scaffolding {

    // ensureNotNull with a non-null object and null varargs should return the original object unchanged
    @Test(timeout = 4000)
    public void test_ensureNotNull_withNonNullObject_andNullArgs_returnsOriginalObject() throws Throwable {
        String nonNullInput = ".;(s<;hD";
        Object result = Validate.ensureNotNull((Object) nonNullInput, nonNullInput, (Object[]) null);
        assertEquals(nonNullInput, result);
    }
}
