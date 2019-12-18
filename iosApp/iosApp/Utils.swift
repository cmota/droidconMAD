//
//  Utils.swift
//  iosApp
//

import Foundation
import UIKit

public let themeBaseColor = UIColor.init(hexString: "#1fa783")
public let themeGrayColor = UIColor.init(hexString: "#212121")
public let themeLightGrayColor = UIColor.init(hexString: "#818181")
public let defaultDateFormat = "yyyy-MM-dd'T'HH:mm:ss"

public let endpointMap = "https://www.google.com/maps/place/UCM+Facultad+de+Inform%C3%A1tica/@40.4527655,-3.7357,17z/data=!3m2!4b1!5s0xd4229d2abcb6a23:0x64e12201f58471a0!4m5!3m4!1s0xd4229d2a9f08b1f:0xcf68cce94ec84cb8!8m2!3d40.4527655!4d-3.7335113"
public let endpointSite = "https://www.madrid.droidcon.com/"
public let endpointTwitter = "https://twitter.com/droidconMad"
public let endpointTwitterMota = "https://twitter.com/cafonsomota"
public let endpointGithub = "https://github.com/cmota/droidconMAD"

extension UIColor {
    convenience init(hexString: String, alpha: CGFloat = 1.0) {
        var hexInt: UInt32 = 0
        let scanner = Scanner(string: hexString)
        scanner.charactersToBeSkipped = CharacterSet(charactersIn: "#")
        scanner.scanHexInt32(&hexInt)
        
        let red = CGFloat((hexInt & 0xff0000) >> 16) / 255.0
        let green = CGFloat((hexInt & 0xff00) >> 8) / 255.0
        let blue = CGFloat((hexInt & 0xff) >> 0) / 255.0
        let alpha = alpha
        
        self.init(red: red, green: green, blue: blue, alpha: alpha)
    }
}
