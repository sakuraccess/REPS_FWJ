import scala.io.Source

class CSVconvertMatrix extends App{
  def csvToMatrix(filePath: String): List[List[String]] =
    Source.fromFile(filePath)
      .getLines()
      .map(_.split(",").map(_.trim).toList) // Split each line by comma and trim spaces
      .toList
  
} // Close class definition
