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
public class OptionGroup_ESTest_test09 extends OptionGroup_ESTest_scaffolding {

    /**
     * Verifies that setting an OptionGroup as not required via setRequired(false)
     * is correctly reflected by isRequired() returning false.
     */
    @Test(timeout = 4000)
    public void test_setRequired_false_isRequired_returnsFalse() throws Throwable {
        OptionGroup optionGroup = new OptionGroup();

        optionGroup.setRequired(false);

        assertFalse(optionGroup.isRequired());
    }
}
