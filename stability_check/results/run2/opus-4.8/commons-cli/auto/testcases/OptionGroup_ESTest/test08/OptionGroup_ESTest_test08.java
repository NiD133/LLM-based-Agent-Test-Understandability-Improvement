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
     * A newly created, empty OptionGroup should still return a non-null
     * (empty) collection of option names from getNames().
     */
    @Test(timeout = 4000)
    public void getNamesOnEmptyGroupReturnsNonNullCollection() throws Throwable {
        OptionGroup emptyGroup = new OptionGroup();

        Collection<String> names = emptyGroup.getNames();

        assertNotNull("getNames() must never return null", names);
    }
}
