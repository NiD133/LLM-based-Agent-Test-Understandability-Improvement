/*
 * Test suite for org.jsoup.nodes.Entities.
 *
 * Entities provides HTML/XML escape and unescape routines plus lookup helpers
 * for named character references. The tests below exercise:
 *   - the high-level escape(...) overloads (default settings and OutputSettings),
 *   - the low-level escape(QuietAppendable, ...) sink variant,
 *   - unescape(...),
 *   - the named-entity lookup helpers (isNamedEntity, isBaseNamedEntity,
 *     getByName, codepointsForName, findPrefix),
 *   - EscapeMode.nameForCodepoint, and
 *   - CoreCharset.byName.
 *
 * The expected outcomes mirror the original EvoSuite-generated suite; only the
 * structure, naming and documentation have been improved for readability.
 */
package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.BufferedOutputStream;
import java.io.PipedOutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest extends Entities_ESTest_scaffolding {

  // ---------------------------------------------------------------------------
  // escape(String) - default settings (UTF-8, base entities)
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void escapeEmptyStringReturnsEmpty()  throws Throwable  {
      String escaped = Entities.escape("");
      assertEquals("", escaped);
  }

  @Test(timeout = 4000)
  public void escapeNullStringReturnsEmpty()  throws Throwable  {
      String escaped = Entities.escape((String) null);
      assertEquals("", escaped);
  }

  // ---------------------------------------------------------------------------
  // escape(String, OutputSettings) - escaping driven by output settings
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void escapeWithDefaultHtmlSettingsEscapesQuoteAndLessThan()  throws Throwable  {
      Document.OutputSettings settings = new Document.OutputSettings();
      String escaped = Entities.escape("^du^X\"w<", settings);
      assertEquals("^du^X&quot;w&lt;", escaped);
  }

  @Test(timeout = 4000)
  public void escapeWithDefaultHtmlSettingsUsesAposForSingleQuote()  throws Throwable  {
      Document.OutputSettings settings = new Document.OutputSettings();
      String escaped = Entities.escape("K'?wQt&", settings);
      assertEquals("K&apos;?wQt&amp;", escaped);
  }

  @Test(timeout = 4000)
  public void escapeWithXmlSyntaxEscapesAngleBrackets()  throws Throwable  {
      Document.OutputSettings settings = new Document.OutputSettings();
      settings.syntax(Document.OutputSettings.Syntax.xml);
      String escaped = Entities.escape("e\n//]<]>", settings);
      assertEquals("e\n//]&lt;]&gt;", escaped);
  }

  @Test(timeout = 4000)
  public void escapeWithXmlSyntaxUsesNumericAposEscape()  throws Throwable  {
      Document.OutputSettings settings = new Document.OutputSettings();
      Document.OutputSettings sameSettings = settings.syntax(Document.OutputSettings.Syntax.xml);
      String escaped = Entities.escape("K'?wQt&", sameSettings);
      assertEquals("K&#x27;?wQt&amp;", escaped);
  }

  @Test(timeout = 4000)
  public void escapeWithAsciiCharsetEscapesSectionSignAsNamedEntity()  throws Throwable  {
      Document.OutputSettings settings = new Document.OutputSettings();
      Document.OutputSettings asciiSettings = settings.charset("ascii");
      String escaped = Entities.escape("kp1D(Cu%~vB8caHD§", asciiSettings);
      assertEquals("kp1D(Cu%~vB8caHD&sect;", escaped);
  }

  @Test(timeout = 4000)
  public void escapeWithAsciiCharsetEscapesNonAsciiAsHexReference()  throws Throwable  {
      Document.OutputSettings settings = new Document.OutputSettings();
      settings.charset("ascii");
      String escaped = Entities.escape("ethiH_p3≻", settings);
      assertEquals("ethiH_p3&#x227b;", escaped);
  }

  @Test(timeout = 4000)
  public void escapeNonBreakingSpaceUsesNbspByDefault()  throws Throwable  {
      Document.OutputSettings settings = new Document.OutputSettings();
      String escaped = Entities.escape("yen ", settings);
      assertEquals("yen&nbsp;", escaped);
  }

  @Test(timeout = 4000)
  public void escapeNonBreakingSpaceUsesNumericEscapeInXhtmlMode()  throws Throwable  {
      Document.OutputSettings settings = new Document.OutputSettings();
      settings.escapeMode(Entities.EscapeMode.xhtml);
      String escaped = Entities.escape("yen ", settings);
      assertEquals("yen&#xa0;", escaped);
  }

  // ---------------------------------------------------------------------------
  // escape(QuietAppendable, String, OutputSettings, int) - low-level sink variant.
  // These exercise the streaming form against various appendable targets; the
  // assertions confirm the supplied OutputSettings are left at their defaults.
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void escapeToPrintStreamKeepsDefaultHtmlSyntax()  throws Throwable  {
      PipedOutputStream pipedOutputStream = new PipedOutputStream();
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(pipedOutputStream);
      MockPrintStream printStream = new MockPrintStream(bufferedOutputStream, false);
      QuietAppendable sink = QuietAppendable.wrap(printStream);
      Document.OutputSettings settings = new Document.OutputSettings();

      Entities.escape(sink, "20,HVe0[Tl'l>TR", settings, (-1814));

      assertEquals(Document.OutputSettings.Syntax.html, settings.syntax());
  }

  @Test(timeout = 4000)
  public void escapeToFileWriterKeepsDefaultHtmlSyntax()  throws Throwable  {
      MockFileWriter fileWriter = new MockFileWriter("20,HVe0[Tl&apos;l&gt;TR");
      QuietAppendable sink = QuietAppendable.wrap(fileWriter);
      Document.OutputSettings settings = new Document.OutputSettings();

      Entities.escape(sink, "20,HVe0[Tl'l>TR", settings, (-991));

      assertEquals(Document.OutputSettings.Syntax.html, settings.syntax());
  }

  @Test(timeout = 4000)
  public void escapeToPrintStreamKeepsDefaultBaseEscapeMode()  throws Throwable  {
      MockPrintStream printStream = new MockPrintStream("[:9;Wd@P3x0sfFM/");
      QuietAppendable sink = QuietAppendable.wrap(printStream);
      Document.OutputSettings settings = new Document.OutputSettings();

      Entities.escape(sink, "Must be false", settings, (-249532396));

      assertEquals(Entities.EscapeMode.base, settings.escapeMode());
  }

  @Test(timeout = 4000)
  public void escapeToFileWriterKeepsDefaultOutlineDisabled()  throws Throwable  {
      MockFileWriter fileWriter = new MockFileWriter("Zect     _ @          ");
      QuietAppendable sink = QuietAppendable.wrap(fileWriter);
      Document.OutputSettings settings = new Document.OutputSettings();

      Entities.escape(sink, "Zect     _ @          ", settings, (-20));

      assertFalse(settings.outline());
  }

  @Test(timeout = 4000)
  public void escapeWhitespaceToFileWriterKeepsDefaultHtmlSyntax()  throws Throwable  {
      MockFileWriter fileWriter = new MockFileWriter("         ");
      QuietAppendable sink = QuietAppendable.wrap(fileWriter);
      Document.OutputSettings settings = new Document.OutputSettings();

      Entities.escape(sink, "         ", settings, (-67));

      assertEquals(Document.OutputSettings.Syntax.html, settings.syntax());
  }

  @Test(timeout = 4000)
  public void escapeToFileWriterKeepsDefaultIndentAmount()  throws Throwable  {
      MockFileWriter fileWriter = new MockFileWriter("e\nY]<]>");
      QuietAppendable sink = QuietAppendable.wrap(fileWriter);
      Document.OutputSettings settings = new Document.OutputSettings();

      Entities.escape(sink, "v{}++=\"D1mJ", settings, 76);

      assertEquals(1, settings.indentAmount());
  }

  // ---------------------------------------------------------------------------
  // unescape(String)
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void unescapeNamedEntityReturnsCharacter()  throws Throwable  {
      String unescaped = Entities.unescape("&lt;");
      assertEquals("<", unescaped);
  }

  // ---------------------------------------------------------------------------
  // findPrefix(String) - longest base entity name that is a prefix of the input
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void findPrefixReturnsMatchingEntityName()  throws Throwable  {
      String prefix = Entities.findPrefix("shy");
      assertEquals("shy", prefix);
  }

  @Test(timeout = 4000)
  public void findPrefixReturnsEmptyWhenNoEntityMatches()  throws Throwable  {
      String prefix = Entities.findPrefix("a%VaysnL|7L=rC");
      assertEquals("", prefix);
  }

  // ---------------------------------------------------------------------------
  // codepointsForName(String, int[])
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void codepointsForUnknownNameReturnsZero()  throws Throwable  {
      int[] codepoints = new int[5];
      int count = Entities.codepointsForName("", codepoints);
      assertEquals(0, count);
  }

  // ---------------------------------------------------------------------------
  // getByName(String) - resolve a named entity to its character(s)
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void getByNameResolvesKnownEntity()  throws Throwable  {
      String value = Entities.getByName("QUOT");
      assertEquals("\"", value);
      assertNotNull(value);
  }

  @Test(timeout = 4000)
  public void getByNameReturnsEmptyForUnknownEntity()  throws Throwable  {
      String value = Entities.getByName("degGT=k&quot;]+G");
      assertNotNull(value);
      assertEquals("", value);
  }

  // ---------------------------------------------------------------------------
  // isNamedEntity / isBaseNamedEntity
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void isBaseNamedEntityFalseForUnknownName()  throws Throwable  {
      boolean isBase = Entities.isBaseNamedEntity("LQ2");
      assertFalse(isBase);
  }

  @Test(timeout = 4000)
  public void isNamedEntityTrueForKnownExtendedName()  throws Throwable  {
      boolean isNamed = Entities.isNamedEntity("sup2");
      assertTrue(isNamed);
  }

  @Test(timeout = 4000)
  public void isNamedEntityFalseForUnknownName()  throws Throwable  {
      boolean isNamed = Entities.isNamedEntity("|TM1yKJ");
      assertFalse(isNamed);
  }

  // ---------------------------------------------------------------------------
  // EscapeMode.nameForCodepoint(int)
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void baseModeNamesAmpersandCodepoint()  throws Throwable  {
      Entities.EscapeMode baseMode = Entities.EscapeMode.base;
      String name = baseMode.nameForCodepoint(38); // '&'
      assertEquals("amp", name);
  }

  @Test(timeout = 4000)
  public void xhtmlModeNamesGreaterThanCodepoint()  throws Throwable  {
      Entities.EscapeMode xhtmlMode = Entities.EscapeMode.xhtml;
      String name = xhtmlMode.nameForCodepoint(62); // '>'
      assertEquals("gt", name);
  }

  // ---------------------------------------------------------------------------
  // CoreCharset.byName(String)
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void coreCharsetByNameAcceptsEmptyName()  throws Throwable  {
      Entities.CoreCharset.byName("");
  }
}
