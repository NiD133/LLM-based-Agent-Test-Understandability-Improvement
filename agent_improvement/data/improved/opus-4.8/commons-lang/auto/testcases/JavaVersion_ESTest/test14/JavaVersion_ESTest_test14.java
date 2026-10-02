package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.fail;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test14 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that {@link JavaVersion#getJavaVersion(String)} throws a
     * NumberFormatException when given a version string that is not a parsable
     * number. Here "1P" cannot be parsed as a float, so the internal parsing
     * fails with a NumberFormatException.
     */
    @Test(timeout = 4000)
    public void getJavaVersion_withNonNumericString_throwsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("1P");
            fail("Expected a NumberFormatException for the unparsable version string \"1P\"");
        } catch (NumberFormatException expected) {
            // expected: "1P" is not a valid numeric Java version
        }
    }
}
