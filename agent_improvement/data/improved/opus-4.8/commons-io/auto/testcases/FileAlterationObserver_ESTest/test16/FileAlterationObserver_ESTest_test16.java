package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.FileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test16 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * When an observer is created without an explicit file filter, the constructor
     * falls back to {@link TrueFileFilter#INSTANCE}, whose {@code toString()} is "true".
     */
    @Test(timeout = 4000)
    public void defaultFileFilterIsTrueFileFilter() throws Throwable {
        FileAlterationObserver observerWithoutFilter = new FileAlterationObserver("*d}l,{k~N_v");

        FileFilter defaultFilter = observerWithoutFilter.getFileFilter();

        assertTrue("default filter should be TrueFileFilter", defaultFilter instanceof TrueFileFilter);
        assertEquals("true", defaultFilter.toString());
    }
}
