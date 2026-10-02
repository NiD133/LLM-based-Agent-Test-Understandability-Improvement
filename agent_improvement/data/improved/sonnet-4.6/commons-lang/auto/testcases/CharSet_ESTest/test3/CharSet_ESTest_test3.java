package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test3 extends CharSet_ESTest_scaffolding {

    /**
     * Verifies that CharSet can be constructed from an array where most entries
     * are null (left uninitialized) and a single non-null entry contains a mix
     * of literal characters and a negated character pattern ("^0").
     *
     * The CharSet constructor must silently skip null entries and correctly
     * parse the occupied slot at index 4.
     */
    @Test(timeout = 4000)
    public void test3() throws Throwable {
        // Six-element array; indices 0-3 and 5 remain null by default.
        String[] setDefinitions = new String[6];
        // "=]w9^0fV": individual chars '=', ']', 'w', '9', 'f', 'V'
        //             plus the negated-character pattern "^0" (excludes '0').
        setDefinitions[4] = "=]w9^0fV";

        CharSet charSet = new CharSet(setDefinitions);
    }
}
