package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test10 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string whose numeric prefix ("0") causes the internal parser to attempt
     * Float.parseFloat on the entire input ("0s"), which throws NumberFormatException
     * because "s" is not a valid floating-point character.
     */
    @Test(timeout = 4000)
    public void test_getJavaVersion_withNonNumericSuffix_throwsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("0s");
            fail("Expected NumberFormatException for version string containing non-numeric characters");
        } catch (NumberFormatException e) {
            // "0s" triggers Float.parseFloat internally, which cannot parse the 's' suffix
        }
    }
}
