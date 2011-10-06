import java.io.File
import tools.nsc.io.Path
 
Path(new File("d:/work/")).walk.
    filter(e => e.toString.contains(".jar")).foreach(println)
    
    
import tools.nsc.io._
 
val dirProc = Process("camd.exe /c dir")
val stdOut  = dirProc.stdout.collect { case line:String => line }
val stdErr  = dirProc.stderr.collect { case line:String => line }
 
println("STDOUT:")
println(stdOut.mkString("\n"))
println("END OF STDOUT:")
 
println("STDERR:")
println(stdErr.mkString("\n"))
println("END OF STDERR")
 
println(dirProc.exitValue)
dirProc.destroy

println(dirProc.exitValue match {
    case Some(value) => value match {
        case 0 => "success"
        case _ => "failure"
    }
    case _ => "thread exception?"
})

