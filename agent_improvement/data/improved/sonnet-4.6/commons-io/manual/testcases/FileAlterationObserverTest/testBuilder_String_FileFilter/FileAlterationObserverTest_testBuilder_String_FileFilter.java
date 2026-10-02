package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileFilter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testBuilder_String_FileFilter extends AbstractMonitorTest {

    private static final String OBSERVED_DIRECTORY_PATH = "/foo";

    /**
     * Converts the observer's directory to a Unix-style path string for
     * platform-independent comparison.
     */
    private String toUnixPath(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    /**
     * Verifies that building a {@link FileAlterationObserver} from a directory path
     * string and a {@link FileFilter} correctly stores both values.
     */
    @Test
    void testBuilder_String_FileFilter() {
        final FileFilter expectedFilter = CanReadFileFilter.CAN_READ;

        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(OBSERVED_DIRECTORY_PATH)
                .setFileFilter(expectedFilter)
                .getUnchecked();

        assertEquals(OBSERVED_DIRECTORY_PATH, toUnixPath(observer),
                "Observer should watch the directory path that was supplied to the builder");
        assertEquals(expectedFilter, observer.getFileFilter(),
                "Observer should use the file filter that was supplied to the builder");
    }
}
