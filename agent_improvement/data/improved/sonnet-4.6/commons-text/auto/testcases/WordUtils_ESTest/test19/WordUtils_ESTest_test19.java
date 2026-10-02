package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test19 extends WordUtils_ESTest_scaffolding {

    /**
     * When a non-null, non-empty delimiters array containing only null chars ('\0') is supplied,
     * and the input string contains no '\0' characters, the method treats the entire string as
     * one unbroken word. Because lastWasGap starts as true, only the very first character is
     * captured as an initial — yielding "R".
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        // A 4-element char array whose elements are all '\0' (the Java default for char)
        char[] nullCharDelimiters = new char[4];

        // No '\0' in the input, so no delimiter is encountered after position 0;
        // only the leading 'R' is recorded as an initial.
        String initials = WordUtils.initials("R'G4}z<EYofF'C", nullCharDelimiters);

        assertEquals("R", initials);
    }
}
