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
public class OptionGroup_ESTest_test08 extends OptionGroup_ESTest_scaffolding {

    /**
     * Verifies that a newly created OptionGroup returns a non-null collection
     * of option names, even when no options have been added yet.
     */
    @Test(timeout = 4000)
    public void test_getNames_onNewOptionGroup_returnsNonNullCollection() throws Throwable {
        OptionGroup emptyGroup = new OptionGroup();

        Collection<String> optionNames = emptyGroup.getNames();

        assertNotNull("getNames() should never return null for an empty group", optionNames);
    }
}
