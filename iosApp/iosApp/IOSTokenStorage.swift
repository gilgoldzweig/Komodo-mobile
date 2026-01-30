//
// Created by Gil Goldzweig Goldbaum on 2026-01-29.
//
import Foundation
import ComposeApp

class IOSTokenStorage: TokenStorage {

    private let storage = UserDefaults.standard
    private let suiteName = "com.myapp.tokens"

    func saveToken(id: String, token: String, completionHandler: @escaping (Error?) -> Void) {
        storage.set(token, forKey: "\(suiteName).\(id)")
        completionHandler(nil)
    }

    func getToken(id: String, completionHandler: @escaping (String?, Error?) -> Void) {
        let val = storage.string(forKey: "\(suiteName).\(id)")
        completionHandler(val, nil)
    }

    func deleteToken(id: String, completionHandler: @escaping (Error?) -> Void) {
        storage.removeObject(forKey: "\(suiteName).\(id)")
        completionHandler(nil)
    }

    func deleteAllTokens(completionHandler: @escaping (Error?) -> Void) {
        let keys = storage.dictionaryRepresentation().keys
        for key in keys where key.hasPrefix(suiteName) {
            storage.removeObject(forKey: key)
        }
        completionHandler(nil)
    }
}


