import scala.io.Source

object CSVconvertMatrix{
  def csvToMatrix(filePath: String): List[List[String]] =
    Source.fromFile(filePath)
      .getLines()
      .map(_.split(",").map(_.trim).toList) // Split each line by comma and trim spaces
      .toList
} // Close class definition
