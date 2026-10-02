package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testBuilder_File extends AbstractMonitorTest {

    // An arbitrary path used to construct the File under test
    private static final String OBSERVED_DIRECTORY_PATH = "/foo";

    @Test
    @DisplayName("Builder.setFile() — getDirectory() returns the exact File passed to the builder")
    void testBuilder_File() {
        // Arrange: create the File that the observer should watch
        final File expectedDirectory = new File(OBSERVED_DIRECTORY_PATH);

        // Act: build the observer using the fluent builder API
        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(expectedDirectory)
                .getUnchecked();

        // Assert: the observer exposes the same File as its watched directory
        assertEquals(expectedDirectory, observer.getDirectory(),
                "getDirectory() should return the File supplied to Builder.setFile()");
    }
}
