package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileFilter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testConstructor_String_FileFilter_IOCase extends AbstractMonitorTest {

    private static final String DIRECTORY_PATH = "/foo";

    private String directoryToUnixString(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testConstructor_String_FileFilter_IOCase() {
        // Create observer using the deprecated (String, FileFilter, IOCase) constructor
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(
                DIRECTORY_PATH, CanReadFileFilter.CAN_READ, IOCase.INSENSITIVE);

        // The observer should record the directory path, file filter, and case-insensitive comparator
        assertEquals(DIRECTORY_PATH, directoryToUnixString(observer));
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }
}
