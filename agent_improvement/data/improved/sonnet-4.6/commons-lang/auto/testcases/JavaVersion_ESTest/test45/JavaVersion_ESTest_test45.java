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
     * "1O" contains the letter 'O' (not the digit '0'), which cannot be parsed
     * as a float, so getJavaVersion must propagate a NumberFormatException.
     */
    @Test(timeout = 4000)
    public void test45_getJavaVersionWithLetterOInsteadOfZeroThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("1O");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException e) {
        }
    }
}
