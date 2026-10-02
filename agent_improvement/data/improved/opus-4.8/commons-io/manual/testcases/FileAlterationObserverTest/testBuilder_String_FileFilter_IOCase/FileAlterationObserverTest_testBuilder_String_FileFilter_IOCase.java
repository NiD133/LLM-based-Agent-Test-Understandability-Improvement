package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link FileAlterationObserver.Builder} correctly transfers the
 * directory, file filter and IOCase it is configured with onto the resulting
 * {@link FileAlterationObserver}.
 */
public class FileAlterationObserverTest_testBuilder_String_FileFilter_IOCase extends AbstractMonitorTest {

    /** Directory path used to configure the observer under test. */
    private static final String EXPECTED_DIRECTORY = "/foo";

    /**
     * Returns the observer's directory as a Unix-style path string, so the
     * assertion is independent of the host operating system's file separator.
     */
    private String directoryAsUnixPath(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testBuilder_String_FileFilter_IOCase() {
        // Build an observer from a directory path, a file filter and a case-insensitive IOCase.
        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(EXPECTED_DIRECTORY)
                .setFileFilter(CanReadFileFilter.CAN_READ)
                .setIOCase(IOCase.INSENSITIVE)
                .getUnchecked();

        // The configured directory, filter and IOCase must be reflected on the built observer.
        assertEquals(EXPECTED_DIRECTORY, directoryAsUnixPath(observer));
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
        // INSENSITIVE IOCase maps to the case-insensitive name comparator.
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }
}
