package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Collection;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionGroup_ESTest_test07 extends OptionGroup_ESTest_scaffolding {

    /**
     * A freshly constructed OptionGroup has no option selected, so isSelected()
     * must return false.
     */
    @Test(timeout = 4000)
    public void test_newOptionGroup_isNotSelected() throws Throwable {
        OptionGroup optionGroup = new OptionGroup();
        boolean isSelected = optionGroup.isSelected();
        assertFalse(isSelected);
    }
}
