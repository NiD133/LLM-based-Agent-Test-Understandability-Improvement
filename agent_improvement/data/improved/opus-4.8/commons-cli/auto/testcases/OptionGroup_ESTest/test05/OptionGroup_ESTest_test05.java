package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionGroup_ESTest_test05 extends OptionGroup_ESTest_scaffolding {

    /**
     * Clearing the selection on a new group (by selecting {@code null}) must
     * succeed and must leave the group's "required" flag at its default of false.
     */
    @Test(timeout = 4000)
    public void selectingNullLeavesGroupNotRequired() throws Throwable {
        OptionGroup optionGroup = new OptionGroup();

        optionGroup.setSelected((Option) null);

        assertFalse(optionGroup.isRequired());
    }
}
