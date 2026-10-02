/*
 * Tests for XmlStreamWriter – verifies encoding detection, default-encoding
 * reporting, flush/close behaviour, and NPE handling on a null OutputStream.
 */

package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Writer;
import java.nio.CharBuffer;
import org.apache.commons.io.output.XmlStreamWriter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest extends XmlStreamWriter_ESTest_scaffolding {

    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Builder-based construction: wrapping a DataOutputStream yields a writer
     * whose default encoding is UTF-8.
     */
    @Test(timeout = 4000)
    public void test_builderWithDataOutputStream_defaultEncodingIsUtf8() throws Throwable {
        XmlStreamWriter.Builder builder = XmlStreamWriter.builder();

        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(pipedOutputStream);

        builder.setOutputStream(dataOutputStream);
        XmlStreamWriter writer = builder.get();

        assertEquals(DEFAULT_ENCODING, writer.getDefaultEncoding());
    }

    /**
     * Flushing after the writer has been closed must throw IOException.
     */
    @Test(timeout = 4000)
    public void test_flushAfterClose_throwsIOException() throws Throwable {
        MockFile mockFile = new MockFile("DQf{xmx %q-", "DQf{xmx %q-");
        XmlStreamWriter writer = new XmlStreamWriter(mockFile);
        writer.close();

        try {
            writer.flush();
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            // expected: the underlying stream is already closed
        }
    }

    /**
     * Flushing a writer backed by a null OutputStream is a no-op (writer
     * field stays null until encoding is detected), and the default encoding
     * remains UTF-8.
     */
    @Test(timeout = 4000)
    public void test_flushWithNullOutputStream_noOpAndDefaultEncodingIsUtf8() throws Throwable {
        XmlStreamWriter writer = new XmlStreamWriter((OutputStream) null);
        writer.flush();

        assertEquals(DEFAULT_ENCODING, writer.getDefaultEncoding());
    }

    /**
     * Writing the XML prolog opening "<?xml" starts buffering; completing the
     * prolog with "?>" triggers encoding detection which tries to write to the
     * null OutputStream and throws NullPointerException.
     */
    @Test(timeout = 4000)
    public void test_writeXmlPrologCompletion_throwsNullPointerExceptionOnNullOutputStream()
            throws Throwable {
        XmlStreamWriter writer = new XmlStreamWriter((OutputStream) null);
        writer.write("<?xml");

        // Completing "?>" causes encoding detection to flush to the null stream
        try {
            writer.write("?>");
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("java.io.Writer", e);
        }
    }

    /**
     * Writing a single character to a writer backed by a null OutputStream is
     * buffered in the prolog writer; the default encoding remains UTF-8.
     */
    @Test(timeout = 4000)
    public void test_writeSingleCharWithNullOutputStream_defaultEncodingIsUtf8() throws Throwable {
        XmlStreamWriter writer = new XmlStreamWriter((OutputStream) null);
        writer.write(8192);

        assertEquals(DEFAULT_ENCODING, writer.getDefaultEncoding());
    }

    /**
     * Writing non-XML content to a file-backed writer buffers the first chunk
     * (no "<?xml" prefix → no encoding detection), then subsequent writes also
     * remain buffered; the default encoding stays UTF-8.
     */
    @Test(timeout = 4000)
    public void test_writeNonXmlContent_defaultEncodingIsUtf8() throws Throwable {
        MockFile mockFile = new MockFile("nz", "nz");
        XmlStreamWriter writer = new XmlStreamWriter(mockFile);

        writer.write("\t\tm;+");
        writer.write("nz");

        assertEquals(DEFAULT_ENCODING, writer.getDefaultEncoding());
    }

    /**
     * Constructing a writer from a temp file succeeds and the default encoding
     * is UTF-8.
     */
    @Test(timeout = 4000)
    public void test_constructFromTempFile_defaultEncodingIsUtf8() throws Throwable {
        MockFile dir = new MockFile("");
        File tempFile = MockFile.createTempFile("$VALUES", "ij*`!AFN", (File) dir);
        XmlStreamWriter writer = new XmlStreamWriter(tempFile);

        String defaultEncoding = writer.getDefaultEncoding();
        assertEquals(DEFAULT_ENCODING, defaultEncoding);
    }

    /**
     * After appending the XML prolog start "<?xml" the writer is still in
     * buffering mode; appending a large CharBuffer then triggers encoding
     * detection which tries to write to the null OutputStream and throws
     * NullPointerException.
     */
    @Test(timeout = 4000)
    public void test_appendXmlPrologThenLargeBuffer_throwsNullPointerExceptionOnNullOutputStream()
            throws Throwable {
        XmlStreamWriter xmlWriter = new XmlStreamWriter((OutputStream) null);
        Writer writer = xmlWriter.append((CharSequence) "<?xml");

        CharBuffer largeBuffer = CharBuffer.allocate(8192);
        // Appending a buffer large enough to fill the prolog window forces
        // encoding detection, which cannot write to the null OutputStream
        try {
            writer.append((CharSequence) largeBuffer);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("java.io.Writer", e);
        }
    }

    /**
     * Calling getEncoding() before any write (charset field is still null)
     * throws NullPointerException inside XmlStreamWriter.
     */
    @Test(timeout = 4000)
    public void test_getEncodingBeforeWrite_throwsNullPointerException() throws Throwable {
        File tempFile = MockFile.createTempFile("r>mbV&/Nog5v0I%", "r>mbV&/Nog5v0I%");
        XmlStreamWriter writer = new XmlStreamWriter(tempFile);

        try {
            writer.getEncoding();
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.io.output.XmlStreamWriter", e);
        }
    }

    /**
     * Closing the writer twice is safe; after the first close the charset is
     * set to the default (UTF-8), so getEncoding() returns "UTF-8".
     */
    @Test(timeout = 4000)
    public void test_doubleClose_encodingIsUtf8() throws Throwable {
        MockFile mockFile = new MockFile("nz", "nz");
        XmlStreamWriter writer = new XmlStreamWriter(mockFile);

        writer.close();
        writer.close();

        assertEquals(DEFAULT_ENCODING, writer.getEncoding());
    }
}
