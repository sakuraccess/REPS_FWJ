import scala.io.Source

object CSVconvertMatrix extends App{
  def csvToMatrix(filePath: String): List[List[String]] =
    Source.fromFile(filePath)
      .getLines()
      .map(_.split(",").map(_.trim).toList) // Split each line by comma and trim spaces
      .toList
  val example = csvToMatrix("C:/Users/86138/Desktop/DMM2/solar.csv")
  println(example)
} // Close class definition
