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
public class OptionGroup_ESTest_test03 extends OptionGroup_ESTest_scaffolding {

    /**
     * Selecting a second, different option from a group that already has a
     * selection must fail: a group is mutually exclusive, so once one option
     * is selected, choosing another throws AlreadySelectedException.
     */
    @Test(timeout = 4000)
    public void selectingDifferentOptionWhenOneAlreadySelectedThrows() throws Throwable {
        OptionGroup optionGroup = new OptionGroup();

        // Two distinct options (different keys) belonging to the same group.
        Option firstOption = new Option((String) null, "[]", true, (String) null);
        Option secondOption = new Option((String) null, (String) null);

        // The first selection succeeds and locks the group's choice.
        optionGroup.setSelected(firstOption);

        // Selecting a different option must now be rejected.
        try {
            optionGroup.setSelected(secondOption);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // The option 'null' was specified but an option from this group
            // has already been selected: '[]'
            verifyException("org.apache.commons.cli.OptionGroup", e);
        }
    }
}
