package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link FileAlterationObserver.Builder#setFile(java.io.File)} accepting a path
 * {@code String} results in an observer whose observed directory matches that path.
 */
public class FileAlterationObserverTest_testBuilder_String extends AbstractMonitorTest {

    /** The directory path used to configure the builder. */
    private static final String DIRECTORY_PATH = "/foo";

    /**
     * Returns the observer's directory as a Unix-style path string, so the assertion is
     * independent of the platform's file separator.
     */
    private String observedDirectoryAsUnixPath(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testBuilder_String() {
        // Build an observer from a directory path supplied as a String.
        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(DIRECTORY_PATH)
                .getUnchecked();

        // The observer should report the same directory it was configured with.
        assertEquals(DIRECTORY_PATH, observedDirectoryAsUnixPath(observer));
    }
}
