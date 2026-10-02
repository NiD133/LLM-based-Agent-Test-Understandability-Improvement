package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test01 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Two observers built for different directories (and via different
     * construction paths) should not be considered equal to each other.
     */
    @Test(timeout = 4000)
    public void twoObserversForDifferentDirectoriesAreNotEqual() throws Throwable {
        // HiddenFileFilter.VISIBLE is implemented as a NotFileFilter instance.
        NotFileFilter visibleFileFilter = (NotFileFilter) HiddenFileFilter.VISIBLE;

        // Observer #1: built through the Builder for an arbitrary directory name.
        FileAlterationObserver.Builder builder = FileAlterationObserver.builder();
        builder.setFile("2?]h;XOH%&(5fmQ:Th+");
        FileAlterationObserver observerFromBuilder = builder.get();

        // Observer #2: built directly for the empty directory name with the filter.
        FileAlterationObserver observerForEmptyPath =
                new FileAlterationObserver("", visibleFileFilter);

        assertFalse(observerForEmptyPath.equals((Object) observerFromBuilder));
    }
}
