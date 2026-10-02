package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileFilter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testConstructor_String_FileFilter extends AbstractMonitorTest {

    private static final String DIRECTORY_NAME = "/foo";

    private String directoryName(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testConstructor_String_FileFilter() {
        final FileFilter expectedFileFilter = CanReadFileFilter.CAN_READ;

        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(DIRECTORY_NAME, expectedFileFilter);

        assertEquals(DIRECTORY_NAME, directoryName(observer));
        assertEquals(expectedFileFilter, observer.getFileFilter());
    }
}
