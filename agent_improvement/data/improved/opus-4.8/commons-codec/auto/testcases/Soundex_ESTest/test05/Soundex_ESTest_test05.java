package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test05 extends Soundex_ESTest_scaffolding {

    /**
     * Verifies that {@link Soundex#difference(String, String)} reports the number of
     * matching characters between the Soundex encodings of two strings, and that a freshly
     * constructed Soundex keeps the default maximum code length of 4.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Soundex soundex = new Soundex("org.apache.commons.codec.EncoderException");

        // difference() encodes both inputs and counts how many of the 4 Soundex
        // characters match; here exactly one position matches.
        int matchingCharacters = Soundex.US_ENGLISH_GENEALOGY.difference(
                ")1XSFmv!V?i#", "org.apache.commons.codec.EncoderException");

        assertEquals(1, matchingCharacters);
        assertEquals("default Soundex code length should be 4", 4, soundex.getMaxLength());
    }
}
