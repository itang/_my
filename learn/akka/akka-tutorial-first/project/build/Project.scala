import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) 
	with AkkaProject{
	//val localReps = "Local repos" at "http://localhost/nexus/content/groups/public/"
	
	val akkaSTM = akkaModule("stm")
	val akkaRemote = akkaModule("remote")
}

