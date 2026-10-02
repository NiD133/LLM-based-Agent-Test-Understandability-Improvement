package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test20 extends CharRange_ESTest_scaffolding {

    /**
     * A single-character range built with {@link CharRange#is(char)} should:
     * <ul>
     *   <li>report both its start and end as that one character, and</li>
     *   <li>not contain any other character.</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void singleCharRange_excludesOtherCharsAndExposesBounds() throws Throwable {
        CharRange tildeOnly = CharRange.is('~');

        assertFalse("'U' lies outside the single-character range '~'", tildeOnly.contains('U'));
        assertEquals("range should start at '~'", '~', tildeOnly.getStart());
        assertEquals("range should end at '~'", '~', tildeOnly.getEnd());
    }
}
