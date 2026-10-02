package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import java.util.Properties;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test05 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that {@link CommandLine#getOptionProperties(Option)} pairs up the
     * values of a multi-argument option into a key/value Properties map.
     *
     * <p>The option is given two values ("key" and "value"), which collapse into a
     * single property entry, so the resulting Properties map has exactly one entry.</p>
     */
    @Test(timeout = 4000)
    public void getOptionProperties_withTwoValues_returnsSinglePropertyEntry() throws Throwable {
        // An option with a long name "mf" that accepts arguments.
        Option multiArgOption = new Option((String) null, "mf", true, "&jM^W@]Ux%2T.zg ");
        // Allow the option to hold several argument values.
        multiArgOption.setArgs(320);

        CommandLine commandLine = new CommandLine();
        commandLine.addOption(multiArgOption);

        // Supply two values: the first becomes the property key, the second its value.
        multiArgOption.processValue("&jM^W@]Ux%2T.zg ");
        multiArgOption.processValue("R({[");

        Properties properties = commandLine.getOptionProperties(multiArgOption);

        // The two values form one key/value pair, hence a single property entry.
        assertEquals(1, properties.size());
    }
}
