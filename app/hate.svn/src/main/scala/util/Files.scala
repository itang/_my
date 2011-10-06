package util

object Files {
    import java.io.File
    /**
     * 遍历文件.
     */
    def visitFile(file: File)(process: File => Option[File]): Unit = file match {
        case dir if file.isDirectory =>
            process(dir) match {
                case Some(_) => dir.listFiles.foreach(visitFile(_)(process))
                case None =>
            }
        case _ => process(file)
    }

    /**
     * 删除文件.
     * 
     * 如果是目录则递归删除其所有文件和本身.
     */
    def forceDeleteFile(file: File): Option[File] = {
        if (file.isDirectory) {
            file.listFiles.foreach(forceDeleteFile)
        }

        if (file.delete()) None else Some(file)
    }
}
