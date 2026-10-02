package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test15 extends WordUtils_ESTest_scaffolding {

    /**
     * isDelimiter(char, char[]) returns true when the character is present in
     * the delimiters array.
     */
    @Test(timeout = 4000)
    public void isDelimiterReturnsTrueWhenCharIsInDelimiterArray() throws Throwable {
        char[] delimiters = new char[] {'G', '\0'};

        boolean isDelimiter = WordUtils.isDelimiter('G', delimiters);

        assertTrue(isDelimiter);
    }
}
