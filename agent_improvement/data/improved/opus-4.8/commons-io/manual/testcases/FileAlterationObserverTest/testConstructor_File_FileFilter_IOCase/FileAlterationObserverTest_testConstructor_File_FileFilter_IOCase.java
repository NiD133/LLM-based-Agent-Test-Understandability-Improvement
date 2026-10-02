package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

/**
 * Tests the deprecated {@link FileAlterationObserver#FileAlterationObserver(File, java.io.FileFilter, IOCase)}
 * constructor, verifying that the directory, file filter and case-sensitivity arguments are each stored
 * and exposed through the corresponding getters.
 */
public class FileAlterationObserverTest_testConstructor_File_FileFilter_IOCase extends AbstractMonitorTest {

    /** Arbitrary directory path supplied to the observer under test. */
    private static final String OBSERVED_DIRECTORY_PATH = "/foo";

    @Test
    void testConstructor_File_FileFilter_IOCase() {
        // Arrange: a directory, a file filter and a case-sensitivity mode to pass to the constructor.
        final File observedDirectory = new File(OBSERVED_DIRECTORY_PATH);

        // Act: build an observer via the deprecated (File, FileFilter, IOCase) constructor.
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer =
                new FileAlterationObserver(observedDirectory, CanReadFileFilter.CAN_READ, IOCase.INSENSITIVE);

        // Assert: each constructor argument is reflected by its getter.
        assertEquals(observedDirectory, observer.getDirectory());
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
        // IOCase.INSENSITIVE maps to the case-insensitive name comparator.
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }
}
