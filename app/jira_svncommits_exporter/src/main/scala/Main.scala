import java.io._
import java.util.Scanner
import java.util.logging._

import scala.io._
import scala.collection.mutable.ListBuffer

import scalax.io.Implicits._ 

object config {
	private val conf = scala.xml.XML.loadFile("./config/conf.xml")
	val name =  new File((conf \\ "name") .text)
	val output = new File((conf \\ "output") .text)
	val from = new File((conf \\ "from") .text)
	val commitsDir = new File((conf \\ "commitsDir") .text)// new File("./commits")
	val logger :Logger = Logger.getLogger(this.getClass.getName);
	val branch = (conf \\ "branch") .text
	val projectType = (conf \\ "type").text 
    
}

import config._
	
object Main {
	
	def main(args:Array[String]):Unit = {
		displayInfo()
		
		val in : Scanner = new Scanner(System.in);
    	var break = false
    	while(!break){
    		print("*操作: help, clean, init, build, exit: ")
	    	in.nextLine().trim match {
	    		case "help" =>
	    			println("usage:")
	    			println("\thelp :" + "列出所有操作")
	    			println("\tclean :" + "清空输出目录数据")
	    			println("\tinit :" + "初始化操作")
	    			println("\tbuild :" + "通过svncommits 创建输出文件目录数据")
	    			println("\texit :" + "程序退出")
	    		case "clean" =>
	    			config.output.deleteRecursively
	    			logger.info("clean操作完成!")
	    		case "init" =>
	    			init()
	    			logger.info("init操作完成!")
	    		case "build" =>
	    			commitsDir.listFiles.foreach(new Commits(_).execute())
	    			logger.info("build操作完成!")
	    		case "exit" =>
	    			println("退出!")
	    			break = true
	    		case _ => println("请输入正确的操作!")
	    	}
		}
	}
	
	private def displayInfo(){
		println("@%s(from svn commits)".format(config.name) )
		println("配置:")
		println("\tfrom: " + config.from.getAbsolutePath)
		println("\tbranch: " + config.branch)
		println("\toutput: " + config.output.getAbsolutePath)
		println("\tsvn commits files (in %s):".format(commitsDir.getAbsolutePath))
		for(file <- commitsDir.listFiles)
    		println("\t\t"+ file.getName)
	}
	
	private def init(){
		if(!config.output.exists()) config.output.mkdirs()
	}
}

trait Executable { def execute() : Unit }

class Commits(val svnLog : File) extends  Executable {
	def who = svnLog.getName
	
	def execute() = getCommits().foreach ( _.execute() )
	
	private def getCommits() = {
		val commits: ListBuffer[Commit] = new ListBuffer[Commit]()
		getCommitsLines().foreach { line =>
			val p = line.split("\\s+")
			if(p.length >= 2){
				val action = p(0).trim
				val path =  p(1).trim
				commits += new Commit(action, path, who)
			}
		}
		commits.toArray
	}
	
	private def getCommitsLines(): Array[String] = {
		val  list : ListBuffer[String] = new ListBuffer[String]()
		try{
			val reader =  new BufferedReader (new InputStreamReader(new FileInputStream(svnLog) ))
			var line = reader.readLine
			while(line != null){
				list += line.trim()
				line = reader.readLine
			}
			reader.close()
		}catch{
			case e => logger.warning("获取commits line错误:" + e.getMessage)
		}
		list.toArray
	}
}

class Commit(val name: String , val svnfile: String, val who:String) extends  Executable {
	def execute() {
		try{
			val fp = filePath(svnfile)
			name match {
				case "ADD" => processFileTran(fp)
				case "MODIFY" => processFileTran(fp)
				case "DEL" => processFileDel(fp)
				case _ =>
			}
		}catch{
			case e => 
				e.printStackTrace()
				logger.warning("出现错误:" + e.getMessage )
		}
	}

	private def processFileDel(fp: Tuple2[Array[File],Array[File]]) = {
		fp._2.foreach{ t => t match{
				case file if t.isFile => file.createNewFile()
				case dir if t.isDirectory => 
					if(!dir.exists)
						dir.mkdirs()
				case _ => 
			}
			
			logger.info("setting delete file info" + t.getAbsolutePath)	
			t.renameTo(new File(t.getParentFile, "___" +  t.getName))
		}
	}
	
	private def processFileTran(fp: Tuple2[Array[File],Array[File]]) = {
		(fp._1 zip fp._2).foreach( item  => {
			val (s, t) = item
			if(!t.getParentFile.exists) t.getParentFile.mkdirs()
			
			logger.info("copy " + s.getAbsolutePath)	
			copyFile(s, t)
		})
	}
	
	private def copyFile(source: File, target: File): File = {
		if(source.isDirectory){
			if(!target.exists) target.mkdirs()
			for(file <- source.listFiles) file match{
				case _ if file.isFile =>
					copyFile (file, new File(target, file.getName))
				case _ if file.isDirectory =>
					copyFile(file,new File(target, file.getName))
				case _ =>
			}
		}else {
			source copyTo  target
		}
		target
	}
	

	private def svnFileInfo2FileName(svnFileInfo: String) = {
	    val speFlag = branch + "/"
		val fileName = svnFileInfo.substring(svnFileInfo.indexOf(speFlag) + speFlag.length, svnFileInfo.length)
		fileName
	}
	
	object Maven{
	    def apply(svnFileInfo: String) : String = {
	        val fileName = svnFileInfo2FileName(svnFileInfo)
	        fileName match {
	        	//classes 资源文件
			    case _ if(fileName.indexOf("src/main/java") != -1) =>
				    var r = "/WEB-INF/" + fileName.replace("src/main/java","classes");
				    if(fileName.endsWith(".java")){ //Java文件
				        r = r.replace(".java" ,".class")
				    }
				    r
				// 其他
			    case _ =>  "/" + fileName.replace("src/main/webapp/",  "")
	        }
	    }
	}
	
	object Eclipse{
	    def apply(svnFileInfo: String) : String = {
	        val fileName = svnFileInfo2FileName(svnFileInfo)
	        fileName match {
	             //Java文件
			    case _ if(fileName.endsWith(".java")) =>
				    "/WEB-INF/" + fileName.replace("src","classes").replace(".java" ,".class")
				
			    case _ if(fileName.indexOf("src/") != -1) =>
				    "/WEB-INF/" + fileName.replace("src","classes")
			    case _ =>
				     "/" + fileName.replace("WebContent/",  "")
	        }
	    }
	}
	
	
	private def filePath ( svnFileInfo: String) = {
		var r :String = projectType match {
		    case "maven" => Maven(svnFileInfo)
		    case _ => Eclipse(svnFileInfo)
		}
		
		val fromFile = new File(config.from  + r)
		val fromFiles = if(fromFile.getName.endsWith(".class") ) 
						findRefClassfiles(fromFile)
						else Array(fromFile)

		(fromFiles, fromFiles.map( file =>{ 
			new File(file.getAbsolutePath.replace(config.from.getAbsolutePath , 
				config.output.getAbsolutePath + "/" +  who))
			})
		 )
	}
	
	private def findRefClassfiles(classFile : File) : Array[File] = {
		val fn = classFile.getName.substring(0, classFile.getName.indexOf(".class")) 
		classFile.getParentFile().listFiles.filter(isClassFile).filter{ file => {
				val on = file.getName.substring(0, file.getName.indexOf(".class")) 
				(on.indexOf(fn + "$") != -1 || on == fn)
			}
		}
	}
	
	private def isClassFile(file:File):Boolean = file.isFile && file.getName.endsWith(".class")
}

