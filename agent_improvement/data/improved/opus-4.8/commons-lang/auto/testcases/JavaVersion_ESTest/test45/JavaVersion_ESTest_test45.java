package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test45 extends JavaVersion_ESTest_scaffolding {

    /**
     * The version string "1O" (digit one followed by the letter O) is not a known
     * constant, so {@link JavaVersion#getJavaVersion(String)} falls through to parsing
     * it as a float. Parsing the non-numeric text throws a {@link NumberFormatException}.
     */
    @Test(timeout = 4000)
    public void getJavaVersionWithNonNumericStringThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("1O");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException expected) {
            // "1O" cannot be parsed as a float version number.
        }
    }
}
