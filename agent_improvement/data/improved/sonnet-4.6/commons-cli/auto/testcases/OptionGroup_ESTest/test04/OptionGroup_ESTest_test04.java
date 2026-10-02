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
public class OptionGroup_ESTest_test04 extends OptionGroup_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Verify that re-selecting the same option in a group is idempotent (no AlreadySelectedException thrown)
        OptionGroup group = new OptionGroup();
        Option option = new Option((String) null, "[]", true, (String) null);

        group.setSelected(option);
        group.setSelected(option); // Re-selecting the same option is allowed

        // The default value separator is the null character when none has been set
        assertEquals('\u0000', option.getValueSeparator());
    }
}
