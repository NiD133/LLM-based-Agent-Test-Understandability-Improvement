package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test50 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that JavaVersion.get() throws NumberFormatException when given a
     * version string that contains a non-numeric character (e.g. "0S").
     *
     * Internally, "0S" falls through to the default switch branch, where the code
     * attempts Float.parseFloat on a substring of the input.  The letter 'S' makes
     * the substring unparseable, so Float.parseFloat raises NumberFormatException.
     */
    @Test(timeout = 4000)
    public void test_get_withNonNumericVersionString_throwsNumberFormatException() throws Throwable {
        try {
            JavaVersion.get("0S");
            fail("Expected NumberFormatException for non-numeric version string \"0S\"");
        } catch (NumberFormatException e) {
            // expected: "0S" cannot be parsed as a float, so NumberFormatException is thrown
        }
    }
}
