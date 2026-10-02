package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test11 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string like "1S" is syntactically invalid: after stripping the
     * numeric prefix the remaining substring "1S" is passed to
     * Float.parseFloat, which throws NumberFormatException because 'S' is not
     * a digit or decimal-point character.
     */
    @Test(timeout = 4000)
    public void test11_getWithNonNumericVersionString_throwsNumberFormatException() throws Throwable {
        String invalidVersionWithNonNumericChar = "1S";
        try {
            JavaVersion.get(invalidVersionWithNonNumericChar);
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException e) {
            // expected: Float.parseFloat cannot handle a letter in the version token
        }
    }
}
