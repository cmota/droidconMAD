//
//  SpeakerHeaherTableViewCell.swift
//  iosApp
//

import UIKit

class SpeakerHeaderTableViewCell: UITableViewCell {

    @IBOutlet weak var speakerImageView: UIImageView!
    @IBOutlet weak var speakerNameLabel: UILabel!
    @IBOutlet weak var speakerDescriptionLabel: UILabel!
    @IBOutlet weak var speakerSectionLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
