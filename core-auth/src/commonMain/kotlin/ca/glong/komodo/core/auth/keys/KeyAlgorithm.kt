package ca.glong.komodo.core.auth.keys

/**
 * Supported cryptographic algorithms for key generation and operations.
 */
enum class KeyAlgorithm {
    /** Edwards-curve Digital Signature Algorithm (EdDSA) with Ed25519 curve */
    ED25519,
    
    /** NIST P-256 elliptic curve (also known as secp256r1) */
    P256,
    
    /** RSA with 4096-bit key size */
    RSA_4096
}
