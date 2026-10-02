package org.apache.commons.io.monitor;

import org.junit.Test;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test11 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that a FileAlterationObserver can be constructed from a directory name,
     * an age-based file filter, and a case-insensitivity setting without throwing.
     */
    @Test(timeout = 4000)
    public void constructorAcceptsDirectoryNameFilterAndIOCase() throws Throwable {
        MockDate cutoffDate = new MockDate(-2406, -2406, -2406, -2406, -2406, 2);
        AgeFileFilter ageFileFilter = new AgeFileFilter(cutoffDate);
        IOCase caseInsensitive = IOCase.INSENSITIVE;

        new FileAlterationObserver("", ageFileFilter, caseInsensitive);
    }
}
