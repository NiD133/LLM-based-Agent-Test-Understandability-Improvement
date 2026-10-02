package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

/**
 * Tests the deprecated {@link FileAlterationObserver#FileAlterationObserver(String, java.io.FileFilter)}
 * constructor, which builds an observer from a directory name and a file filter.
 */
public class FileAlterationObserverTest_testConstructor_String_FileFilter extends AbstractMonitorTest {

    /** Directory name passed to the constructor under test. */
    private static final String DIRECTORY_NAME = "/foo";

    /** Returns the observer's directory path normalized to Unix separators for cross-platform comparison. */
    private String getDirectoryAsUnixPath(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testConstructor_String_FileFilter() {
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer =
                new FileAlterationObserver(DIRECTORY_NAME, CanReadFileFilter.CAN_READ);

        // The observed directory should match the name supplied to the constructor.
        assertEquals(DIRECTORY_NAME, getDirectoryAsUnixPath(observer));
        // The file filter should be the exact instance supplied to the constructor.
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
    }
}
