package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testConstructor_File_FileFilter_IOCase extends AbstractMonitorTest {

    // Arbitrary path used to construct a File; the directory need not exist for constructor tests.
    private static final String WATCH_DIRECTORY_PATH = "/foo";

    @Test
    void testConstructor_File_FileFilter_IOCase() {
        final File watchDirectory = new File(WATCH_DIRECTORY_PATH);

        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(
                watchDirectory, CanReadFileFilter.CAN_READ, IOCase.INSENSITIVE);

        // The observer should track exactly the directory passed to the constructor.
        assertEquals(watchDirectory, observer.getDirectory());
        // The file filter supplied should be retained as-is.
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
        // IOCase.INSENSITIVE must map to the case-insensitive name comparator.
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }
}
