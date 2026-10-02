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
public class FileAlterationObserver_ESTest_test11 extends FileAlterationObserver_ESTest_scaffolding {

    // Extreme out-of-range value used for all date fields except seconds,
    // exercising the constructor with an unconventional AgeFileFilter threshold.
    private static final int EXTREME_DATE_FIELD_VALUE = -2406;

    @Test(timeout = 4000)
    public void testConstructorWithAgeFilterAndInsensitiveCaseOnEmptyPath() throws Throwable {
        // Build an AgeFileFilter from a date whose fields are all extreme negative values.
        // This exercises that the observer constructor accepts any valid FileFilter.
        MockDate extremeDate = new MockDate(EXTREME_DATE_FIELD_VALUE, EXTREME_DATE_FIELD_VALUE,
                EXTREME_DATE_FIELD_VALUE, EXTREME_DATE_FIELD_VALUE, EXTREME_DATE_FIELD_VALUE, 2);
        AgeFileFilter ageFileFilter = new AgeFileFilter(extremeDate);

        IOCase caseInsensitive = IOCase.INSENSITIVE;

        // Verify that constructing an observer with an empty directory path,
        // an AgeFileFilter, and case-insensitive comparison does not throw.
        FileAlterationObserver observer = new FileAlterationObserver("", ageFileFilter, caseInsensitive);
    }
}
