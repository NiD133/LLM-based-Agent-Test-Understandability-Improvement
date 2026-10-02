package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test50 extends StringUtil_ESTest_scaffolding {

    /**
     * Joining a String array (one empty element followed by three nulls) with a
     * separator should produce a non-null result.
     */
    @Test(timeout = 4000)
    public void joinArrayWithEmptyAndNullElementsReturnsNonNull() throws Throwable {
        String[] parts = new String[4];
        parts[0] = "";
        // parts[1], parts[2], parts[3] remain null

        String joined = StringUtil.join(parts, "?Must be true");

        assertNotNull(joined);
    }
}
