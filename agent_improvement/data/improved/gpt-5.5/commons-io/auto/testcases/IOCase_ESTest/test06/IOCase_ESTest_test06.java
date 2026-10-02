package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test06 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        IOCase caseInsensitiveComparison = IOCase.INSENSITIVE;
        String text = " without breaking the first codepoint or grapheme cluster";
        int startIndex = 15;
        String searchText = "!8-T";

        int index = caseInsensitiveComparison.checkIndexOf(text, startIndex, searchText);

        assertEquals(-1, index);
    }
}
