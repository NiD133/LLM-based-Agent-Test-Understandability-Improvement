package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test53 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that JavaVersion.get() throws NumberFormatException when given a
     * non-numeric, non-standard string like "/o". The input has no dot separator,
     * so toFloatVersion() returns -1 (in the "< 1" range). The fallback path then
     * attempts Float.parseFloat on a substring of the input, which fails because
     * "/o" is not a parseable number.
     */
    @Test(timeout = 4000)
    public void test_get_withNonNumericString_throwsNumberFormatException() throws Throwable {
        try {
            JavaVersion.get("/o");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException e) {
        }
    }
}
