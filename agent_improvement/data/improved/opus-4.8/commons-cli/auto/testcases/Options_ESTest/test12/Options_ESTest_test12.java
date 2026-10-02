package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test12 extends Options_ESTest_scaffolding {

    /**
     * Verifies that {@link Options#addOptionGroup(OptionGroup)} returns the same
     * {@link Options} instance it was called on, enabling a fluent call chain.
     */
    @Test(timeout = 4000)
    public void addOptionGroupReturnsSameOptionsInstance() throws Throwable {
        Options options = new Options();
        OptionGroup emptyGroup = new OptionGroup();

        Options returnedOptions = options.addOptionGroup(emptyGroup);

        assertSame(options, returnedOptions);
    }
}
