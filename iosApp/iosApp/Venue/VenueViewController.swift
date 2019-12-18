//
//  VenueViewController.swift
//  iosApp
//

import UIKit

class VenueViewController: UIViewController {

    @IBOutlet weak var pinImageView: UIImageView!
    @IBOutlet weak var mapButton: UIButton!
    @IBOutlet weak var websiteButton: UIButton!
    @IBOutlet weak var twitterButton: UIButton!
    @IBOutlet weak var textView: UITextView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        pinImageView.image = UIImage.init(named: "pin")?.withRenderingMode(UIImageRenderingMode.alwaysTemplate)
        pinImageView.tintColor = themeBaseColor
        
        mapButton.setImage(UIImage.init(named: "map")?.withRenderingMode(UIImageRenderingMode.alwaysTemplate), for: .normal)
        mapButton.tintColor = themeBaseColor
        
        websiteButton.setImage(UIImage.init(named: "www")?.withRenderingMode(UIImageRenderingMode.alwaysTemplate), for: .normal)
        websiteButton.tintColor = themeBaseColor
        
        twitterButton.setImage(UIImage.init(named: "twitter")?.withRenderingMode(UIImageRenderingMode.alwaysTemplate), for: .normal)
        twitterButton.tintColor = themeBaseColor
        
        let githubString: NSString = "You can find the project on Github or DM me directly on Twitter"
        let rangeGitHub = githubString.range(of: "Github")
        let rangeTwitter = githubString.range(of: "Twitter")
        let attributedString = NSMutableAttributedString.init(string: githubString as String)
        
        attributedString.addAttributes([NSAttributedStringKey.font: UIFont.init(name: "OpenSans-Light", size: 14) as Any],
                                        range: NSRange.init(location: 0, length: githubString.length))
        
        if let url = URL(string: endpointGithub) {
            attributedString.addAttributes([NSAttributedStringKey.link: url,
                                            NSAttributedStringKey.foregroundColor: themeBaseColor,
                                            NSAttributedStringKey.font: UIFont.init(name: "OpenSans-Semibold", size: 14) as Any,
                                            NSAttributedStringKey.underlineColor: themeBaseColor], range: rangeGitHub)
        }
        
        if let url = URL(string: endpointTwitterMota) {
            attributedString.addAttributes([NSAttributedStringKey.link: url,
                                            NSAttributedStringKey.foregroundColor: themeBaseColor,
                                            NSAttributedStringKey.font: UIFont.init(name: "OpenSans-Semibold", size: 14) as Any,
                                            NSAttributedStringKey.underlineColor: themeBaseColor], range: rangeTwitter)
        }
        
        textView.attributedText = attributedString
    }
    
    @IBAction func didTapOnMap(_ sender: Any) {
        
        if let url = URL(string: endpointMap) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        }
    }
    
    @IBAction func didTapOnInternet(_ sender: Any) {
        
        if let url = URL(string: endpointSite) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        }
    }
    
    @IBAction func didTapOnTwitter(_ sender: Any) {
        
        if let url = URL(string: endpointTwitter) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        }
    }
}
