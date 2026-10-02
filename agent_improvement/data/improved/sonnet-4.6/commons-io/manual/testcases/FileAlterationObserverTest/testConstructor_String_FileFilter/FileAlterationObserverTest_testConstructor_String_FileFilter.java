package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

/**
 * Tests the deprecated {@code FileAlterationObserver(String, FileFilter)} constructor,
 * verifying that the observed directory path and file filter are stored correctly.
 */
public class FileAlterationObserverTest_testConstructor_String_FileFilter extends AbstractMonitorTest {

    private static final String OBSERVED_DIRECTORY_PATH = "/foo";

    private String directoryToUnixString(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testConstructor_String_FileFilter() {
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(OBSERVED_DIRECTORY_PATH, CanReadFileFilter.CAN_READ);

        assertEquals(OBSERVED_DIRECTORY_PATH, directoryToUnixString(observer),
                "Observer should record the directory path passed to the constructor");
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter(),
                "Observer should record the file filter passed to the constructor");
    }
}
