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

    private static final String TEXT_SHORTER_THAN_WRAP_LENGTH = "?*!2g,cNA6z2~8(~C,";
    private static final int WRAP_LENGTH_LARGER_THAN_TEXT = 259;
    private static final String CUSTOM_NEW_LINE = "*|";
    private static final boolean WRAP_LONG_WORDS = true;

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        String wrappedText = WordUtils.wrap(
                TEXT_SHORTER_THAN_WRAP_LENGTH,
                WRAP_LENGTH_LARGER_THAN_TEXT,
                CUSTOM_NEW_LINE,
                WRAP_LONG_WORDS);

        assertEquals(TEXT_SHORTER_THAN_WRAP_LENGTH, wrappedText);
    }
}
