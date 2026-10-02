package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test31 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test31() throws Throwable {
        CommandLine commandLine0 = new CommandLine();
        OptionGroup optionGroup0 = new OptionGroup();
        Option option0 = commandLine0.getParsedOptionValue(optionGroup0, (Option) null);
        assertNull(option0);
    }
}
