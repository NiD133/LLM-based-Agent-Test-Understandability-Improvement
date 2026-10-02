package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link FileAlterationObserver.Builder} correctly applies both the
 * directory path (supplied as a String) and a {@link java.io.FileFilter} to the
 * observer it creates.
 */
public class FileAlterationObserverTest_testBuilder_String_FileFilter extends AbstractMonitorTest {

    /** Directory path passed to the builder; uses a Unix-style separator. */
    private static final String DIRECTORY_PATH = "/foo";

    /**
     * Returns the observer's directory as a Unix-style path String, so the
     * comparison is independent of the platform's file separator.
     */
    private String getDirectoryAsUnixPath(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testBuilder_String_FileFilter() {
        // Build an observer from a directory path String and a "can read" file filter.
        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(DIRECTORY_PATH)
                .setFileFilter(CanReadFileFilter.CAN_READ)
                .getUnchecked();

        // The builder should observe exactly the directory it was given...
        assertEquals(DIRECTORY_PATH, getDirectoryAsUnixPath(observer));
        // ...and retain the file filter it was configured with.
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
    }
}
