object Main {
  import scala.io.StdIn.readLine
//  import scala.io.Source

  def main(args: Array[String]): Unit = {
    println("Welcome to the Renewable Energy Plant System!")
    apiDataFetching.fetchData()

    val windData = CSVconvertMatrix.csvToMatrix("wind_data_output.csv")
    val pvData = CSVconvertMatrix.csvToMatrix("pv_data_output.csv")

    var option: String = ""
    while (option != "4") {
      println("\nPlease choose an option:")
      println("1: Generate the graph for dataset")
      println("2: Analyze the data")
      println("3: Detect errors in the data")
      println("4: Exit")
      println("Enter your choice (1-4):")

      option = readLine()

      option match {
        case "1" => generateGraph()
        case "2" => analyzeData(windData)
        case "3" => detectErrors(windData)
        case "4" => println("Exiting system...")
        case _ => println("Invalid option. Please enter a number between 1 and 4.")
      }
    }
  }

  private def generateGraph(): Unit = {
    println("Generating graph for the dataset...")
    // Placeholder for graph generation logic
  }

  private def analyzeData(data: List[List[String]]): Unit = {
    println("Analyzing data...")
//    val data = CSVconvertMatrix.csvToMatrix("wind.csv")
    println("Choose the time frame for analysis:\n1. Hourly\n2. Daily\n3. Weekly\n4. Monthly")
    val timeFrame = readLine()

    val selectedData = timeFrame match {
      case "1" => DataFilter.datahour(data)
      case "2" => DataFilter.dataday(data)
      case "3" => DataFilter.dataweek(data)
      case "4" => DataFilter.datamonth(data)
      case _ =>
        println("Invalid choice, defaulting to Daily data.")
        DataFilter.dataday(data)
    }

    println("Choose the type of statistical analysis:\n1. Average\n2. Median\n3. Mode\n4. Range\n5. Mid Value")
    val analysisType = readLine()
    val result = analysisType match {
      case "1" => s"Average Power: ${new dataanalysis().average(selectedData)}"
      case "2" => s"Median Power: ${new dataanalysis().median(selectedData)}"
      case "3" => s"Mode Power: ${new dataanalysis().mode(selectedData)}"
      case "4" => s"Range of Power: ${new dataanalysis().range(selectedData)}"
      case "5" => s"Mid Value of Power: ${new dataanalysis().midValue(selectedData)}"
      case _ =>
        println("Invalid choice, calculating Average by default.")
        s"Average Power: ${new dataanalysis().average(selectedData)}"
    }

    println(result)
  }

  private def detectErrors(data: List[List[String]]): Unit = {
    println("Detecting errors in the data...")
//    val data = CSVconvertMatrix.csvToMatrix("wind.csv")
    println("Choose the time frame for error detection:\n1. Hourly\n2. Daily\n3. Weekly\n4. Monthly")
    val timeFrame = readLine()

    val selectedData = timeFrame match {
      case "1" => DataFilter.datahour(data)
      case "2" => DataFilter.dataday(data)
      case "3" => DataFilter.dataweek(data)
      case "4" => DataFilter.datamonth(data)
      case _ =>
        println("Invalid choice, defaulting to Daily data.")
        DataFilter.dataday(data)
    }

    val analysis = new dataanalysis()
    val minValue = analysis.median(selectedData)
    val avgValue = analysis.average(selectedData)
    val error = avgValue - minValue

    println(s"Error detection: Minimal value is $minValue, you should add $error power at that time.")
  }
}