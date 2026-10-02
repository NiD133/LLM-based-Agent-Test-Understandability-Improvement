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
     * Verifies that toString() includes the observed directory path, the effective file filter
     * (null filter defaults to TrueFileFilter, shown as "true"), and the listener count.
     *
     * MockFile("", "") resolves to the VFS root ("/") because an empty parent combined with
     * an empty child name yields the root path in the mock file system.
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        // A MockFile with empty parent and empty name resolves to the VFS root "/"
        MockFile rootDirectory = new MockFile("", "");

        // Null filter is internally replaced by TrueFileFilter (serialised as "true")
        FileAlterationObserver observer = new FileAlterationObserver(rootDirectory, (FileFilter) null);

        String observerDescription = observer.toString();

        // Expected format: ClassName[file='<path>', <filter>, listeners=<count>]
        assertEquals("FileAlterationObserver[file='/', true, listeners=0]", observerDescription);
    }
}
