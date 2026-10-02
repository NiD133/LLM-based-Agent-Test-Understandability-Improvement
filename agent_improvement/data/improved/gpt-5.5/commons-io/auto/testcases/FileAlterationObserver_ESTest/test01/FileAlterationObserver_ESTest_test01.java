package org.apache.commons.io.monitor;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.EmptyFileFilter;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.evosuite.runtime.testdata.EvoSuiteFile;
import org.evosuite.runtime.testdata.FileSystemHandling;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileAlterationObserver_ESTest_test01 extends FileAlterationObserver_ESTest_scaffolding {

    private static final String BUILDER_DIRECTORY_NAME = "2?]h;XOH%&(5fmQ:Th+";
    private static final String EMPTY_DIRECTORY_NAME = "";

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        NotFileFilter visibleFileFilter = (NotFileFilter) HiddenFileFilter.VISIBLE;

        FileAlterationObserver.Builder observerBuilder = FileAlterationObserver.builder();
        observerBuilder.setFile(BUILDER_DIRECTORY_NAME);
        FileAlterationObserver observerFromBuilder = observerBuilder.get();

        FileAlterationObserver observerForEmptyDirectory = new FileAlterationObserver(EMPTY_DIRECTORY_NAME, visibleFileFilter);

        assertFalse(observerForEmptyDirectory.equals((Object) observerFromBuilder));
    }
}
