//
//  SessionSpeakerTableViewCell.swift
//  iosApp
//

import UIKit

class SessionSpeakerTableViewCell: UITableViewCell {

    @IBOutlet weak var speakerImageView: UIImageView!
    @IBOutlet weak var speakerLabel: UILabel!
    @IBOutlet weak var speakerDescription: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
