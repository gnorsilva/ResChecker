import sbt._

class ResourceChecker(info: ProjectInfo) extends DefaultProject(info)
{

	val junitInterface = "com.novocode" % "junit-interface" % "0.6" % "test->default"
	override def mainJavaSourcePath = "src"	
	override def testJavaSourcePath = "tests"
	override def testResourcesPath = "test_assets"
	override def outputPath = "bin"
	override def mainCompilePath = "bin"
	override def testCompilePath = "bin"

}
