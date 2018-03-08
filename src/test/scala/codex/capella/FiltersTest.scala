package codex.capella

import codex.capella.Pipeline._
import org.scalatest.{FlatSpec, Matchers}

class FiltersTest extends FlatSpec with Matchers {

  val baseUrl = "https://capella.pics/07d4fa39-7465-474a-9e01-15a71bb71c32"

  "FiltersTest.pixelize" should "return valid string" in {
    Filters.pixelize(10)(baseUrl) shouldBe s"$baseUrl/pixelize/10"
  }

  "FiltersTest.resize" should "return valid string" in {
    Filters.resize(10)(baseUrl) shouldBe s"$baseUrl/resize/10"
    Filters.resize(10, 20)(baseUrl) shouldBe s"$baseUrl/resize/10x20"
    Filters.resize(10, Some(20))(baseUrl) shouldBe s"$baseUrl/resize/10x20"
  }

  "FiltersTest.crop" should "return valid string" in {
    Filters.crop(10)(baseUrl) shouldBe s"$baseUrl/crop/10"
    Filters.crop(10, 20)(baseUrl) shouldBe s"$baseUrl/crop/10x20"
    Filters.crop(10, Some(20))(baseUrl) shouldBe s"$baseUrl/crop/10x20"
    Filters.crop(10, 20, (30, 40))(baseUrl) shouldBe s"$baseUrl/crop/10x20&30,40"
    Filters.crop(10, 20, 30, 40)(baseUrl) shouldBe s"$baseUrl/crop/10x20&30,40"
    Filters.crop(10, Some(20), Some((30, 40)))(baseUrl) shouldBe s"$baseUrl/crop/10x20&30,40"
  }

  "FiltersTest chaining" should "return valid string" in {
    baseUrl |> Filters.pixelize(10) |> Filters.resize(10, 20) shouldBe s"$baseUrl/pixelize/10/resize/10x20"
    baseUrl |> Filters.resize(10) |> Filters.crop(10, 20) shouldBe s"$baseUrl/resize/10/crop/10x20"
    baseUrl |> Filters.pixelize(10) shouldBe s"$baseUrl/pixelize/10"
  }

  "FiltersTest chaining" should "return valid string through applyFilters method" in {
    Filters.applyFilters(baseUrl, Seq(
      Filters.pixelize(10), Filters.resize(10, 20)
    )) shouldBe s"$baseUrl/pixelize/10/resize/10x20"

    Filters.applyFilters(baseUrl, Seq(
      Filters.resize(10),
      Filters.crop(10, 20)
    )) shouldBe s"$baseUrl/resize/10/crop/10x20"

    Filters.applyFilters(baseUrl, Seq(Filters.pixelize(10))) shouldBe s"$baseUrl/pixelize/10"
  }

}
