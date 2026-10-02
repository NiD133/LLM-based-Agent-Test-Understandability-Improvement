package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test11 extends CharRange_ESTest_scaffolding {

    /**
     * A negated range "everything except space" should not be reported as
     * containing the plain range "[space, space]", since the space character
     * is exactly what the negated range excludes.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        final char space = ' ';

        CharRange everythingExceptSpace = CharRange.isNot(space);
        CharRange onlySpace = CharRange.isIn(space, space);

        boolean negatedRangeContainsSpace = everythingExceptSpace.contains(onlySpace);

        assertFalse(negatedRangeContainsSpace);

        // Both ranges keep the endpoints they were built from.
        assertEquals(space, everythingExceptSpace.getStart());
        assertEquals(space, everythingExceptSpace.getEnd());
        assertEquals(space, onlySpace.getStart());
        assertEquals(space, onlySpace.getEnd());
    }
}
