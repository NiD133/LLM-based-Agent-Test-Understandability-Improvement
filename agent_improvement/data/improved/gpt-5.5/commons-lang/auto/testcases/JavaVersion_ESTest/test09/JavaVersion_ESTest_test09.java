package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test09 extends JavaVersion_ESTest_scaffolding {

    private static final String MALFORMED_VERSION = "1U";
    private static final String EXPECTED_EXCEPTION_MESSAGE = "Expecting exception: NumberFormatException";

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        try {
            JavaVersion.get(MALFORMED_VERSION);
            fail(EXPECTED_EXCEPTION_MESSAGE);
        } catch (NumberFormatException expected) {
            // Expected: the non-numeric suffix cannot be parsed as a Java version.
        }
    }
}
