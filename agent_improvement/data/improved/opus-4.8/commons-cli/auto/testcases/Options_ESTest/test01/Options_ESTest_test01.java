package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test01 extends Options_ESTest_scaffolding {

    /**
     * A freshly created Options has no short options registered, so
     * hasShortOption returns false for any name.
     */
    @Test(timeout = 4000)
    public void hasShortOptionReturnsFalseWhenNoOptionsAdded() throws Throwable {
        Options emptyOptions = new Options();

        boolean isShortOptionPresent = emptyOptions.hasShortOption("j");

        assertFalse(isShortOptionPresent);
    }
}
