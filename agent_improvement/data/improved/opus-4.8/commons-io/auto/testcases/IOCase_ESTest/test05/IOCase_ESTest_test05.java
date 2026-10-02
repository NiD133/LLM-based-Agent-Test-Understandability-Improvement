package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test05 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that checkRegionMatches returns false when both the source and
     * search strings are null, regardless of the start index. The null inputs
     * short-circuit the comparison before the (out-of-range) index is ever used.
     */
    @Test(timeout = 4000)
    public void checkRegionMatchesWithNullStringsReturnsFalse() throws Throwable {
        IOCase insensitiveCase = IOCase.INSENSITIVE;

        boolean matches = insensitiveCase.checkRegionMatches((String) null, -2368, (String) null);

        assertFalse(matches);
    }
}
