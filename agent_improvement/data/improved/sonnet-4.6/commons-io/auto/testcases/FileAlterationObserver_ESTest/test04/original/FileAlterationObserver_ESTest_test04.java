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
public class FileAlterationObserver_ESTest_test04 extends FileAlterationObserver_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        MockFile mockFile0 = new MockFile("", "");
        FileAlterationObserver.Builder fileAlterationObserver_Builder0 = FileAlterationObserver.builder();
        IOCase iOCase0 = IOCase.SENSITIVE;
        FileEntry fileEntry0 = new FileEntry(mockFile0);
        fileAlterationObserver_Builder0.setRootEntry(fileEntry0);
        MockDate mockDate0 = new MockDate((-2406), (-2406), (-2406), (-2406), (-2406), 2);
        AgeFileFilter ageFileFilter0 = new AgeFileFilter(mockDate0);
        MockFile.createTempFile("^i[JH", "rootEntry");
        FileAlterationObserver fileAlterationObserver0 = fileAlterationObserver_Builder0.get();
        fileAlterationObserver0.initialize();
        FileAlterationListenerAdaptor fileAlterationListenerAdaptor0 = new FileAlterationListenerAdaptor();
        IOFileFilter[] iOFileFilterArray0 = new IOFileFilter[4];
        iOFileFilterArray0[0] = (IOFileFilter) ageFileFilter0;
        iOFileFilterArray0[1] = (IOFileFilter) ageFileFilter0;
        iOFileFilterArray0[2] = (IOFileFilter) ageFileFilter0;
        iOFileFilterArray0[3] = (IOFileFilter) ageFileFilter0;
        AndFileFilter andFileFilter0 = new AndFileFilter(iOFileFilterArray0);
        FileAlterationObserver fileAlterationObserver1 = new FileAlterationObserver(fileEntry0, andFileFilter0, iOCase0);
        fileAlterationObserver1.addListener(fileAlterationListenerAdaptor0);
        fileAlterationObserver1.checkAndNotify();
        assertEquals("", fileEntry0.getName());
    }
}
