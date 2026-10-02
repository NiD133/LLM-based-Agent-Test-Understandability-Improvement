package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test46 extends JavaVersion_ESTest_scaffolding {

    /**
     * JavaVersion.get() falls through to its default branch for unrecognised strings.
     * That branch calls Float.parseFloat() on a substring of the input to look for
     * a decimal component.  When the input is "/v" (no recognised version format and
     * no parseable numeric characters), Float.parseFloat throws NumberFormatException.
     */
    @Test(timeout = 4000)
    public void test46() throws Throwable {
        try {
            JavaVersion.get("/v");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException e) {
            // Expected: "/v" contains no parseable float, so Float.parseFloat throws
        }
    }
}
