package test.test_xtend_maven

class PersonExtensions {
	def getFullName(Person p) {
        p.forename + " "+ p.name 
    }
}