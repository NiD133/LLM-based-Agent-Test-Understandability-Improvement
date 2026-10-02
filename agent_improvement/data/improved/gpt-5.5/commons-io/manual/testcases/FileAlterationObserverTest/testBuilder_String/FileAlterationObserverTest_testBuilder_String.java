package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testBuilder_String extends AbstractMonitorTest {

    private static final String DIRECTORY_PATH = "/foo";

    private String observedDirectoryPath(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testBuilder_String() {
        final String file = DIRECTORY_PATH;
        final FileAlterationObserver observer = FileAlterationObserver.builder().setFile(file).getUnchecked();
        assertEquals(file, observedDirectoryPath(observer));
    }
}
