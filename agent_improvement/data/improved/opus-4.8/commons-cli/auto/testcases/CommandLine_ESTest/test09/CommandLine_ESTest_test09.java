package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test09 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that querying an empty CommandLine for a {@code null} OptionGroup
     * reports the group as not set, returning {@code false}.
     */
    @Test(timeout = 4000)
    public void hasOption_withNullOptionGroup_returnsFalse() throws Throwable {
        CommandLine emptyCommandLine = CommandLine.builder().get();

        boolean hasNullGroup = emptyCommandLine.hasOption((OptionGroup) null);

        assertFalse(hasNullGroup);
    }
}
