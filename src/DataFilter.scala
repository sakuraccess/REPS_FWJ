class DataFilter {
  def extractSolarData(data: List[List[String]]): List[Double] = {
    data.map { case List(_, _, powerGeneration) =>
      if (powerGeneration == "0") 0.0
      else powerGeneration.toDouble
    }
  }

  def datahour(data: List[List[String]]): List[Double] = {
    val solarData = extractSolarData(data)
    solarData.grouped(4).toList.map(hourlyData =>
      if (hourlyData.nonEmpty) hourlyData.sum / hourlyData.size else 0
    )
  }

  def dataday(data: List[List[String]]): List[Double] = {
    val solarData = extractSolarData(data)
    solarData.grouped(4 * 24).toList.map(dailyData =>
      if (dailyData.nonEmpty) dailyData.sum / dailyData.size else 0
    )
  }

  def dataweek(data: List[List[String]]): List[Double] = {
    val solarData = extractSolarData(data)
    solarData.grouped(4 * 24 * 7).toList.map(weeklyData =>
      if (weeklyData.nonEmpty) weeklyData.sum / weeklyData.size else 0
    )
  }

  def datamonth(data: List[List[String]]): List[Double] = {
    val solarData = extractSolarData(data)
    // Assuming each month approximately as 30 days
    solarData.grouped(4 * 24 * 30).toList.map(monthlyData =>
      if (monthlyData.nonEmpty) monthlyData.sum / monthlyData.size else 0
    )
  }

}
