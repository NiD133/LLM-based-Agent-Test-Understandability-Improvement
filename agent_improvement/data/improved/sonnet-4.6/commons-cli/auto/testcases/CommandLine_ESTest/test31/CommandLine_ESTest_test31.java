package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test31 extends CommandLine_ESTest_scaffolding {

    /**
     * When an OptionGroup has no selected option and the caller supplies a null default value,
     * getParsedOptionValue should return null.
     */
    @Test(timeout = 4000)
    public void test_getParsedOptionValue_unselectedOptionGroup_withNullDefault_returnsNull() throws Throwable {
        CommandLine commandLine = new CommandLine();
        OptionGroup emptyOptionGroup = new OptionGroup();

        Option result = commandLine.getParsedOptionValue(emptyOptionGroup, (Option) null);

        assertNull(result);
    }
}
