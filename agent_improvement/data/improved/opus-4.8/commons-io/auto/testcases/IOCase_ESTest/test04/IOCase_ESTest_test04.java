package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test04 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#checkRegionMatches(String, int, String)} returns
     * {@code false} when the search string is {@code null}, regardless of the
     * (here negative) start index. A null search argument must short-circuit to
     * {@code false} rather than throw.
     */
    @Test(timeout = 4000)
    public void checkRegionMatchesReturnsFalseWhenSearchIsNull() throws Throwable {
        IOCase sensitiveCase = IOCase.SENSITIVE;
        String text = ">oH(kNS8W#e/$";
        int negativeStartIndex = -639;
        String nullSearch = null;

        boolean matches = sensitiveCase.checkRegionMatches(text, negativeStartIndex, nullSearch);

        assertFalse(matches);
    }
}
