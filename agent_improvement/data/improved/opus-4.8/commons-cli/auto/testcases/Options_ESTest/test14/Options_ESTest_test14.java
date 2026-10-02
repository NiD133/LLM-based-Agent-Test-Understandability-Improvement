package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test14 extends Options_ESTest_scaffolding {

    /**
     * An option that was never added to any OptionGroup should not be
     * associated with a group, so getOptionGroup must return null.
     */
    @Test(timeout = 4000)
    public void getOptionGroupReturnsNullForUngroupedOption() throws Throwable {
        Options options = new Options();
        Option ungroupedOption = new Option((String) null, true, (String) null);

        OptionGroup group = options.getOptionGroup(ungroupedOption);

        assertNull(group);
    }
}
