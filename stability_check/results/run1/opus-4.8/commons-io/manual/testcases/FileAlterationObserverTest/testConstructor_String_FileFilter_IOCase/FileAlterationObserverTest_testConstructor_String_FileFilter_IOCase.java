package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

/**
 * Tests the deprecated {@link FileAlterationObserver#FileAlterationObserver(String, java.io.FileFilter, IOCase)}
 * constructor, verifying that the directory name, file filter and IOCase are each stored on the observer.
 */
public class FileAlterationObserverTest_testConstructor_String_FileFilter_IOCase extends AbstractMonitorTest {

    /** Directory name passed to the constructor under test. */
    private static final String DIRECTORY_NAME = "/foo";

    /** Returns the observer's directory as a Unix-style path string, independent of the host OS separators. */
    private String getDirectoryAsUnixPath(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testConstructor_String_FileFilter_IOCase() {
        // Arrange & Act: build an observer from a directory name, a file filter and a case-insensitive IOCase.
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer =
                new FileAlterationObserver(DIRECTORY_NAME, CanReadFileFilter.CAN_READ, IOCase.INSENSITIVE);

        // Assert: each constructor argument is reflected by the corresponding getter.
        assertEquals(DIRECTORY_NAME, getDirectoryAsUnixPath(observer));
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }
}
