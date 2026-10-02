package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.FileFilter;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test01 extends FileAlterationObserver_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Build an observer for an arbitrary path using the Builder API
        FileAlterationObserver.Builder builder = FileAlterationObserver.builder();
        builder.setFile("2?]h;XOH%&(5fmQ:Th+");
        FileAlterationObserver observerFromBuilder = builder.get();

        // Create a second observer for the empty-string directory, filtering only visible files
        FileFilter visibleFilesFilter = HiddenFileFilter.VISIBLE;
        FileAlterationObserver observerForEmptyDir = new FileAlterationObserver("", visibleFilesFilter);

        // Two observers configured with different root paths are not equal (identity comparison)
        assertFalse(observerForEmptyDir.equals((Object) observerFromBuilder));
    }
}
