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
public class OptionGroup_ESTest_test05 extends OptionGroup_ESTest_scaffolding {

    /**
     * Verifies that a newly created OptionGroup is not required by default,
     * and that passing null to setSelected() (which resets any selection) does
     * not affect the required flag.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Arrange: create a fresh OptionGroup with no options added
        OptionGroup optionGroup = new OptionGroup();

        // Act: reset the selected option to null (no option is chosen)
        optionGroup.setSelected((Option) null);

        // Assert: the group is not required — the default required state is false
        assertFalse(optionGroup.isRequired());
    }
}
