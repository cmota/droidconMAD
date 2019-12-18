//
//  SchedulesTableViewCell.swift
//  iosApp
//

import UIKit

protocol SchedulesTableViewCellProtocol {
    func scheduleTableViewCellDidChangeFavorite(tableViewCell: UITableViewCell, favorite: Bool)
}

class SchedulesTableViewCell: UITableViewCell {

    @IBOutlet weak var timeLabel: UILabel!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var subtitleLabel: UILabel!
    @IBOutlet weak var tagLabel: UILabel!
    @IBOutlet weak var favoriteButton: UIButton!
    
    var delegate: SchedulesTableViewCellProtocol?
    var favorite = false
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    @IBAction func didTapOnFavorite(_ sender: Any) {
        favorite = !favorite
        
        if let delegate = delegate {
            delegate.scheduleTableViewCellDidChangeFavorite(tableViewCell: self, favorite: favorite)
        }
        
        updateFavoriteButton(favorite: favorite)
    }
    
    public func updateFavoriteButton(favorite: Bool) {
        self.favorite = favorite
        
        favoriteButton.setImage(UIImage.init(named: favorite ? "favorite" : "favoriteline")?.withRenderingMode(UIImageRenderingMode.alwaysTemplate), for: .normal)
        favoriteButton.imageView?.tintColor = favorite ? themeBaseColor : .black
    }
}
