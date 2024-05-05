import scala.io.Source

object CSVconvertMatrix {
  def csvToMatrix(filePath: String): List[List[String]] = {
    val lines = Source.fromFile(filePath).getLines().toList
    val data = lines.drop(4).map(_.split(",").toList.map(_.trim))  // Skip the header
//    println(s"Sample data after header: ${data.take(3)}")  // Print the first 3 rows to check
    data
  }
}

