object Main {
  import scala.io.StdIn.readLine

  def main(args: Array[String]): Unit = {
    println("Welcome to renewable energy plant system!")
    var option: String = ""

    while (option != "4") {
      println("Option 1: generate the graph for dataset")
      println("Option 2: analysis the data")
      println("Option 3: Detect error")
      option = scala.io.StdIn.readLine() // 从用户终端获取输入

      if (option == "1") {
        
      } else if (option == "2") {
        
      } else if (option == "3") {
        
      }
    }
  }
}