package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test05 extends Options_ESTest_scaffolding {

    /**
     * Looking up an option that was never added should return {@code null}.
     */
    @Test(timeout = 4000)
    public void getOption_returnsNull_whenOptionNotRegistered() throws Throwable {
        Options emptyOptions = new Options();

        Option result = emptyOptions.getOption("v");

        assertNull(result);
    }
}
