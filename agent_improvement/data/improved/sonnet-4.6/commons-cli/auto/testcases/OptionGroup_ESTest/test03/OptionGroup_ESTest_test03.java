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

    // Verifies that selecting a second, different option in a group that already has a selection raises an exception.
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        OptionGroup optionGroup = new OptionGroup();

        Option alreadySelectedOption = new Option((String) null, "[]", true, (String) null);
        Option newOption = new Option((String) null, (String) null);

        optionGroup.setSelected(alreadySelectedOption);

        try {
            optionGroup.setSelected(newOption);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // The option 'null' was specified but an option from this group has already been selected: '[]'
            //
            verifyException("org.apache.commons.cli.OptionGroup", e);
        }
    }
}
