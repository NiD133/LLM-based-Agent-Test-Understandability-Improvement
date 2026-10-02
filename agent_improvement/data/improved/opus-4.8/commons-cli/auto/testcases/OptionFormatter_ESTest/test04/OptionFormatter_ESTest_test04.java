package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test04 extends OptionFormatter_ESTest_scaffolding {

    /**
     * When an Option has neither a short opt nor a long opt set, the formatter's
     * combined "both opts" representation should be an empty string.
     */
    @Test(timeout = 4000)
    public void getBothOptReturnsEmptyWhenNoOptsAreSet() throws Throwable {
        Option optionWithoutAnyOpt = new Option(null, null);
        OptionFormatter formatter = OptionFormatter.from(optionWithoutAnyOpt);

        String bothOpt = formatter.getBothOpt();

        assertEquals("", bothOpt);
    }
}
