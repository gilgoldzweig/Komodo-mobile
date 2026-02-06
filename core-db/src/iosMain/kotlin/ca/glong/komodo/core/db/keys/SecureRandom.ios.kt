// Platform: iOS
package ca.glong.komodo.core.db.keys

import kotlinx.cinterop.*
import platform.Security.SecRandomCopyBytes
import platform.Security.errSecSuccess
import platform.Security.kSecRandomDefault

@OptIn(ExperimentalForeignApi::class)
internal actual fun generateSecureRandomBytes(size: Int): ByteArray {
	val bytes = ByteArray(size)
	bytes.usePinned { pinned ->
		val status = SecRandomCopyBytes(kSecRandomDefault, size.toULong(), pinned.addressOf(0))
		check(status == errSecSuccess) { "SecRandomCopyBytes failed with status: $status" }
	}
	return bytes
}
