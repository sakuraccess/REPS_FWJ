object apiDataFetching {
  import java.net.{HttpURLConnection, URL}
  import scala.io.Source

  private val apiToken = "716a34f1e2b69037ef446727c64d598a6ccf554b"
  private val baseUrl = "https://www.renewables.ninja/api/data"

  def fetchData(): Unit = {
    val parametersPv = Seq(
      "local_time=true",
      "header=true",
      "lat=63.2468",
      "lon=25.9209",
      "date_from=2022-01-01",
      "date_to=2022-12-31",
      "dataset=merra2",
      "capacity=33",
      "system_loss=0.1",
      "tracking=0",
      "tilt=35",
      "azim=180",
      "format=csv",
      "raw=false"
    ).mkString("&")

    val urlPv = new URL(s"$baseUrl/pv?$parametersPv")
    val connectionPv = urlPv.openConnection().asInstanceOf[HttpURLConnection]
    connectionPv.setRequestMethod("GET")
    connectionPv.setRequestProperty("Authorization", s"Token $apiToken")
    connectionPv.getResponseCode match {
      case 200 =>
        val csvData = Source.fromInputStream(connectionPv.getInputStream).mkString
//        println("Received CSV data:")
        saveCsvToFile(csvData, "pv_data_output.csv")
      case code =>
        println(s"HTTP error code: $code")
        println(Source.fromInputStream(connectionPv.getErrorStream).mkString)
    }
    connectionPv.disconnect()

//api/data/wind?&lat=56&lon=-3&date_from=2014-01-01&date_to=2014-02-28&capacity=1&dataset=merra2&height=100&turbine=Vestas+V80+2000&format=json
    val parametersWind = Seq(
      "local_time=true",
      "header=true",
      "lat=63.2468",
      "lon=25.9209",
      "date_from=2023-01-01",
      "date_to=2023-12-31",
      "dataset=merra2",
      "capacity=150000",
      "height=80",
      "turbine=Vestas+V90+2000",
      "format=csv",
      "raw=false"
    ).mkString("&")

    val urlWind = new URL(s"$baseUrl/wind?$parametersWind")
    val connectionWind = urlWind.openConnection().asInstanceOf[HttpURLConnection]
    connectionWind.setRequestMethod("GET")
    connectionWind.setRequestProperty("Authorization", s"Token $apiToken")
    connectionWind.getResponseCode match {
      case 200 =>
        val csvData = Source.fromInputStream(connectionWind.getInputStream).mkString
        //        println("Received CSV data:")
        saveCsvToFile(csvData, "wind_data_output.csv")
      case code =>
        println(s"HTTP error code: $code")
        println(Source.fromInputStream(connectionWind.getErrorStream).mkString)
    }
    connectionWind.disconnect()
  }

  private def saveCsvToFile(csvData: String, fileName: String): Unit = {
    //  val fileName = "solar_data_output.csv"
    val pw = new java.io.PrintWriter(new java.io.File(fileName))
    try {
      pw.write(csvData)
      println(s"CSV data has been saved to $fileName")
    } finally {
      pw.close()
    }
  }
}


