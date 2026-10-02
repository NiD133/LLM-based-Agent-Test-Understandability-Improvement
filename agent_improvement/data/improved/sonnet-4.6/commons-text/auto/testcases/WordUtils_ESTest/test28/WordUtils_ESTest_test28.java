package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test28 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_capitalizeFullyWithNullCharDelimiters_lowercasesEntireString() throws Throwable {
        // Delimiters are two null characters ('\0', '\0'); no character in the input matches them,
        // so the method lowercases everything and only the very first character could be capitalized —
        // but ';' has no uppercase form, leaving the whole string lowercased.
        char[] nullCharDelimiters = new char[2];
        String result = WordUtils.capitalizeFully(";)(5b_Sh4o|A8@", nullCharDelimiters);
        assertEquals(";)(5b_sh4o|a8@", result);
    }
}
