package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.monitor.FileAlterationObserver.Builder;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testToString extends AbstractMonitorTest {

    private static final String PATH_STRING_FIXTURE = "/foo";

    /**
     * Test toString().
     */
    @Test
    void testToString() {
        final File file = new File(PATH_STRING_FIXTURE);
        final Builder builder = FileAlterationObserver.builder();

        FileAlterationObserver observer = builder.setFile(file).getUnchecked();
        final String defaultFilterDescription = "FileAlterationObserver[file='" + file.getPath() + "', true, listeners=0]";
        assertEquals(defaultFilterDescription, observer.toString());

        observer = builder.setFileFilter(CanReadFileFilter.CAN_READ).getUnchecked();
        final String canReadFilterDescription = "FileAlterationObserver[file='" + file.getPath() + "', CanReadFileFilter, listeners=0]";
        assertEquals(canReadFilterDescription, observer.toString());
        assertEquals(file, observer.getDirectory());
    }
}
