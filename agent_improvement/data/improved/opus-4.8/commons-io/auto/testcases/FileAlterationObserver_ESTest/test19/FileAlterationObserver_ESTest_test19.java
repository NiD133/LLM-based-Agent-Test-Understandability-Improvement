package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.FileFilter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test19 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that toString() reflects the observer's default state: when a
     * null file filter is supplied, the constructor substitutes
     * TrueFileFilter (rendered as "true"), and no listeners are registered.
     */
    @Test(timeout = 4000)
    public void toStringReportsDirectoryDefaultFilterAndNoListeners() throws Throwable {
        MockFile rootDirectory = new MockFile("", "");
        FileAlterationObserver observer =
                new FileAlterationObserver(rootDirectory, (FileFilter) null);

        String description = observer.toString();

        assertEquals("FileAlterationObserver[file='/', true, listeners=0]", description);
    }
}
