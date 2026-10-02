package org.apache.commons.lang3;

import static org.junit.Assert.fail;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test29 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string that is not numeric (here "/jo") cannot be matched to any known
     * Java version. {@link JavaVersion#get(String)} then falls through to its numeric
     * parsing branch and attempts {@code Float.parseFloat("/jo")}, which fails with a
     * {@link NumberFormatException}.
     */
    @Test(timeout = 4000)
    public void getWithNonNumericVersionStringThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.get("/jo");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException expected) {
            // expected: "/jo" cannot be parsed as a float
        }
    }
}
