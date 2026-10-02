package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test14 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link WordUtils#isDelimiter(int, char[])} returns {@code false}
     * when the given code point does not match any character in the delimiter array.
     *
     * <p>Here the code point {@code -716} is checked against a single-element array
     * holding the default {@code '\0'} character, so no match is found.</p>
     */
    @Test(timeout = 4000)
    public void isDelimiterReturnsFalseWhenCodePointNotInDelimiters() throws Throwable {
        char[] delimiters = new char[1];
        int nonDelimiterCodePoint = -716;

        boolean isDelimiter = WordUtils.isDelimiter(nonDelimiterCodePoint, delimiters);

        assertFalse(isDelimiter);
    }
}
