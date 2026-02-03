//
//  KomodoIOSApp.swift
//  KomodoIOS
//
//  Created by Gil Goldzweig Goldbaum on 2026-02-03.
//

import SwiftUI
import SwiftData
import KomodoKMP

@main
struct KomodoIOSApp: App {
    
    init() {
        KoinHelperKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView().ignoresSafeArea()
        }
    }
}
