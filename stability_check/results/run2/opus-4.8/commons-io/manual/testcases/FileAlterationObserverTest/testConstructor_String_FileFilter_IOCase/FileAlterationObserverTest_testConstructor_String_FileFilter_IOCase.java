package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

/**
 * Tests the deprecated {@link FileAlterationObserver#FileAlterationObserver(String, java.io.FileFilter, IOCase)}
 * constructor, verifying that the directory name, file filter and IOCase are wired into the observer as expected.
 */
public class FileAlterationObserverTest_testConstructor_String_FileFilter_IOCase extends AbstractMonitorTest {

    /** Directory name passed to the observer under test. */
    private static final String DIRECTORY_NAME = "/foo";

    /**
     * Returns the observer's directory as a Unix-style path so it can be compared independently of the host OS
     * file separator.
     */
    private String directoryAsUnixPath(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testConstructor_String_FileFilter_IOCase() {
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer =
                new FileAlterationObserver(DIRECTORY_NAME, CanReadFileFilter.CAN_READ, IOCase.INSENSITIVE);

        assertEquals(DIRECTORY_NAME, directoryAsUnixPath(observer), "observed directory");
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter(), "file filter");
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator(),
                "comparator derived from IOCase.INSENSITIVE");
    }
}
