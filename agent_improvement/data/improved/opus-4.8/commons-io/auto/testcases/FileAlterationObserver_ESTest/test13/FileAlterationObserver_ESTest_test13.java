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
public class FileAlterationObserver_ESTest_test13 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that {@link FileAlterationObserver.Builder#setFileFilter} follows the
     * fluent builder contract by returning the very same builder instance it was
     * called on, allowing calls to be chained.
     */
    @Test(timeout = 4000)
    public void setFileFilterReturnsSameBuilderInstance() throws Throwable {
        // HiddenFileFilter.VISIBLE is internally implemented as a NotFileFilter.
        NotFileFilter visibleFileFilter = (NotFileFilter) HiddenFileFilter.VISIBLE;

        FileAlterationObserver.Builder builder = FileAlterationObserver.builder();
        FileAlterationObserver.Builder returnedBuilder = builder.setFileFilter(visibleFileFilter);

        assertSame(builder, returnedBuilder);
    }
}
