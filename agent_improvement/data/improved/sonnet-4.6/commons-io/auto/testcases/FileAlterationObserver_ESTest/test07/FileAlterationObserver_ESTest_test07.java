package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.File;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.EmptyFileFilter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test07 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that checkAndNotify() throws StackOverflowError when a FileEntry
     * contains itself as one of its own children, creating an infinite recursion cycle.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Use the singleton EmptyFileFilter (accepts only empty files/directories)
        EmptyFileFilter emptyFileFilter = (EmptyFileFilter) EmptyFileFilter.EMPTY;

        // Create the root directory entry backed by a mock file
        MockFile rootDir = new MockFile("Q<", "Q<");
        FileEntry rootEntry = new FileEntry(rootDir);

        // Populate the mock filesystem so the directory appears to exist
        MockFile.createTempFile("org.apache.commons.io.output.WriterOutputStream", "Q<", (File) rootDir);

        // Build a children array where index 0 points back to the root entry itself,
        // creating a circular parent→child→parent reference
        FileEntry[] childrenWithCircularRef = new FileEntry[6];
        childrenWithCircularRef[0] = rootEntry;
        rootEntry.setChildren(childrenWithCircularRef);

        // Construct the observer using the circular root entry
        FileAlterationObserver observer = new FileAlterationObserver(rootEntry, emptyFileFilter, IOCase.SYSTEM);

        // checkAndNotify() recursively walks children; the circular reference causes
        // infinite recursion and must overflow the call stack
        try {
            observer.checkAndNotify();
            fail("Expecting exception: StackOverflowError");
        } catch (StackOverflowError e) {
            //
            // no message in exception (getMessage() returned null)
            //
        }
    }
}
