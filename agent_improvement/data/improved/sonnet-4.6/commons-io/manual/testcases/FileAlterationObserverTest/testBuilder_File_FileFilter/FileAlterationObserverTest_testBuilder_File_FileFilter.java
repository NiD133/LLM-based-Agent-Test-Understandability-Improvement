package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testBuilder_File_FileFilter extends AbstractMonitorTest {

    private static final String OBSERVED_PATH = "/foo";

    /**
     * Verifies that the builder correctly propagates a File directory and a FileFilter
     * to the constructed observer — i.e. getDirectory() and getFileFilter() return
     * exactly the values supplied via setFile() and setFileFilter().
     */
    @Test
    void testBuilder_File_FileFilter() {
        final File directory = new File(OBSERVED_PATH);
        final FileFilter filter = CanReadFileFilter.CAN_READ;

        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(directory)
                .setFileFilter(filter)
                .getUnchecked();

        assertEquals(directory, observer.getDirectory());
        assertEquals(filter, observer.getFileFilter());
    }
}
