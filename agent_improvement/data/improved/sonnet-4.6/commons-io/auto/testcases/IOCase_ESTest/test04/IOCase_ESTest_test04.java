package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test04 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // checkRegionMatches returns false when the search string is null,
        // regardless of the source string or start index
        boolean regionMatches = IOCase.SENSITIVE.checkRegionMatches(">oH(kNS8W#e/$", -639, (String) null);
        assertFalse(regionMatches);
    }
}
