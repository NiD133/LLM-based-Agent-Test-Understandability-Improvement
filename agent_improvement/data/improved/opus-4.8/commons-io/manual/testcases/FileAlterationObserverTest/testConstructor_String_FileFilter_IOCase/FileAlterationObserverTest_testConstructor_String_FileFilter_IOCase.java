package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

/**
 * Tests the deprecated {@link FileAlterationObserver#FileAlterationObserver(String, java.io.FileFilter, IOCase)}
 * constructor, verifying that each of the three arguments is stored and exposed correctly by the observer.
 */
public class FileAlterationObserverTest_testConstructor_String_FileFilter_IOCase extends AbstractMonitorTest {

    /** Directory path passed to the constructor (Unix-style so the assertion is platform independent). */
    private static final String DIRECTORY_PATH = "/foo";

    /**
     * Returns the observer's directory as a Unix-style path string, so it can be compared against
     * {@link #DIRECTORY_PATH} regardless of the platform's file separator.
     */
    private String getDirectoryAsUnixPath(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testConstructor_String_FileFilter_IOCase() {
        // Construct an observer using the (String directory, FileFilter, IOCase) constructor.
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer =
                new FileAlterationObserver(DIRECTORY_PATH, CanReadFileFilter.CAN_READ, IOCase.INSENSITIVE);

        // The directory path argument is retained as the observed directory.
        assertEquals(DIRECTORY_PATH, getDirectoryAsUnixPath(observer));

        // The file filter argument is retained as-is.
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());

        // IOCase.INSENSITIVE maps to the case-insensitive name comparator.
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }
}
