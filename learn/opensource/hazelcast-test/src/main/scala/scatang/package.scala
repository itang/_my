package object scatang {

  implicit def ints(source: Int) = new {

    def times(proc: Int => Unit) {
      var index = 1
      while (index <= source) {
        proc(index)
        index += 1
      }
    }

    def times(block: => Unit) {
      times(index => block)
    }
  }

  implicit def anys[T <: Any](source: T) = new {

    def tap(proc: T => Any): T = {
      try {
        proc(source)
      } catch {
        case e => Console.err.print(e.getMessage());
      }
      source
    }

    def deliver[R](proc: T => R): R = proc(source)

    def `with`[R](proc: T => R) = deliver(proc)
  }
}
