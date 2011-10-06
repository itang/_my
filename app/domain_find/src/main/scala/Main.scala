package com.itang

import java.io._
import java.net._

import scala.io._
import scala.collection.JavaConversions._

//域名数据.
case class Dnsable(name: String, desc: String) {
    def dnsName = name
}

//域名检查器.
trait Checker {
    def check(dnsable: { def dnsName: String }): Option[Boolean]
}

//结果记录器.
abstract class Recorder {
    def record(info: String): Unit
    def close(): Unit
}

//打印.
trait Printer extends Recorder {
    abstract override def record(info: String) {
        println(info)
        super.record(info)
    }
}

//默认写文件的记录器.
class MyRecorder(implicit writer: Writer) extends Recorder {
    def record(info: String) {
        writer.write(info + "\n")
        writer.flush()
    }
    def close() {
        try {
            writer.close()
        } catch {
            case e => e.printStackTrace()
        }
    }
}
//可以申请的域名单独写一个文件
trait AvaliableDsnRecorder extends Recorder {
    val writer = new BufferedWriter(new FileWriter(new File("avaliable.txt"), true))

    abstract override def record(info: String) {
        if (info.contains("^")) { //可获得标志
            writer.write(info + "\n")
            writer.flush()
        }

        super.record(info)
    }
    abstract override def close() {
        try {
            writer.close()
            super.close()
        } catch {
            case e => e.printStackTrace()
        }
    }
}

trait UrlGetChecker extends Checker {
    def checkURL: String
    def aTag: String = "已被注册"
    def encoding = "GBK"
    def timeout = 3000
    def check(dnsable: { def dnsName: String }): Option[Boolean] = {
        var input: BufferedSource = null
        try {
            val url = new URL(checkURL.format(dnsable.dnsName))
            val urlConnection = url.openConnection()
            urlConnection.setReadTimeout(timeout)
            input = Source.fromInputStream(urlConnection.getInputStream, encoding);
            val source = input.getLines.mkString("")
            if (source.indexOf(aTag) > -1) {
                None
            } else {
                Some(true)
            }
        } catch {
            case e =>
                e.printStackTrace()
                None
        }
        finally {
            if (input != null) {
                input.close()
            }
        }
    }
}

object PandaChecker extends UrlGetChecker {
    def checkURL = "http://panda.www.net.cn/cgi-bin/check.cgi?area_domain=%s.com"
    override def aTag = "not"
}

object Www1Checker extends UrlGetChecker {
    val checkURL = "http://www1.usr.name/Services/domainnew/checkdomain1.asp?domainname=%s&domainsuffix=.cn&domainlang=1&domainid=0"
}

object WhoisChecker extends Checker {
    import org.jruby.embed.ScriptingContainer
    val container = new ScriptingContainer
    def check(domain: String): Option[Boolean] = {
        try {
            val result = container.runScriptlet("`whois " + domain + ".com`").toString
            if (result.contains("No match")) {
                Some(true)
            } else {
                None
            }
        } catch {
            case e =>
                e.printStackTrace()
                None
        }
    }

    def check(dnsable: { def dnsName: String }): Option[Boolean] = {
        check(dnsable.dnsName)
    }
}

import scala.actors.Futures._
import java.util.concurrent.atomic.AtomicInteger
object Main {
    def main(args: Array[String]): Unit = {
        implicit val writer = new BufferedWriter(new FileWriter(new File("dns.txt"), true))

        val recorder = new MyRecorder with AvaliableDsnRecorder with Printer

        val checker = WhoisChecker

        val dsnNames: List[Dnsable] = dsnNamesForCheck()

        recorder.record {
            val totalInfo = dsnNames.map(it => it.desc + ":" + it.name).mkString(", ")
            totalInfo
        }

        val info = "%d/" + dsnNames.size + " %s.com [%s] : %s"
        var count = new AtomicInteger(0);

        import java.util.concurrent.Executors
        import java.util.concurrent.TimeUnit
        val maxThreadNumber = Runtime.getRuntime.availableProcessors() + 1
        println("INFO>maxThreadNumber:" + maxThreadNumber)
        val executor = Executors newFixedThreadPool maxThreadNumber
        dsnNamesForCheck.foreach { dnsable =>
            val task = new Runnable() {
                def run() {
                    println(Thread.currentThread)

                    val i = count.incrementAndGet() //放的位置值得考虑
                    val result = checker.check(dnsable)

                    result match {
                        case None =>
                            recorder.record {
                                info.format(i, dnsable.dnsName, dnsable.desc, "已被注册")
                            }
                        case _ =>
                            recorder.record {
                                info.format(i, dnsable.dnsName, dnsable.desc, "^" * 100)
                            }
                    }

                }
            }
            executor.execute(task)
        }

        while (count.get < dsnNames.size) {
            Thread.sleep(2000)
        }

        // executor.awaitTermination(1, TimeUnit.HOURS)
        executor.shutdown()

        Thread.sleep(1000 * 30) //等后续处理完

        recorder.close()
    }

    def needChecks[T](dsnNames: List[Dnsable])(func: Dnsable => T) {
        var checked: Map[String, Boolean] = Map()
        for (
            dnsable <- dsnNames;
            if !checked.contains(dnsable.dnsName)
        ) {
            func(dnsable)
            checked += (dnsable.dnsName -> true)
        }
    }

    def dsnNamesForCheck(): List[Dnsable] = {
        new Words().whats.map { it => Dnsable(it.pinyin, it.word) }.distinct.toList
    }
}
