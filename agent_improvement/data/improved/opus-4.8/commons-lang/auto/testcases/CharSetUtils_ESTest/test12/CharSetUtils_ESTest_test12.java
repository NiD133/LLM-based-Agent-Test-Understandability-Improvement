package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test12 extends CharSetUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link CharSetUtils#containsAny(String, String...)} returns
     * {@code false} when none of the characters in the search string occur in
     * the supplied character set.
     *
     * <p>Here the search string is {@code "<"} and the only non-null entry in
     * the set is the text {@code "org.apache.commons.lang3.CharSetUtils"},
     * which does not contain a {@code '<'} character, so no match is found.</p>
     */
    @Test(timeout = 4000)
    public void containsAnyReturnsFalseWhenCharacterIsNotInSet() throws Throwable {
        String searchString = "<";
        String[] characterSet = new String[6];
        characterSet[0] = "org.apache.commons.lang3.CharSetUtils";

        boolean containsAny = CharSetUtils.containsAny(searchString, characterSet);

        assertFalse(containsAny);
    }
}
