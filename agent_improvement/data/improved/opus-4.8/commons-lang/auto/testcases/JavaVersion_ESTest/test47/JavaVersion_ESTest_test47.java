package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.fail;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test47 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string that is not a recognised constant and is not numeric
     * (here {@code "/u"}) falls through to JavaVersion.get's parsing branch,
     * which ultimately calls Float.parseFloat on the non-numeric text and
     * throws a NumberFormatException.
     */
    @Test(timeout = 4000)
    public void getWithNonNumericVersionStringThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.get("/u");
            fail("Expected a NumberFormatException for non-numeric version string \"/u\"");
        } catch (NumberFormatException expected) {
            // Expected: "/u" cannot be parsed as a float version number.
        }
    }
}
