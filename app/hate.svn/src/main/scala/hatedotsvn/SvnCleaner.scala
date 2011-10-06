package hatedotsvn

import java.io.File

trait SvnCleaner {
    def cleanSvnDirs(): Result
    def addListener(listeners: Listener): Unit
}

case class Result(success: List[File], failture: List[File])

trait Listener {
    def onDelete(file: File): Unit
}

object SvnCleaner {
    def apply(from: File): SvnCleaner = new SvnCleanerImpl(from)
    def apply(from: String): SvnCleaner = apply(new File(from))
    def isSvnDirectory(file: File) = file.isDirectory && file.getName == ".svn"
}

private[hatedotsvn] class SvnCleanerImpl(from: File) extends SvnCleaner {
    import util.Files._
    var listeners: List[Listener] = Nil

    def cleanSvnDirs() = {
        var success: List[File] = Nil
        var failture: List[File] = Nil
        visitFile(from) {
            case file if SvnCleaner.isSvnDirectory(file) =>
                val result = forceDeleteFile(file)
                result match {
                    case None =>
                        notifySvnDeleted(file)
                        success = file :: success
                    case _ => failture = file :: failture
                }
                result
            case file => Some(file)
        }

        Result(success, failture)
    }

    def addListener(listener: Listener) = listeners = listener :: listeners

    private def notifySvnDeleted(file: File) = listeners.foreach(_.onDelete(file))
}
