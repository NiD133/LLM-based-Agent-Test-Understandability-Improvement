package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import java.util.Properties;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test06 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that an option holding a single value is exposed through
     * {@link CommandLine#getOptionProperties(Option)} as one property entry.
     * <p>
     * When an option has an odd number of values, the last (here: only) value is
     * treated as a property key whose value defaults to {@code "true"}, yielding a
     * Properties object of size 1.
     */
    @Test(timeout = 4000)
    public void getOptionPropertiesReturnsSingleEntryForOneValue() throws Throwable {
        // An option with no short name, long name "mf", which accepts an argument.
        final String singleValue = "2&jM^W@]Ux%2T.zg ";
        Option option = new Option((String) null, "mf", true, singleValue);

        CommandLine commandLine = new CommandLine();
        commandLine.addOption(option);

        // Attach exactly one value to the option.
        option.processValue(singleValue);

        Properties properties = commandLine.getOptionProperties(option);

        assertEquals(1, properties.size());
    }
}
