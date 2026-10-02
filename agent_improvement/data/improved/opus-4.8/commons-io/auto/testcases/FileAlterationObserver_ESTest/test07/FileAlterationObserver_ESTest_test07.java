package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
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
     * When the observed directory tree contains a cycle - a {@link FileEntry} that is
     * registered as one of its own children - {@code checkAndNotify()} recurses into
     * {@code checkAndFire(...)} indefinitely and the JVM eventually throws a
     * {@link StackOverflowError}.
     */
    @Test(timeout = 4000)
    public void checkAndNotifyOnSelfReferencingEntryThrowsStackOverflow() throws Throwable {
        // An existing directory that backs the root entry of the observer.
        MockFile rootDirectory = new MockFile("Q<", "Q<");
        MockFile.createTempFile("org.apache.commons.io.output.WriterOutputStream", "Q<", (File) rootDirectory);

        // Build a self-referencing entry: the root entry lists itself as a child,
        // creating a cycle in the tree that checkAndFire() will traverse.
        FileEntry rootEntry = new FileEntry(rootDirectory);
        FileEntry[] children = new FileEntry[6];
        children[0] = rootEntry;
        rootEntry.setChildren(children);

        EmptyFileFilter fileFilter = (EmptyFileFilter) EmptyFileFilter.EMPTY;
        FileAlterationObserver observer =
                new FileAlterationObserver(rootEntry, fileFilter, IOCase.SYSTEM);

        try {
            observer.checkAndNotify();
            fail("Expecting exception: StackOverflowError");
        } catch (StackOverflowError e) {
            // Expected: the cyclic entry tree causes unbounded recursion.
            // The error carries no message.
        }
    }
}
