package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test25 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string like "1-U" has no dot, so the parser falls through to
     * Float.parseFloat on a substring that still contains the non-numeric "-U"
     * portion, which causes NumberFormatException.
     */
    @Test(timeout = 4000)
    public void test25_getJavaVersion_withNonNumericSuffix_throwsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("1-U");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException e) {
            // expected: "1-U" cannot be parsed as a float
        }
    }
}
