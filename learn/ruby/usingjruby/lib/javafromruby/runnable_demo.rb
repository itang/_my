require 'java'

java_import java.lang.Runnable
java_import java.lang.Thread

class Foo
  include Runnable # 实现Runnable接口, 可以省掉, jruby会自动处理
  def run
    puts "foo"
    puts Thread.currentThread
    #java.lang.Thread.sleep(1000 * 5)
  end
end

puts Thread.current_thread

callable = java.util.concurrent.Executors.callable(Foo.new)
callable.call

Thread.new(Foo.new).start

Executors = java.util.concurrent.Executors
# 传递block, jruby能处理它
callable = Executors.callable do
  puts "foo"
  puts Thread.currentThread
end
callable.call

#传递Proc, jruby能处理它
myproc = Proc.new { puts "proc"  }
callable = java.util.concurrent.Executors.callable(myproc)
callable.call