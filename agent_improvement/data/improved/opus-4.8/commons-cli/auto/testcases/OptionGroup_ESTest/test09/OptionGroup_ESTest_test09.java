package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionGroup_ESTest_test09 extends OptionGroup_ESTest_scaffolding {

    /**
     * Verifies that setting an option group's "required" flag to false leaves
     * the group reported as not required.
     */
    @Test(timeout = 4000)
    public void settingRequiredToFalseMakesGroupNotRequired() throws Throwable {
        OptionGroup optionGroup = new OptionGroup();

        optionGroup.setRequired(false);

        assertFalse(optionGroup.isRequired());
    }
}
