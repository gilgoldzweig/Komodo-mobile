//
//  ContentView.swift
//  KomodoIOS
//
//  Created by Gil Goldzweig Goldbaum on 2026-02-03.
//

import SwiftUI
import SwiftData
import KomodoKMP

struct ComposeView : UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> some UIViewController {
        return MainViewControllerKt.MainViewController()
    }
    
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
    
}

struct ContentView: View {
    @Environment(\.modelContext) private var modelContext
    @Query private var items: [Item]

    var body: some View {
       ComposeView()
    }
}

#Preview {
    ContentView()
}
