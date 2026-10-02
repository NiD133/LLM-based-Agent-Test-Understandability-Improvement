package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test02 extends OptionFormatter_ESTest_scaffolding {

    /**
     * An option that only has a short opt (no long opt) should produce a
     * combined display string consisting of the short-option prefix ("-")
     * followed by the short opt, with no separator and no long opt appended.
     */
    @Test(timeout = 4000)
    public void getBothOpt_withShortOptOnly_returnsPrefixedShortOpt() throws Throwable {
        Option optionWithShortOptOnly = new Option("NO_ARGS_ALLOWED", "NO_ARGS_ALLOWED");
        OptionFormatter formatter = OptionFormatter.from(optionWithShortOptOnly);

        String bothOpt = formatter.getBothOpt();

        assertEquals("-NO_ARGS_ALLOWED", bothOpt);
    }
}
