package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test08 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        final IOCase systemCaseRules = IOCase.SYSTEM;
        final String valueToSearch = "System";
        final int startIndexPastEndOfValue = 24;
        final String missingSearchTerm = null;

        final int index = systemCaseRules.checkIndexOf(valueToSearch, startIndexPastEndOfValue, missingSearchTerm);

        assertEquals((-1), index);
    }
}
