require 'java'

list = java.util.ArrayList.new

list << "List of"
list << 3
list << :assorted_items

list.each do |item|
  puts "#{item.class}: #{item}"
end

#
#As you can see, we can add a variety of objects, including native Ruby
#types like Symbols, to the list. JRuby even provides appropriate Ruby
#iteration idioms for Java collections, which is why we can call each( ) on
#the list in this example.