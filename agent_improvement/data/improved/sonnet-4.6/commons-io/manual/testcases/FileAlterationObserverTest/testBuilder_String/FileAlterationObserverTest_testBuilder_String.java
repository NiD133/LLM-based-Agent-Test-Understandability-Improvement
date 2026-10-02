package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.monitor.FileAlterationObserver.Builder;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testBuilder_String extends AbstractMonitorTest {

    private static final String DIRECTORY_PATH = "/foo";

    private String directoryToUnixString(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testBuilder_String() {
        final String directoryPath = DIRECTORY_PATH;
        final FileAlterationObserver observer = FileAlterationObserver.builder().setFile(directoryPath).getUnchecked();
        assertEquals(directoryPath, directoryToUnixString(observer),
                "Observer directory should match the string path passed to the builder");
    }
}
