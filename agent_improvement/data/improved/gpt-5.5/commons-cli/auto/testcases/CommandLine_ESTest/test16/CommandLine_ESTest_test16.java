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
public class CommandLine_ESTest_test16 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        CommandLine commandLine = new CommandLine();

        Option parsedOption = new Option("GPmL", "GPmL");
        commandLine.addOption(parsedOption);

        Option.Builder requestedOptionBuilder = Option.builder("GPmL");
        requestedOptionBuilder.deprecated();
        Option requestedDeprecatedOption = requestedOptionBuilder.get();

        Option[] parsedValues = commandLine.getParsedOptionValues(
                requestedDeprecatedOption,
                (Supplier<Option[]>) null);

        assertNull(parsedValues);
    }
}
