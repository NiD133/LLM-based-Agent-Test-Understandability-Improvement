package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests the deprecated {@link FileAlterationObserver#FileAlterationObserver(String)} constructor,
 * which builds an observer whose root directory is taken from the given path name.
 */
public class FileAlterationObserverTest_testConstructor_String extends AbstractMonitorTest {

    /** The directory path passed to the constructor under test. */
    private static final String DIRECTORY_PATH = "/foo";

    /**
     * Returns the observer's directory as a Unix-style path string, so the assertion
     * is independent of the host platform's file separator.
     */
    private String observedDirectoryAsUnixPath(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testConstructor_String() {
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(DIRECTORY_PATH);

        // The observer should report the same directory path it was constructed with.
        assertEquals(DIRECTORY_PATH, observedDirectoryAsUnixPath(observer));
    }
}
