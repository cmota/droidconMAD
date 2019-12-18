//
//  SessionViewController.swift
//  iosApp
//

import app
import UIKit

class SessionViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {

    @IBOutlet weak var tableView: UITableView!
    
    let sessionViewModel: SessionViewModel
    
    init(sessionViewModel: SessionViewModel) {
        
        self.sessionViewModel = sessionViewModel
        
        super.init(nibName: "SessionViewController", bundle: nil)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        //navigationItem.title = session.title
        navigationItem.title = "Session"
        tableView.separatorStyle = .none
        
        tableView.register(UINib(nibName: "SessionHeaderTableViewCell", bundle: nil), forCellReuseIdentifier: "SessionHeaderTableViewCell")
        tableView.register(UINib(nibName: "SessionSpeakerTableViewCell", bundle: nil), forCellReuseIdentifier: "SessionSpeakerTableViewCell")
    }
}

extension SessionViewController {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return sessionViewModel.session.speakers.count + 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        if indexPath.row == 0 {
            guard let cell = tableView.dequeueReusableCell(withIdentifier: "SessionHeaderTableViewCell", for: indexPath) as? SessionHeaderTableViewCell  else {
                fatalError("The dequeued cell is not an instance of SessionHeaderTableViewCell.")
            }
            
            cell.sessionTitleLabel.text = sessionViewModel.title
            cell.sessionDescriptionLabel.text = sessionViewModel.description
            cell.sessionRoomLabel.text = sessionViewModel.room
            cell.sessionTimeLabel.text = "\(sessionViewModel.timeStart) - \(sessionViewModel.timeEnd)"
            cell.speakersSeparatorLabel.text = sessionViewModel.session.speakers.count > 0 ? "SPEAKERS" : ""
            cell.selectionStyle = .none
            cell.accessoryType = .none
            
            return cell
        }
        else {
            guard let cell = tableView.dequeueReusableCell(withIdentifier: "SessionSpeakerTableViewCell", for: indexPath) as? SessionSpeakerTableViewCell  else {
                fatalError("The dequeued cell is not an instance of SessionSpeakerTableViewCell.")
            }
            
            let speaker = AppData.init().getSpeakerById(id: sessionViewModel.session.speakers[indexPath.row - 1].id)
            var sessions = [String]()
            
            for session in speaker.sessions {
                sessions.append(session.name)
            }
            
            cell.speakerLabel.text = speaker.fullName
            cell.speakerDescription.text = sessions.joined(separator: " · ")
            cell.accessoryType = .disclosureIndicator
            cell.accessoryView?.tintColor = themeLightGrayColor
            
            if let url = URL(string: speaker.profilePicture) {
                cell.speakerImageView.layer.cornerRadius = cell.speakerImageView.frame.height/2
                cell.speakerImageView.sd_setImage(with: url)
            }
            
            return cell;
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        guard indexPath.row != 0 else { return }
        
        let speaker = AppData.init().getSpeakerById(id: sessionViewModel.session.speakers[indexPath.row - 1].id)
        
        let speakerViewController = SpeakerViewController()
        speakerViewController.speaker = speaker
        speakerViewController.hidesBottomBarWhenPushed = true
        
        self.navigationController?.pushViewController(speakerViewController, animated: true)
        
        tableView.deselectRow(at: indexPath, animated: true)
    }
    
    func tableView(_ tableView: UITableView, accessoryButtonTappedForRowWith indexPath: IndexPath) {
        
    }
}
