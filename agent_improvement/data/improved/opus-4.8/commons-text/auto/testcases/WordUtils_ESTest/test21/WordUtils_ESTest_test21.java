package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test21 extends WordUtils_ESTest_scaffolding {

    /**
     * When {@link WordUtils#capitalize(String, char...)} is given an empty
     * delimiter array, no characters act as word separators. As a result only
     * the very first character of the string is capitalized and the rest of the
     * text is left unchanged.
     */
    @Test(timeout = 4000)
    public void capitalizeWithEmptyDelimitersCapitalizesOnlyFirstCharacter() throws Throwable {
        char[] noDelimiters = new char[0];

        String result = WordUtils.capitalize("upper value is less than lower value", noDelimiters);

        assertEquals("Upper value is less than lower value", result);
    }
}
