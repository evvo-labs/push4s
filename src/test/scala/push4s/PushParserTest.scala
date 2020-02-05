package push4s

import org.scalatest.{FunSpec, Matchers}
import org.scalatest.Assertions._

class PushParserTest extends FunSpec with Matchers {
  describe("The parser") {
    it("returns None if parens aren't balanced") {
      PushParser.parse("(") shouldBe None
      PushParser.parse("(()") shouldBe None
      PushParser.parse("()(") shouldBe None
      PushParser.parse("(3 4 5 (3") shouldBe None
    }

    it ("should work on the empty string") {
      PushParser.parse("").get shouldBe List()
    }

    it ("should work on single atoms") {
      PushParser.parse("3").get shouldBe List(PushInt(3))
      PushParser.parse("3.0").get shouldBe List(PushFloat(3.0f))
      PushParser.parse("true").get shouldBe List(PushBoolean(true))
      PushParser.parse("\"h\"").get shouldBe List(PushString("h"))
    }

    it ("can parse instructions it knows about") {
      PushParser.parse("boolean_not").get shouldBe List(PushInstruction.fromName("boolean_not").get)
    }

    it ("can't parse instructions it doesn't know about") {
      PushParser.parse("asdfasdfasdfasdfasdfasd") shouldBe None
    }

    it ("ignores extra whitespace") {
      PushParser.parse("3 1 3").get shouldBe
      PushParser.parse("3           1  3").get
    }

    it ("can parse multiple atoms") {
      PushParser.parse("3 3.0 true \"h\"").get shouldBe List(
        PushInt(3),
        PushFloat(3.0f),
        PushBoolean(true),
        PushString("h")
      )
    }

    it ("can parse lists") {
      PushParser.parse("()").get shouldBe List(PushList(List()))

      PushParser.parse("0 (1 2) (3 (4))").get shouldBe List(
        PushInt(0),
        PushList(List(PushInt(1), PushInt(2))),
        PushList(List(PushInt(3), PushList(List(PushInt(4)))))
      )
    }

  }

}
