package org.apache.commons.lang3;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test36 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string whose fractional part is not numeric (here "p" after the
     * comma separator) cannot be parsed as a float. {@code getJavaVersion} should
     * therefore propagate a {@link NumberFormatException} rather than returning a value.
     */
    @Test(timeout = 4000)
    public void getJavaVersion_withNonNumericFraction_throwsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("1,p");
            fail("Expected a NumberFormatException for the non-numeric version string \"1,p\"");
        } catch (NumberFormatException expected) {
            // Expected: the fractional part "p" cannot be parsed as a float.
        }
    }
}
