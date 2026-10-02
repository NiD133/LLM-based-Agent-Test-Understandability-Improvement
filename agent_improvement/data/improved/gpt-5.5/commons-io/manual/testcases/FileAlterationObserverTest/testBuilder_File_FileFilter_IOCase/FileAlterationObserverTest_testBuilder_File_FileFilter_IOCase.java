package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testBuilder_File_FileFilter_IOCase extends AbstractMonitorTest {

    private static final String PATH_STRING_FIXTURE = "/foo";

    @Test
    void testBuilder_File_FileFilter_IOCase() {
        final File observedDirectory = new File(PATH_STRING_FIXTURE);

        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(observedDirectory)
                .setFileFilter(CanReadFileFilter.CAN_READ)
                .setIOCase(IOCase.INSENSITIVE)
                .getUnchecked();

        assertEquals(observedDirectory, observer.getDirectory());
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }
}
