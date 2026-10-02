package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testConstructor_File_FileFilter extends AbstractMonitorTest {

    private static final String DIRECTORY_PATH = "/foo";

    @Test
    void testConstructor_File_FileFilter() {
        final File expectedDirectory = new File(DIRECTORY_PATH);
        final FileFilter expectedFilter = CanReadFileFilter.CAN_READ;

        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(expectedDirectory, expectedFilter);

        assertEquals(expectedDirectory, observer.getDirectory());
        assertEquals(expectedFilter, observer.getFileFilter());
    }
}
