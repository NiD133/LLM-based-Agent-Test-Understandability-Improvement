package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testConstructor_String extends AbstractMonitorTest {

    private static final String DIRECTORY_NAME = "/foo";

    private String observedDirectoryAsUnixPath(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testConstructor_String() {
        final String directoryName = DIRECTORY_NAME;
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(directoryName);
        assertEquals(directoryName, observedDirectoryAsUnixPath(observer));
    }
}
