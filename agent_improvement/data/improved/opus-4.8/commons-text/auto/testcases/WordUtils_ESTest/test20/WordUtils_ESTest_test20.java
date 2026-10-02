package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test20 extends WordUtils_ESTest_scaffolding {

    /**
     * When an empty delimiter array is supplied, {@code initials} treats it as
     * "no characters separate words" and returns an empty String, regardless of
     * the input text (see the {@code WordUtils.initials(*, new char[0]) = ""} contract).
     */
    @Test(timeout = 4000)
    public void initialsWithEmptyDelimiterArrayReturnsEmptyString() throws Throwable {
        char[] noDelimiters = new char[0];

        String initials = WordUtils.initials("org.apache.commons.lang3.Strings$CiStrings", noDelimiters);

        assertEquals("", initials);
    }
}
