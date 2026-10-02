package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testConstructor_File_FileFilter extends AbstractMonitorTest {

    private static final String OBSERVED_DIRECTORY_PATH = "/foo";

    @Test
    void testConstructor_File_FileFilter() {
        final File observedDirectory = new File(OBSERVED_DIRECTORY_PATH);
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(observedDirectory, CanReadFileFilter.CAN_READ);

        assertEquals(observedDirectory, observer.getDirectory(),
                "Observer should track the directory passed to the constructor");
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter(),
                "Observer should use the file filter passed to the constructor");
    }
}
