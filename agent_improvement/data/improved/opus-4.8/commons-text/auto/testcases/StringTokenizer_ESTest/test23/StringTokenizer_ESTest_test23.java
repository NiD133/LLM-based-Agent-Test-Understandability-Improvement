package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test23 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that disabling the "ignore empty tokens" flag on a freshly
     * created tokenizer takes effect, and that no token has been read yet
     * (so previousIndex() still reports -1).
     */
    @Test(timeout = 4000)
    public void setIgnoreEmptyTokensFalse_disablesFlagAndLeavesCursorAtStart() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance("add() Ss unsup!oted");

        StringTokenizer sameTokenizer = tokenizer.setIgnoreEmptyTokens(false);

        assertFalse("empty tokens should no longer be ignored", sameTokenizer.isIgnoreEmptyTokens());
        assertEquals("no token has been read yet", -1, sameTokenizer.previousIndex());
    }
}
