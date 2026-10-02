package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test05 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05_wrapLengthExceedsInputLength_returnsInputUnchanged() throws Throwable {
        String input = "?*!2g,cNA6z2~8(~C,";
        // wrapLength (259) is much larger than the input length (18), so no wrapping should occur
        int wrapLength = 259;
        String newLineStr = "*|";
        boolean wrapLongWords = true;

        String result = WordUtils.wrap(input, wrapLength, newLineStr, wrapLongWords);

        assertEquals(input, result);
    }
}
