package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test04 extends Options_ESTest_scaffolding {

    /**
     * A freshly constructed Options holds no long options, so hasLongOption
     * should report false for any name.
     */
    @Test(timeout = 4000)
    public void hasLongOptionReturnsFalseWhenNoOptionsRegistered() throws Throwable {
        Options emptyOptions = new Options();

        boolean hasLongOption = emptyOptions.hasLongOption("T");

        assertFalse(hasLongOption);
    }
}
