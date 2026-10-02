package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * Tests the deprecated {@link FileAlterationObserver#FileAlterationObserver(File)} constructor,
 * verifying that the directory passed in is the one returned by {@link FileAlterationObserver#getDirectory()}.
 */
public class FileAlterationObserverTest_testConstructor_File extends AbstractMonitorTest {

    /** Arbitrary directory path used to build the observer under test. */
    private static final String DIRECTORY_PATH = "/foo";

    @Test
    void testConstructor_File() {
        final File directory = new File(DIRECTORY_PATH);

        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(directory);

        assertEquals(directory, observer.getDirectory());
    }
}
