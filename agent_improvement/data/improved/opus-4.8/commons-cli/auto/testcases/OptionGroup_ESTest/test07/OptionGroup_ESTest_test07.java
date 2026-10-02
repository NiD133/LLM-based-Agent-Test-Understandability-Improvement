package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionGroup_ESTest_test07 extends OptionGroup_ESTest_scaffolding {

    /**
     * A newly created OptionGroup should report no selected option,
     * because no option has been set as selected yet.
     */
    @Test(timeout = 4000)
    public void newOptionGroupHasNoSelectedOption() throws Throwable {
        OptionGroup optionGroup = new OptionGroup();

        boolean selected = optionGroup.isSelected();

        assertFalse(selected);
    }
}
