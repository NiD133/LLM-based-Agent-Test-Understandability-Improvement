package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testBuilder_String_FileFilter extends AbstractMonitorTest {

    private static final String DIRECTORY_PATH = "/foo";

    private String observedDirectoryPath(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testBuilder_String_FileFilter() {
        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(DIRECTORY_PATH)
                .setFileFilter(CanReadFileFilter.CAN_READ)
                .getUnchecked();

        assertEquals(DIRECTORY_PATH, observedDirectoryPath(observer));
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
    }
}
