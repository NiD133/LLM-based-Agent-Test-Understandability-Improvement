package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionGroup_ESTest_test10 extends OptionGroup_ESTest_scaffolding {

    /**
     * A newly constructed OptionGroup should not be required by default.
     */
    @Test(timeout = 4000)
    public void newOptionGroupIsNotRequiredByDefault() throws Throwable {
        OptionGroup optionGroup = new OptionGroup();

        boolean required = optionGroup.isRequired();

        assertFalse(required);
    }
}
