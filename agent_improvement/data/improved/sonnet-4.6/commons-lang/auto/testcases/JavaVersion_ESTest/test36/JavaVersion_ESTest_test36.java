package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test36 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that a version string using a comma separator followed by a non-numeric
     * suffix (e.g. "1,p") causes a NumberFormatException.
     *
     * Internally, getJavaVersion falls through to the default branch of its switch
     * statement and attempts Float.parseFloat on the substring after the comma ("p"),
     * which is not a valid floating-point literal, hence the exception.
     */
    @Test(timeout = 4000)
    public void test_getJavaVersion_commaWithNonNumericSuffix_throwsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("1,p");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException e) {
            // "p" is not a valid float literal; Float.parseFloat throws as expected
        }
    }
}
