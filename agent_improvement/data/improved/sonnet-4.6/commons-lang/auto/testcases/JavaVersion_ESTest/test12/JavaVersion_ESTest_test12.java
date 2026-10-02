package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test12 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string like "1R" is not a recognised Java version and contains a
     * non-numeric character after the major-version digit.  The internal parsing
     * logic eventually calls Float.parseFloat on the non-numeric suffix, which
     * throws NumberFormatException.
     */
    @Test(timeout = 4000)
    public void test_getJavaVersion_throwsNumberFormatException_whenVersionStringContainsNonNumericSuffix() throws Throwable {
        try {
            JavaVersion.getJavaVersion("1R");
            fail("Expected NumberFormatException for version string \"1R\" containing a non-numeric character");
        } catch (NumberFormatException e) {
            // expected: Float.parseFloat cannot parse the non-numeric suffix "R"
        }
    }
}
