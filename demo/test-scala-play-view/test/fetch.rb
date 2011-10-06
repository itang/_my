require 'rubygems'     # 1.8
require 'open-uri'

LEN = 3
(1..10).each do |page|
	s = (page - 1) * LEN + 1
	m = s + LEN
	system "wget http://203.175.156.102:9000/action=Query&TotalResults=true&start=#{s}&maxresults=#{m}&print=all&Text=IT -O result_#{page}.xml"
end
