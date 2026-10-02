package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test11 extends CharSetUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link CharSetUtils#count(String, String...)} counts how many
     * characters of the input string belong to the supplied character set.
     *
     * <p>Here the set is built from the same string as the input. The set is
     * interpreted with set-syntax (see {@link CharSet}), so the 13-character
     * input yields a count of 12.</p>
     */
    @Test(timeout = 4000)
    public void testCountCharactersInSetBuiltFromSameString() throws Throwable {
        String input = "!NIzU+h g./^6";

        // The set array holds the input string in its first slot; the remaining
        // null entries are ignored by count().
        String[] set = new String[4];
        set[0] = input;

        int matchingCharCount = CharSetUtils.count(input, set);

        assertEquals(12, matchingCharCount);
    }
}
