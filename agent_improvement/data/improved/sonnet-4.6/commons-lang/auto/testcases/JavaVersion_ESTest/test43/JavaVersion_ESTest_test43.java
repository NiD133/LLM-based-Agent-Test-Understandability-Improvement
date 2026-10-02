package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test43 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that getJavaVersion throws NumberFormatException when the version string
     * contains non-numeric characters (e.g. "1Q"), because the internal parsing logic
     * eventually calls Float.parseFloat on the malformed input.
     */
    @Test(timeout = 4000)
    public void test_getJavaVersion_throwsNumberFormatException_forMalformedVersionString() throws Throwable {
        try {
            JavaVersion.getJavaVersion("1Q");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException e) {
            // "1Q" is not a valid version string; Float.parseFloat("1Q") throws NumberFormatException
        }
    }
}
