package pagingtree

trait Region {
    var id: Long
    var code: String
    var name: String
    var description: String
    var parentId: Long
}