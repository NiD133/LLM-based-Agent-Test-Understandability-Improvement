package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test02 extends WordUtils_ESTest_scaffolding {

    private static final String TEXT_TO_WRAP = "3^s.6";
    private static final int ZERO_WRAP_LENGTH_IS_NORMALIZED_TO_ONE = 0;
    private static final String BACKSPACE_DOT_STAR_REGEX = "\b.*";
    private static final boolean WRAP_LONG_WORDS = true;
    private static final String EXPECTED_WRAPPED_TEXT = "3\b.*^\b.*s\b.*.\b.*6";

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        String wrappedText = WordUtils.wrap(
                TEXT_TO_WRAP,
                ZERO_WRAP_LENGTH_IS_NORMALIZED_TO_ONE,
                BACKSPACE_DOT_STAR_REGEX,
                WRAP_LONG_WORDS,
                BACKSPACE_DOT_STAR_REGEX);

        assertEquals(EXPECTED_WRAPPED_TEXT, wrappedText);
    }
}
