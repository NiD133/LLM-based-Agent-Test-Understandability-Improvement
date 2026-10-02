package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test16 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * When no file filter is supplied to the constructor, the observer should
     * install TrueFileFilter as the default, whose toString() returns "true".
     */
    @Test(timeout = 4000)
    public void testDefaultFileFilterIsTrueFileFilterWhenNoFilterSpecified() throws Throwable {
        // Any directory name is valid here; the filter default is independent of whether the path exists.
        FileAlterationObserver observer = new FileAlterationObserver("*d}l,{k~N_v");

        TrueFileFilter defaultFilter = (TrueFileFilter) observer.getFileFilter();

        assertEquals("true", defaultFilter.toString());
    }
}
