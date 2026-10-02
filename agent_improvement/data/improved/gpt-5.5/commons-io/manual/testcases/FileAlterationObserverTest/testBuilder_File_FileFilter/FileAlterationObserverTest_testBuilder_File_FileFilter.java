package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testBuilder_File_FileFilter extends AbstractMonitorTest {

    private static final String PATH_STRING_FIXTURE = "/foo";

    @Test
    void testBuilder_File_FileFilter() {
        final File file = new File(PATH_STRING_FIXTURE);
        final FileFilter readableFileFilter = CanReadFileFilter.CAN_READ;

        final FileAlterationObserver observer = FileAlterationObserver.builder()
            .setFile(file)
            .setFileFilter(readableFileFilter)
            .getUnchecked();

        assertEquals(file, observer.getDirectory());
        assertEquals(readableFileFilter, observer.getFileFilter());
    }
}
