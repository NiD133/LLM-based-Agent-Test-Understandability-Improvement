package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link FileAlterationObserver.Builder} correctly applies both the
 * observed directory and the file filter when an observer is built via
 * {@code setFile(...)} and {@code setFileFilter(...)}.
 */
public class FileAlterationObserverTest_testBuilder_File_FileFilter extends AbstractMonitorTest {

    /** Arbitrary directory path passed to the builder; it does not need to exist on disk. */
    private static final String OBSERVED_DIRECTORY_PATH = "/foo";

    @Test
    void testBuilder_File_FileFilter() {
        // Arrange: the directory to observe and the filter that selects only readable files.
        final File observedDirectory = new File(OBSERVED_DIRECTORY_PATH);

        // Act: build an observer configured with the directory and the file filter.
        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(observedDirectory)
                .setFileFilter(CanReadFileFilter.CAN_READ)
                .getUnchecked();

        // Assert: the observer exposes exactly the directory and filter it was built with.
        assertEquals(observedDirectory, observer.getDirectory());
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
    }
}
