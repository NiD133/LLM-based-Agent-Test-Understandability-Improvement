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

    private static final String MALFORMED_VERSION = "1V";
    private static final String EXPECTED_NUMBER_FORMAT_EXCEPTION = "Expecting exception: NumberFormatException";

    @Test(timeout = 4000)
    public void rejectsMalformedVersionWithNonNumericMinorPart() throws Throwable {
        try {
            JavaVersion.getJavaVersion(MALFORMED_VERSION);
            fail(EXPECTED_NUMBER_FORMAT_EXCEPTION);
        } catch (NumberFormatException expected) {
            // Expected for this malformed version string.
        }
    }
}
