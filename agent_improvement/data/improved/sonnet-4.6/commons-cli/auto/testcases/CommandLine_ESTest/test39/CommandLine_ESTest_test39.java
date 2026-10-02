package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test39 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_getParsedOptionValues_withUnselectedOptionGroupAndNullDefault_returnsNull() throws Throwable {
        CommandLine commandLine = CommandLine.builder().get();
        // An OptionGroup with no selected option is considered unselected
        OptionGroup unselectedOptionGroup = new OptionGroup();
        // null is passed as the default value array; the cast tells the compiler which overload to use
        Class<Option>[] result = commandLine.getParsedOptionValues(unselectedOptionGroup, (Class<Option>[]) null);
        assertNull(result);
    }
}
