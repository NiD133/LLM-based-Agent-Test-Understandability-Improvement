package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test35 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string like "1-R" looks like a major version "1" followed by a
     * non-numeric release tag ("-R"). The internal parser reaches Float.parseFloat
     * on the non-numeric portion and throws NumberFormatException.
     */
    @Test(timeout = 4000)
    public void test_getJavaVersion_withNonNumericReleaseSuffix_throwsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("1-R");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException e) {
            // expected: "1-R" cannot be parsed as a float version number
        }
    }
}
