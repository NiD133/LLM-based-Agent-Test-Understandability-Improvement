package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test17 extends Options_ESTest_scaffolding {

    /**
     * Verifies that addOption(opt, hasArg, description) returns the same Options
     * instance it was called on, enabling a fluent (chained) builder style.
     * Here a null short option name with no argument is added.
     */
    @Test(timeout = 4000)
    public void addOption_returnsSameInstanceForChaining() throws Throwable {
        Options options = new Options();

        Options returnedOptions = options.addOption((String) null, false, "NO_ARGS_ALLOWED");

        assertSame(options, returnedOptions);
    }
}
