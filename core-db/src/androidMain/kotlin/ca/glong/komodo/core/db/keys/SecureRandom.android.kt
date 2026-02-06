// Platform: Android
package ca.glong.komodo.core.db.keys

internal actual fun generateSecureRandomBytes(size: Int): ByteArray {
	val bytes = ByteArray(size)
	java.security.SecureRandom().nextBytes(bytes)
	return bytes
}
