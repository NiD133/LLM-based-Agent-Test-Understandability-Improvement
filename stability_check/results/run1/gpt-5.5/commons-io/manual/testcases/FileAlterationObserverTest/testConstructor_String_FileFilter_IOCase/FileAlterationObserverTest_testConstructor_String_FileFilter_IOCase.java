package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testConstructor_String_FileFilter_IOCase extends AbstractMonitorTest {

    private static final String DIRECTORY_NAME = "/foo";
    private static final FileFilter FILE_FILTER = CanReadFileFilter.CAN_READ;
    private static final IOCase IO_CASE = IOCase.INSENSITIVE;

    private String directoryToUnixString(final FileAlterationObserver observer) {
        final File directory = observer.getDirectory();
        return FilenameUtils.separatorsToUnix(directory.toString());
    }

    @Test
    void testConstructor_String_FileFilter_IOCase() {
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(DIRECTORY_NAME, FILE_FILTER, IO_CASE);

        assertEquals(DIRECTORY_NAME, directoryToUnixString(observer));
        assertEquals(FILE_FILTER, observer.getFileFilter());
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }
}
