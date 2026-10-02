package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test03 extends WordUtils_ESTest_scaffolding {

    private static final String SHORT_TEXT_WITH_NO_WRAP_POINT = ":9~&Gxna!";
    private static final int NEGATIVE_WRAP_LENGTH = -182;
    private static final String NULL_NEW_LINE = null;
    private static final boolean DO_NOT_WRAP_LONG_WORDS = false;

    @Test(timeout = 4000)
    public void wrapReturnsOriginalTextWhenRequestedLengthIsNegative() throws Throwable {
        String wrappedText = WordUtils.wrap(
                SHORT_TEXT_WITH_NO_WRAP_POINT,
                NEGATIVE_WRAP_LENGTH,
                NULL_NEW_LINE,
                DO_NOT_WRAP_LONG_WORDS,
                SHORT_TEXT_WITH_NO_WRAP_POINT);

        assertEquals(SHORT_TEXT_WITH_NO_WRAP_POINT, wrappedText);
    }
}
