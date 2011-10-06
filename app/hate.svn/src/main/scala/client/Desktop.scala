package client

import java.io.File
import hatedotsvn._
import com.trolltech.qt.gui._

object Desktop {
    def main(args: Array[String]) {
        QApplication.initialize(args)
        new Desktop().show
        QApplication.exec()
    }
}

class Desktop extends QWidget {
    val button: QPushButton = new QPushButton("清除")
    val lineEdit = new QLineEdit
    val textEdit = new QTextEdit
    def init = {
        button.resize(120, 40)
        button.setWindowTitle("清除包含的.svn目录")
        button.clicked.connect(this: Object, "btnClick()")

        lineEdit.setText("""E:\temp\exec""")

        val layout = new QVBoxLayout()
        layout.addWidget(lineEdit)
        layout.addWidget(button)
        layout.addWidget(textEdit)
        setLayout(layout);

        setWindowTitle(tr(".svn 清除器 v0.1"))
    }

    def btnClick() = {
        val cleaner = SvnCleaner(lineEdit.text)
        cleaner.addListener(new Listener() {
            def onDelete(file: File) = textEdit.append(file.getCanonicalPath)
        })
        val result = cleaner.cleanSvnDirs()
        textEdit.append("共删除%d个.svn 目录".format(result.success.size))
    }
    
    init
}
