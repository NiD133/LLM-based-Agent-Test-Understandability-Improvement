package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test0 extends CharSet_ESTest_scaffolding {

    /**
     * Two CharSets built from different definitions are not equal.
     *
     * <p>{@code ASCII_ALPHA_LOWER} is defined by the range "a-z", while the set
     * created here is built from an array of {@code null} definition strings,
     * which contribute no character ranges. Since {@link CharSet#equals(Object)}
     * compares the underlying ranges, the two sets are unequal in both
     * directions.</p>
     */
    @Test(timeout = 4000)
    public void equalsReturnsFalseForCharSetsWithDifferentRanges() throws Throwable {
        CharSet lowerCaseLetters = CharSet.ASCII_ALPHA_LOWER;

        // An array of two null definition strings yields a CharSet with no ranges.
        String[] nullDefinitions = new String[2];
        CharSet emptyCharSet = CharSet.getInstance(nullDefinitions);

        assertFalse(lowerCaseLetters.equals(emptyCharSet));
        assertFalse(emptyCharSet.equals((Object) lowerCaseLetters));
    }
}
