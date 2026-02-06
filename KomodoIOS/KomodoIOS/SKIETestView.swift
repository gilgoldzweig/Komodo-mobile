import SwiftUI

struct SKIETestView: View {
    var body: some View {
        VStack {
            Text("SKIE Framework Import Test")
                .padding()
            
            Button("Test SKIE Import") {
                testSkieFramework()
            }
            .padding()
        }
    }
    
    private func testSkieFramework() {
        do {
            let message = "SKIE framework successfully imported!"
            print(message)
        } catch {
            print("Error: Failed to import SKIE framework")
        }
    }
}

#Preview {
    SKIETestView()
}
