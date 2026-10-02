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
    public void test06_checkIndexOf_returnsMinusOne_whenSearchStringNotFound() throws Throwable {
        IOCase caseInsensitive = IOCase.INSENSITIVE;
        String text = " without breaking the first codepoint or grapheme cluster";
        int startIndex = 15;
        String searchPattern = "!8-T";

        int foundIndex = caseInsensitive.checkIndexOf(text, startIndex, searchPattern);

        assertEquals(-1, foundIndex);
    }
}
