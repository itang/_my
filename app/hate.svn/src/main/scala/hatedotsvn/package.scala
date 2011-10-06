/**
 * package object
 */
package object hatedotsvn {
    implicit def stringToSvnCleaner(file: String) = SvnCleaner(file)
}