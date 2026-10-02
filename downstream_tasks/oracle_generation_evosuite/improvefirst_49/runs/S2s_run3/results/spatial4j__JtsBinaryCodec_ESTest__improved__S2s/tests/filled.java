/*
 * Improved version of the EvoSuite-generated test for JtsBinaryCodec.
 * Refactored for readability: descriptive method names, meaningful variable
 * names, and brief comments explaining the intent of each test case.
 */

package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.PipedOutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileOutputStream;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.io.jts.JtsBinaryCodec;
import org.locationtech.spatial4j.shape.Shape;
import org.locationtech.spatial4j.shape.impl.PointImpl;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(
    mockJVMNonDeterminism = true,
    useVFS = true,
    useVNET = true,
    resetStaticState = true,
    separateClassLoader = false
)
public class JtsBinaryCodec_ESTest extends JtsBinaryCodec_ESTest_scaffolding {

  // -----------------------------------------------------------------------
  // writePoint
  // -----------------------------------------------------------------------

  /**
   * Writing a point to a real output stream succeeds, and the point's
   * longitude coordinate is not mutated by the write operation.
   */
  @Test(timeout = 4000)
  public void testWritePoint_preservesLongitude() throws Throwable {
    JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
    JtsSpatialContext ctx = factory.newSpatialContext();
    JtsBinaryCodec codec = new JtsBinaryCodec(ctx, factory);

    // Use a mock print stream as the backing output to avoid real I/O.
    MockPrintStream backingStream = new MockPrintStream("Expected initial read of one byte, not: ");
    ObjectOutputStream output = new ObjectOutputStream(backingStream);

    PointImpl point = new PointImpl(10.0, 3877.7633, ctx);
    codec.writePoint(output, point);

    // The write must not alter the point's longitude.
    assertEquals(10.0, point.getX(), 0.0);
  }

  // -----------------------------------------------------------------------
  // writeShapeByTypeIfSupported
  // -----------------------------------------------------------------------

  /**
   * When the shape argument is null, writeShapeByTypeIfSupported returns
   * false regardless of the type byte, because null is not a supported shape.
   */
  @Test(timeout = 4000)
  public void testWriteShapeByTypeIfSupported_nullShapeReturnsFalse() throws Throwable {
    JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
    JtsSpatialContext ctx = new JtsSpatialContext(factory);
    JtsBinaryCodec codec = new JtsBinaryCodec(ctx, factory);

    // Connect through a piped stream to supply a writable DataOutput.
    PipedOutputStream pipe = new PipedOutputStream();
    DataOutputStream output = new DataOutputStream(pipe);

    boolean supported = codec.writeShapeByTypeIfSupported(output, (Shape) null, (byte) 0);

    assertFalse(supported);
  }

  /**
   * When a PointImpl is written with type byte 5 (TYPE_GEOM), the codec
   * recognises it as a supported shape and returns true.
   */
  @Test(timeout = 4000)
  public void testWriteShapeByTypeIfSupported_pointWithTypeGeomReturnsTrue() throws Throwable {
    JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
    JtsSpatialContext ctx = new JtsSpatialContext(factory);
    JtsBinaryCodec codec = new JtsBinaryCodec(ctx, factory);

    // Write to a mock file to avoid needing a real path.
    MockFileOutputStream fileOut = new MockFileOutputStream("Expected initial read of one byte, not: ");
    DataOutputStream output = new DataOutputStream(fileOut);

    PointImpl originPoint = new PointImpl(0, 0, ctx);
    boolean supported = codec.writeShapeByTypeIfSupported(output, originPoint, (byte) 5);

    assertTrue(supported);
  }

  // -----------------------------------------------------------------------
  // readShapeByTypeIfSupported
  // -----------------------------------------------------------------------

  /**
   * When the type byte does not correspond to any known shape type (e.g. 81),
   * readShapeByTypeIfSupported returns null rather than throwing.
   */
  @Test(timeout = 4000)
  public void testReadShapeByTypeIfSupported_unknownTypeByteReturnsNull() throws Throwable {
    JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
    JtsSpatialContext ctx = factory.newSpatialContext();
    JtsBinaryCodec codec = new JtsBinaryCodec(ctx, factory);

    byte[] emptyData = new byte[5];
    DataInputStream input = new DataInputStream(new ByteArrayInputStream(emptyData));

    Shape result = codec.readShapeByTypeIfSupported(input, (byte) 81);

    assertNull(result);
  }

  /**
   * Reading with type byte 5 (TYPE_GEOM) from an all-zero / empty stream
   * triggers WKB parsing which fails with a RuntimeException wrapping the
   * underlying parse error ("error reading WKT").
   */
  @Test(timeout = 4000)
  public void testReadShapeByTypeIfSupported_typeGeomOnEmptyDataThrowsRuntimeException()
      throws Throwable {
    JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
    JtsSpatialContext ctx = factory.newSpatialContext();
    JtsBinaryCodec codec = new JtsBinaryCodec(ctx, factory);

    byte[] emptyData = new byte[5];
    DataInputStream input = new DataInputStream(new ByteArrayInputStream(emptyData));

    // Undeclared exception!
    try {
      codec.readShapeByTypeIfSupported(input, (byte) 5);
      fail("Expecting exception: RuntimeException");
    } catch (RuntimeException e) {
      //
      // error reading WKT
      //
    }
  }

  // -----------------------------------------------------------------------
  // typeForShape
  // -----------------------------------------------------------------------

  /**
   * typeForShape(null) returns byte 5 (TYPE_GEOM), which is the fallback
   * type used for shapes not recognised by the base class.
   */
  @Test(timeout = 4000)
  public void testTypeForShape_nullShapeReturnsTypeGeomByte() throws Throwable {
    JtsSpatialContext ctx = JtsSpatialContext.GEO;
    JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
    JtsBinaryCodec codec = new JtsBinaryCodec(ctx, factory);

    byte typeCode = codec.typeForShape((Shape) null);

    assertEquals(5, typeCode);
  }

  // -----------------------------------------------------------------------
  // writeShape – null DataOutput
  // -----------------------------------------------------------------------

  /**
   * Passing a null DataOutput to writeShape causes a NullPointerException
   * inside the base-class BinaryCodec before any JTS-specific code runs.
   */
  @Test(timeout = 4000)
  public void testWriteShape_nullDataOutputThrowsNullPointerException() throws Throwable {
    JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
    JtsSpatialContext ctx = JtsSpatialContext.GEO;
    PointImpl originPoint = new PointImpl(0, 0, ctx);
    JtsBinaryCodec codec = new JtsBinaryCodec(ctx, factory);

    // Undeclared exception!
    try {
      codec.writeShape((DataOutput) null, originPoint);
      fail("Expecting exception: NullPointerException");
    } catch (NullPointerException e) {
      //
      // no message in exception (getMessage() returned null)
      //
    }
  }

  // -----------------------------------------------------------------------
  // readDim – null DataInput
  // -----------------------------------------------------------------------

  /**
   * Passing a null DataInput to readDim causes a NullPointerException inside
   * the base-class BinaryCodec (double-precision path, default factory).
   */
  @Test(timeout = 4000)
  public void testReadDim_nullDataInputThrowsNullPointerException() throws Throwable {
    JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
    JtsSpatialContext ctx = new JtsSpatialContext(factory);
    JtsBinaryCodec codec = new JtsBinaryCodec(ctx, factory);

    // Undeclared exception!
    try {
      codec.readDim((DataInput) null);
      fail("Expecting exception: NullPointerException");
    } catch (NullPointerException e) {
      //
      // no message in exception (getMessage() returned null)
      //
    }
  }

  // -----------------------------------------------------------------------
  // writeDim – FLOATING_SINGLE precision, null DataOutput
  // -----------------------------------------------------------------------

  /**
   * When the factory is configured with FLOATING_SINGLE precision, writeDim
   * takes the float-write code path inside JtsBinaryCodec. Passing a null
   * DataOutput still triggers a NullPointerException, this time thrown from
   * within JtsBinaryCodec itself (not the base class).
   */
  @Test(timeout = 4000)
  public void testWriteDim_floatPrecisionWithNullOutputThrowsNullPointerException()
      throws Throwable {
    JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
    // Configure single-precision floating point so JtsBinaryCodec uses writeFloat.
    factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
    JtsSpatialContext ctx = factory.newSpatialContext();
    JtsBinaryCodec codec = new JtsBinaryCodec(ctx, factory);

    // Undeclared exception!
    try {
      codec.writeDim((DataOutput) null, 0);
      fail("Expecting exception: NullPointerException");
    } catch (NullPointerException e) {
      //
      // no message in exception (getMessage() returned null)
      //
    }
  }
}
