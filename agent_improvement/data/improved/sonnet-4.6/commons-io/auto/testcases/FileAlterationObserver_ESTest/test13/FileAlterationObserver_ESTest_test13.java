package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test13 extends FileAlterationObserver_ESTest_scaffolding {

    // Verifies that Builder.setFileFilter() returns the same Builder instance,
    // enabling fluent method chaining in the builder API.
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        NotFileFilter visibleFilesFilter = (NotFileFilter) HiddenFileFilter.VISIBLE;
        FileAlterationObserver.Builder builder = FileAlterationObserver.builder();
        FileAlterationObserver.Builder builderAfterSetFilter = builder.setFileFilter(visibleFilesFilter);
        assertSame(builder, builderAfterSetFilter);
    }
}
