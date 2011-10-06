package pagingtree.plugins

import java.sql._

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.H2Adapter

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.slf4j._

import org.squeryl.internals._

object SquerylPlugin extends Plugin {
    private lazy val logger = LoggerFactory.getLogger(getClass)
    private val connectionProvider = () => java.sql.DriverManager.getConnection("jdbc:h2:~/pagingtree", "sa", "")

    override def start() {
        logger.info("初始化数据连接信息")
        SessionFactory.concreteFactory match {
            case None =>
                import org.squeryl.SessionFactory
                Class.forName("org.h2.Driver");
                SessionFactory.concreteFactory = Some(() =>
                    new LazySession(connectionProvider, new H2Adapter))
            case _ => println("已经加载...")
        }
    }

    override def beforeRequest(request: HttpServletRequest) {
        LazySession.startSession()
    }

    override def afterRequest(request: HttpServletRequest, response: HttpServletResponse) {
        LazySession.commitAndcleanSession()
    }

    override def error(request: HttpServletRequest, response: HttpServletResponse, exception: Throwable): Unit = {
        LazySession.rollbackAndCleanSession()
    }

    override def destroy() {
        SessionFactory.concreteFactory = None
    }

}

protected object LazySession {
    def startSession() {
        SessionFactory.newSession.bindToCurrentThread
    }

    def commitAndcleanSession() {
        cleanSession(true)
    }

    def rollbackAndCleanSession() {
        cleanSession(false)
    }

    def cleanSession(commit: Boolean) {
        if (!Session.hasCurrentSession) { return ; }
        val session = Session.currentSession.asInstanceOf[LazySession]
        session.endTransaction(commit)
    }
}

class LazySession(connProvider: () => java.sql.Connection, adapter: DatabaseAdapter) extends Session {
    private lazy val logger = LoggerFactory.getLogger(getClass)
    private var connected = false
    private lazy val _conn = connProvider()

    def connection: Connection = {
        val conn = _conn
        if (!connected) {
            logger.info("启动数据库连接")
        }
        connected = true
        startTransaction()
        conn
    }
    def databaseAdapter: DatabaseAdapter = adapter

    def startTransaction() {
        if (_conn.getAutoCommit) {
            _conn.setAutoCommit(false)
        }
    }

    def endTransaction(commit: Boolean) {
        if (!connected) {
            logger.info("当前请求未启动事务")
            return ;
        }
        val conn = _conn
        try {
            if (commit) {
                conn.commit
                logger.info("事务提交")
            } else {
                conn.rollback()
                logger.info("事务会滚")
            }
        } catch {
            case e: Exception =>
                e.printStackTrace()
        }
        finally {
            try { conn.close }
            catch {
                case _ =>
            }
            unbindFromCurrentThread
            cleanup
        }
    }
}

