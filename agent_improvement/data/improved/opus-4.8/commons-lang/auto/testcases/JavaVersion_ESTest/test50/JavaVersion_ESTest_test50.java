package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.fail;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test50 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string that is not a known constant and is not a parseable number
     * (here "0S") falls through to JavaVersion.get's default branch, where it is
     * eventually passed to Float.parseFloat. Since "0S" is not a valid float, the
     * lookup propagates a NumberFormatException rather than returning a JavaVersion.
     */
    @Test(timeout = 4000)
    public void getWithNonNumericVersionStringThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.get("0S");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException expected) {
            // expected: "0S" cannot be parsed as a float
        }
    }
}
