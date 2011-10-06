package pagingtree.plugins

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

trait Plugin {
    def order: Int = 0

    def start(): Unit = {}

    def beforeRequest(request: HttpServletRequest): Unit = {}

    def afterRequest(request: HttpServletRequest, response: HttpServletResponse): Unit = {}

    def error(request: HttpServletRequest, response: HttpServletResponse, exception: Throwable): Unit = {}

    def destroy(): Unit = {}
}