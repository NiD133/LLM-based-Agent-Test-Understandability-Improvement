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
     * A CharSet built from an array of null strings contains no characters,
     * so it must not equal the predefined ASCII_ALPHA_LOWER ("a-z") set.
     * The test also verifies that equals() is symmetric in both directions.
     */
    @Test(timeout = 4000)
    public void test_asciiAlphaLowerIsNotEqualToCharSetBuiltFromNullStrings() throws Throwable {
        CharSet asciiLower = CharSet.ASCII_ALPHA_LOWER;

        // A two-element array whose entries are both null; CharSet treats each
        // null entry as "no characters", so this produces an empty CharSet.
        String[] nullStrings = new String[2];
        CharSet emptyCharSet = CharSet.getInstance(nullStrings);

        boolean asciiLowerEqualsEmpty = asciiLower.equals(emptyCharSet);

        assertFalse("Empty CharSet should not equal ASCII_ALPHA_LOWER",
                emptyCharSet.equals((Object) asciiLower));
        assertFalse("ASCII_ALPHA_LOWER should not equal an empty CharSet",
                asciiLowerEqualsEmpty);
    }
}
