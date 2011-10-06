Demo = Java::OverloadDemo
puts Demo.what_type_is(42)
puts Demo.what_type_is('Fun!')
puts Demo.what_type_is(Hash.new)


od = Demo.new
# jruby 你默认转换为long
puts od.needed_for(10_10000) # long

# 使用java_send，强制jruby使用int
puts (od.java_send :neededFor, [Java::int], 10_10000)

# 打开类 定义java方法别名
class Java::OverloadDemo
  java_alias :needed_for_int, :neededFor, [Java::int]
end
puts od.needed_for_int(1_000_000) # -> int


# 使用java_method 引用重载的方法
bits = Demo.new
bits_needed_for = bits.java_method :neededFor, [Java::int]
puts (bits_needed_for.call 1_000_000) # -> int