package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.fail;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test35 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string such as "1-R" looks like a low (pre-2) version, so
     * {@link JavaVersion#getJavaVersion(String)} tries to parse its decimal part
     * ("R") as a float. That parse fails and surfaces as a NumberFormatException.
     */
    @Test(timeout = 4000)
    public void getJavaVersion_withNonNumericDecimalPart_throwsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("1-R");
            fail("Expected a NumberFormatException for the non-numeric version string \"1-R\"");
        } catch (NumberFormatException expected) {
            // Parsing the "R" decimal part as a float is what raises this exception.
        }
    }
}
