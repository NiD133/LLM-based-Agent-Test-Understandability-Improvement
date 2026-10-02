package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.FileFilter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.evosuite.runtime.testdata.EvoSuiteFile;
import org.evosuite.runtime.testdata.FileSystemHandling;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test06 extends FileAlterationObserver_ESTest_scaffolding {

    private static final String EMPTY_PATH_PART = "";
    private static final FileFilter NO_FILE_FILTER = null;

    @Test(timeout = 4000)
    public void checkAndNotifyWithEmptyMockFileAndNoFilter() throws Throwable {
        MockFile observedRoot = new MockFile(EMPTY_PATH_PART, EMPTY_PATH_PART);
        FileAlterationObserver observer = new FileAlterationObserver(observedRoot, NO_FILE_FILTER);

        observer.checkAndNotify();
    }
}
