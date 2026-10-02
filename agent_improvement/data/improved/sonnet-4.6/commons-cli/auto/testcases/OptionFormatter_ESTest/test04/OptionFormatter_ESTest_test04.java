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
     * When both the short opt and long opt of an Option are null,
     * getBothOpt() should return an empty string because neither prefix
     * nor separator has anything to attach to.
     */
    @Test(timeout = 4000)
    public void test_getBothOpt_returnsEmptyString_whenBothOptAndLongOptAreNull() throws Throwable {
        Option optionWithNoNames = new Option((String) null, (String) null);
        OptionFormatter formatter = OptionFormatter.from(optionWithNoNames);

        String bothOpt = formatter.getBothOpt();

        assertEquals("", bothOpt);
    }
}
