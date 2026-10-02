package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test59 extends JavaVersion_ESTest_scaffolding {

    /**
     * "1V" has no '.' separator, so the internal parser attempts Float.parseFloat on a
     * substring that contains 'V', which is not a valid floating-point literal.
     */
    @Test(timeout = 4000, expected = NumberFormatException.class)
    public void testGetJavaVersion_malformedVersionStringWithNonNumericCharacter_throwsNumberFormatException() {
        JavaVersion.getJavaVersion("1V");
    }
}
