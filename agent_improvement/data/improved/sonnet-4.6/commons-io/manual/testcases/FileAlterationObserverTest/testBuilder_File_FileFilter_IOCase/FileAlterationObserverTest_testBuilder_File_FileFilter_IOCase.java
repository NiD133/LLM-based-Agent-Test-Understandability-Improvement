package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.io.FileFilter;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testBuilder_File_FileFilter_IOCase extends AbstractMonitorTest {

    // An arbitrary path used to construct a File for the observer under test.
    private static final String OBSERVED_DIRECTORY_PATH = "/foo";

    @Test
    void testBuilder_File_FileFilter_IOCase() {
        // Verify that setting File, FileFilter, and IOCase on the builder is reflected
        // in the resulting observer's directory, file filter, and name comparator.
        final File observedDirectory = new File(OBSERVED_DIRECTORY_PATH);

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
