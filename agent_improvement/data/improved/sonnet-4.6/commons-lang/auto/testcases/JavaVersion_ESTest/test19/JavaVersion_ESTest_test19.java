package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test19 extends JavaVersion_ESTest_scaffolding {

    /**
     * "0U" is not a valid version string: it has no '.' separator, so the code
     * falls through to Float.parseFloat("0U"), which throws NumberFormatException.
     */
    @Test(timeout = 4000, expected = NumberFormatException.class)
    public void getJavaVersion_withNonNumericSuffix_throwsNumberFormatException() {
        JavaVersion.getJavaVersion("0U");
    }
}
