package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test25 extends JavaVersion_ESTest_scaffolding {

    private static final String VERSION_WITH_NON_NUMERIC_MINOR_PART = "1-U";

    @Test(timeout = 4000)
    public void testGetJavaVersionRejectsNonNumericMinorPart() throws Throwable {
        try {
            JavaVersion.getJavaVersion(VERSION_WITH_NON_NUMERIC_MINOR_PART);
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException e) {
        }
    }
}
