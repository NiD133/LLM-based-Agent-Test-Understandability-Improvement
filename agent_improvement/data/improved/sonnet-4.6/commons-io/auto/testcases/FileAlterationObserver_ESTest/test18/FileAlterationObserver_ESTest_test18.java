package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.util.Comparator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test18 extends FileAlterationObserver_ESTest_scaffolding {

    /**
     * Verifies that constructing a FileAlterationObserver from a directory path string
     * always yields a non-null file comparator, even for a non-existent or unusual path.
     */
    @Test(timeout = 4000)
    public void test_getComparator_returnsNonNullForStringConstructor() throws Throwable {
        FileAlterationObserver observer = new FileAlterationObserver("*d}l,{k~N_t");
        Comparator<File> comparator = observer.getComparator();
        assertNotNull(comparator);
    }
}
