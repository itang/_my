require 'java'

system = java.lang.System

os = system.getProperty 'os.name'
home = system.get_property 'java.home'
mem = java.lang.Runtime.get_runtime.free_memory

puts "Running on #{os}"
puts "Java home is #{home}"
puts "#{mem/ (1024 * 1024) } Mb available in JVM"