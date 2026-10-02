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
public class CommandLine_ESTest_test36 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test36_getOptionValue_returnsDefaultWhenOptionGroupHasNoSelectedOption() throws Throwable {
        // An empty OptionGroup has no selected option, so getOptionValue should return the supplied default
        CommandLine commandLine = new CommandLine();
        OptionGroup emptyOptionGroup = new OptionGroup();
        String defaultValue = "u\" G.tb";

        String result = commandLine.getOptionValue(emptyOptionGroup, defaultValue);

        assertEquals(defaultValue, result);
    }
}
