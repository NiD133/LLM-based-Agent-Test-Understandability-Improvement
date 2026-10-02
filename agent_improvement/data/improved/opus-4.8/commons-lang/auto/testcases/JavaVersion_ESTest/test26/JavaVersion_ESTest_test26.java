package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test26 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string that contains a decimal point but whose fractional part
     * ("kS") is not numeric. {@code getJavaVersion} tries to parse the fraction
     * as a float, so it should throw a {@link NumberFormatException}.
     */
    @Test(timeout = 4000)
    public void getJavaVersionWithNonNumericDecimalThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("/kS");
            fail("Expected a NumberFormatException for an unparseable version string");
        } catch (NumberFormatException expected) {
            // expected: the fractional part "kS" cannot be parsed as a float
        }
    }
}
