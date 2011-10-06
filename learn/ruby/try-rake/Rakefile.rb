desc 'default task'
task :default => [:jar, :javadoc] do

end


require 'rake/clean'
CLEAN.include '*.class', 'Manifest'
CLOBBER.include '*.jar', 'doc'

jar = namespace :jar do
	# tasks for .class and Manifest go here
	desc 'Compile the Java code'
	task :compile => ['Pitcher.class']
	
	desc 'Create a .jar file from the compiled code'
	
	task :create => [:compile, 'Manifest'] do
		sh 'jar -cfm baseball.jar Manifest Pitcher.class Catcher.cl
	end
end

javadoc = namespace :doc do
	directory 'doc'
	desc 'Build the documentation'
	task :create => 'doc' do
		sh 'javadoc -d doc *.java'
	end
end
#task :default => [jar[:create], javadoc[:create]]

desc 'parallel'
multitask :parallel => [:jar, :javadoc]

desc 'javac'
file 'Pitcher.class' => 'Pitcher.java' do
	sh 'javac Pitcher.java'
end

desc 'run'
task :run => :"Pitcher.class" do
	require 'java'
	
	puts Java::Pitcher.new.pitch
end

rule '.class' => '.java' do |t|
	sh "javac #{t.source}"
end

file 'Manifest' do
	File.open 'Manifest', 'w' do |f|
	f.puts 'Main-Class: Catcher'
	end
end

desc 'Build the application into a .jar file'
task :jar => ['Pitcher.class', 'Manifest', :run] do
	sh 'jar -cfm baseball.jar Manifest Pitcher.class'
end


directory 'doc'
	desc 'Build the documentation'
	task :javadoc => 'doc' do
	sh 'javadoc -d doc *.java'
end

desc 'Make a tasty lunch'
task :sandwich => [:bread, :cheese] do
	puts 'Grilling the sandwich'
end
desc 'I guess sliced bread really is the greatest thing'
	task :bread do
puts 'Slicing the bread'
end
desc 'Only the finest Emmentaler for our sandwich!'
task :cheese do
	puts 'Grating the cheese'
end

