//
//  SpeakerViewController.swift
//  iosApp
//
//  Created by Carlos Mota on 19/06/2019.
//

import SDWebImage
import UIKit
import app

class SpeakerViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    // MARK: - Navigation
    @IBOutlet weak var tableView: UITableView!
    
    private let dateFormatterInput = DateFormatter()
    private let dateFormatterOut = DateFormatter()
    
    var speaker: Speaker!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationItem.title = speaker.fullName
        tableView.separatorStyle = .none
        
        tableView.register(UINib(nibName: "SpeakerHeaderTableViewCell", bundle: nil), forCellReuseIdentifier: "SpeakerHeaderTableViewCell")
        tableView.register(UINib(nibName: "SpeakerEventTableViewCell", bundle: nil), forCellReuseIdentifier: "SpeakerEventTableViewCell")
    }
}

// MARK: UITableViewDataSource, UITableViewDelegate
extension SpeakerViewController {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return speaker.sessions.count + 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        if indexPath.row == 0 {
            guard let cell = tableView.dequeueReusableCell(withIdentifier: "SpeakerHeaderTableViewCell", for: indexPath) as? SpeakerHeaderTableViewCell  else {
                fatalError("The dequeued cell is not an instance of SpeakersTableViewCell.")
            }
            
            cell.speakerNameLabel.text = speaker.fullName
            cell.speakerDescriptionLabel.text = speaker.bio
            cell.speakerImageView.layer.cornerRadius = cell.speakerImageView.frame.height/2
            cell.speakerSectionLabel.text = speaker.sessions.count > 0 ? "EVENTS" : ""
            cell.selectionStyle = .none
            cell.accessoryType = .none
            
            if let url = URL(string: speaker.profilePicture) {
                cell.speakerImageView.sd_setImage(with: url)
            }
            
            return cell
        }
        else {
            guard let cell = tableView.dequeueReusableCell(withIdentifier: "SpeakerEventTableViewCell", for: indexPath) as? SpeakerEventTableViewCell  else {
                fatalError("The dequeued cell is not an instance of SpeakersTableViewCell.")
            }
            
            dateFormatterInput.dateFormat = defaultDateFormat
            dateFormatterOut.dateFormat = "HH:mm"
            
            let session = AppData.init().getSessionById(id: speaker.sessions[indexPath.row - 1].id)
            
            cell.titleLabel.text = session.title
            cell.accessoryType = .disclosureIndicator
            cell.accessoryView?.tintColor = themeLightGrayColor
            
            if
                let startsAt = session.startsAt,
                let date = dateFormatterInput.date(from: startsAt) {
                
                cell.timeLabel.text = dateFormatterOut.string(from: date)
            }
            
            if let room = session.room {
                cell.typeLabel.text = " / " + room
            }
            
            return cell
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        guard indexPath.row != 0 else { return }
        
        let session = AppData.init().getSessionById(id: speaker.sessions[indexPath.row - 1].id)
        let sessionViewModel = SessionViewModel.init(session: session)
        
        let sessionViewController = SessionViewController(sessionViewModel: sessionViewModel)
        sessionViewController.hidesBottomBarWhenPushed = true
        
        self.navigationController?.pushViewController(sessionViewController, animated: true)
        
        tableView.deselectRow(at: indexPath, animated: true)
    }
}
