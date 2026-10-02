package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test9 extends CharSet_ESTest_scaffolding {

    /**
     * A String array of length 1 whose sole element is null (the default value for
     * uninitialized String references).  CharSet.getInstance() treats a single null
     * entry the same as no pattern at all, so it returns the shared EMPTY instance
     * whose character-range set has size zero.
     */
    @Test(timeout = 4000)
    public void test_getInstanceWithSingleNullEntry_returnsEmptyCharRangeSet() throws Throwable {
        // A one-element array whose only entry is null — equivalent to passing null as a pattern
        String[] setStrsWithNullEntry = new String[1];

        // getInstance resolves null → CharSet.EMPTY (via the COMMON cache)
        CharSet charSet = CharSet.getInstance(setStrsWithNullEntry);

        // EMPTY defines no character ranges, so the backing set must be empty
        Set<CharRange> charRanges = charSet.getCharRanges();
        assertEquals(0, charRanges.size());
    }
}
