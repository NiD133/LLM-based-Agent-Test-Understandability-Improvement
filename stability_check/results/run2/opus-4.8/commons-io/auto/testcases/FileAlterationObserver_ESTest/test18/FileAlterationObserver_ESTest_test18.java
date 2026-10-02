package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.util.Comparator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test18 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that an observer constructed from a directory name is given a
     * default (non-null) file-name comparator, even when no comparator is
     * supplied explicitly.
     */
    @Test(timeout = 4000)
    public void getComparatorReturnsDefaultComparatorWhenNoneSupplied() throws Throwable {
        FileAlterationObserver observer = new FileAlterationObserver("*d}l,{k~N_t");

        Comparator<File> defaultComparator = observer.getComparator();

        assertNotNull(defaultComparator);
    }
}
