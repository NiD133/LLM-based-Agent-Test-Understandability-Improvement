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
public class CommandLine_ESTest_test12 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that getOptionValue returns the provided default value when the matched option
     * has no argument values set, even when queried via a deprecated Option instance.
     * Also verifies no NullPointerException occurs when the deprecated handler is null.
     */
    @Test(timeout = 4000)
    public void test_getOptionValue_returnsDefault_whenOptionHasNoValue_andDeprecatedHandlerIsNull() throws Throwable {
        // Build a CommandLine with no deprecated handler and add option "-cL" (no value set)
        Option nonDeprecatedOption = new Option("cL", "cL");
        CommandLine commandLine = CommandLine.builder()
                .setDeprecatedHandler((Consumer<Option>) null)
                .get();
        commandLine.addOption(nonDeprecatedOption);

        // Create a deprecated Option with the same short name "cL"
        Option deprecatedOption = Option.builder("cL").deprecated().get();

        // Querying by the deprecated option should match the added option (same key),
        // but since no value was set on the option, the default "cL" is returned.
        // The null deprecated handler must not cause a NullPointerException.
        String result = commandLine.getOptionValue(deprecatedOption, "cL");

        assertEquals("cL", result);
    }
}
