package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test10 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        final IOCase caseInsensitive = IOCase.INSENSITIVE;
        final String text = null;
        final int startIndex = 746;
        final String searchText = null;

        final int matchIndex = caseInsensitive.checkIndexOf(text, startIndex, searchText);

        assertEquals(-1, matchIndex);
    }
}
