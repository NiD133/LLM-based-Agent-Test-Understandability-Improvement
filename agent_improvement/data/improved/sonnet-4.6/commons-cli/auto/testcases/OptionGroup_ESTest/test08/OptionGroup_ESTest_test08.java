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
     * Verifies that getNames() on a newly constructed OptionGroup returns
     * a non-null collection (the group starts empty but the collection itself
     * is always initialized).
     */
    @Test(timeout = 4000)
    public void getNames_onEmptyGroup_returnsNonNullCollection() throws Throwable {
        OptionGroup emptyGroup = new OptionGroup();
        Collection<String> optionNames = emptyGroup.getNames();
        assertNotNull(optionNames);
    }
}
