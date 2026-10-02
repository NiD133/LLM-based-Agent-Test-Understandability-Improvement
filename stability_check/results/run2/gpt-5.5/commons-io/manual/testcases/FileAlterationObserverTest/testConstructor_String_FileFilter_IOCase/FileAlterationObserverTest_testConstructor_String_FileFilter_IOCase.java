package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testConstructor_String_FileFilter_IOCase extends AbstractMonitorTest {

    private static final String DIRECTORY_NAME = "/foo";

    private static String directoryToUnixString(final FileAlterationObserver observer) {
        final File directory = observer.getDirectory();
        return FilenameUtils.separatorsToUnix(directory.toString());
    }

    @Test
    @SuppressWarnings("deprecation")
    void testConstructor_String_FileFilter_IOCase() {
        final FileAlterationObserver observer = new FileAlterationObserver(DIRECTORY_NAME, CanReadFileFilter.CAN_READ, IOCase.INSENSITIVE);

        assertEquals(DIRECTORY_NAME, directoryToUnixString(observer));
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }
}
