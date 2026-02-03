//
//  Item.swift
//  KomodoIOS
//
//  Created by Gil Goldzweig Goldbaum on 2026-02-03.
//

import Foundation
import SwiftData

@Model
final class Item {
    var timestamp: Date
    
    init(timestamp: Date) {
        self.timestamp = timestamp
    }
}
