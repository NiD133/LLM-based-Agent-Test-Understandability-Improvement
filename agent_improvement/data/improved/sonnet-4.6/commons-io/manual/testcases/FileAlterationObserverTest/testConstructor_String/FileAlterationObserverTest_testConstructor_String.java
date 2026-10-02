package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testConstructor_String extends AbstractMonitorTest {

    private static final String DIRECTORY_PATH = "/foo";

    @Test
    void testConstructor_String() {
        // Tests the deprecated String-based constructor to ensure it correctly stores the directory path
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(DIRECTORY_PATH);

        final String observedPath = FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
        assertEquals(DIRECTORY_PATH, observedPath);
    }
}
