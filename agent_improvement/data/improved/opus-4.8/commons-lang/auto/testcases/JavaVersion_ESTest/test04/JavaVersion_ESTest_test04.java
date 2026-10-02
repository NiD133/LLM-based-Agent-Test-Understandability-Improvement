package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.fail;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test04 extends JavaVersion_ESTest_scaffolding {

    /**
     * A non-numeric version string is not a known constant, so {@link JavaVersion#get(String)}
     * falls through to parsing it as a float. Parsing fails and a
     * {@link NumberFormatException} is thrown.
     */
    @Test(timeout = 4000)
    public void getWithNonNumericVersionStringThrowsNumberFormatException() throws Throwable {
        String nonNumericVersion = "Array cannot be empty.";

        try {
            JavaVersion.get(nonNumericVersion);
            fail("Expected a NumberFormatException for a non-numeric version string");
        } catch (NumberFormatException expected) {
            // The version string cannot be parsed into a float, as expected.
        }
    }
}
