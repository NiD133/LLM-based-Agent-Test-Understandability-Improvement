package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test02 extends OptionFormatter_ESTest_scaffolding {

    /**
     * Verifies that getBothOpt() returns only the short option prefixed with "-"
     * when an option has a short opt but no long opt.
     */
    @Test(timeout = 4000)
    public void test_getBothOpt_returnsShortOptWithPrefix_whenNoLongOptPresent() throws Throwable {
        Option shortOnlyOption = new Option("NO_ARGS_ALLOWED", "NO_ARGS_ALLOWED");
        OptionFormatter formatter = OptionFormatter.from(shortOnlyOption);

        String bothOpt = formatter.getBothOpt();

        assertEquals("-NO_ARGS_ALLOWED", bothOpt);
    }
}
