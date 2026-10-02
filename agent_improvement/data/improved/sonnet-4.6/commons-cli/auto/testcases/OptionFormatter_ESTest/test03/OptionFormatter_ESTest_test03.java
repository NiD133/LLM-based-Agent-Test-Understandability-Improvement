package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test03 extends OptionFormatter_ESTest_scaffolding {

    /**
     * Verifies that getBothOpt() returns both the short and long option forms
     * combined with the default separator ", " and their respective prefixes
     * ("-" for short, "--" for long) when an option has both forms set to the same name.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Create an option with identical short and long option names ("arEg"),
        // accepting an argument, and with a description.
        Option option = new Option("arEg", "arEg", true, "arEg");

        OptionFormatter formatter = OptionFormatter.from(option);

        // getBothOpt() should combine both forms: "-<shortOpt>, --<longOpt>"
        String bothOpt = formatter.getBothOpt();
        assertEquals("-arEg, --arEg", bothOpt);
    }
}
