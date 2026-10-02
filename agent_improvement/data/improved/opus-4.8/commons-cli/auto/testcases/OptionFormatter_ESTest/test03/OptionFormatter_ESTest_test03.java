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
     * When an option defines both a short opt ("arEg") and an identical long opt,
     * getBothOpt() should render both, each with its prefix ("-" and "--"),
     * joined by the default separator (", ").
     */
    @Test(timeout = 4000)
    public void getBothOptCombinesShortAndLongOptWithPrefixes() throws Throwable {
        // Option(opt, longOpt, hasArg, description) -> short and long opt both "arEg".
        Option optionWithShortAndLongName = new Option("arEg", "arEg", true, "arEg");
        OptionFormatter formatter = OptionFormatter.from(optionWithShortAndLongName);

        String bothOpt = formatter.getBothOpt();

        assertEquals("-arEg, --arEg", bothOpt);
    }
}
