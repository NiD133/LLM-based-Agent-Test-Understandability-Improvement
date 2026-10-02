package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test16 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that {@link JavaVersion#get(String)} rejects a malformed version
     * string. "0X" is not one of the recognized version constants, so it falls
     * through to numeric parsing, which fails and throws a NumberFormatException.
     */
    @Test(timeout = 4000)
    public void getWithMalformedVersionStringThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.get("0X");
            fail("Expected a NumberFormatException for the malformed version string \"0X\"");
        } catch (NumberFormatException expected) {
            // expected: "0X" cannot be parsed as a numeric Java version
        }
    }
}
