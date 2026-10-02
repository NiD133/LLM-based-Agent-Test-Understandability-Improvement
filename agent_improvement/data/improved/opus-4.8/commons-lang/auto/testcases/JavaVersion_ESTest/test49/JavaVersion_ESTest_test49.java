package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.fail;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test49 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string such as "0T" is not one of the recognized constants, so it falls
     * through to numeric parsing. Because it cannot be parsed as a float, looking it up
     * via {@link JavaVersion#getJavaVersion(String)} fails with a NumberFormatException.
     */
    @Test(timeout = 4000)
    public void getJavaVersionWithUnparsableVersionThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("0T");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException expected) {
            // "0T" is not a valid float and cannot be mapped to a Java version.
        }
    }
}
