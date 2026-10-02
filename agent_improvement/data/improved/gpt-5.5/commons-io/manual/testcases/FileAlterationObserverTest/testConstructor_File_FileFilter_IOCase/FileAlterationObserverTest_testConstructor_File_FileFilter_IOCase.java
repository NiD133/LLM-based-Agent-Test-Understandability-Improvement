package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testConstructor_File_FileFilter_IOCase extends AbstractMonitorTest {

    private static final String OBSERVED_DIRECTORY_PATH = "/foo";
    private static final FileFilter READABLE_FILES_ONLY = CanReadFileFilter.CAN_READ;
    private static final IOCase CASE_INSENSITIVE_FILE_NAMES = IOCase.INSENSITIVE;

    @Test
    void testConstructor_File_FileFilter_IOCase() {
        final File observedDirectory = new File(OBSERVED_DIRECTORY_PATH);

        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(
                observedDirectory,
                READABLE_FILES_ONLY,
                CASE_INSENSITIVE_FILE_NAMES);

        assertEquals(observedDirectory, observer.getDirectory(), "The observer should expose the directory passed to the constructor.");
        assertEquals(READABLE_FILES_ONLY, observer.getFileFilter(), "The observer should retain the provided file filter.");
        assertEquals(
                NameFileComparator.NAME_INSENSITIVE_COMPARATOR,
                observer.getComparator(),
                "IOCase.INSENSITIVE should select the case-insensitive name comparator.");
    }
}
