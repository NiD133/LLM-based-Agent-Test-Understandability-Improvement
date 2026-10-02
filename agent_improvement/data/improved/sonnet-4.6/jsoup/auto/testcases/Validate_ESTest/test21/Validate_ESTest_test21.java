package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test21 extends Validate_ESTest_scaffolding {

    // expectNotNull should return the same non-null object it receives
    @Test(timeout = 4000)
    public void test_expectNotNull_withNonNullString_returnsSameObject() throws Throwable {
        String nonNullInput = "di";
        Object result = Validate.expectNotNull((Object) nonNullInput);
        assertEquals(nonNullInput, result);
    }
}
