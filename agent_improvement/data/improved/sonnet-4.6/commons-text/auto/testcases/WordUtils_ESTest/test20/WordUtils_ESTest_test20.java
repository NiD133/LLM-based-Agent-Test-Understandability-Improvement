package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test20 extends WordUtils_ESTest_scaffolding {

    /**
     * An empty delimiter array means no characters act as word boundaries,
     * so WordUtils.initials() cannot identify any word starts and must return "".
     */
    @Test(timeout = 4000)
    public void test_initials_emptyDelimiterArray_returnsEmptyString() throws Throwable {
        char[] noDelimiters = new char[0];
        String input = "org.apache.commons.lang3.Strings$CiStrings";

        String result = WordUtils.initials(input, noDelimiters);

        assertEquals("", result);
    }
}
