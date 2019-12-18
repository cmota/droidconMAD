//
//  SpeakersViewController.swift
//  iosApp
//

import app
import SDWebImage
import UIKit

class SpeakersViewController: UIViewController, ISpeakersData, UITableViewDataSource, UITableViewDelegate {
    
    @IBOutlet weak var tableView: UITableView!
    
    private lazy var presenterSpeakers = ServiceLocator.init().getSpeakersListPresenter
    private var speakers = [Speaker]()
    
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
        
        presenterSpeakers.attachView(currView: self)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationItem.title = "Speakers"
        tableView.separatorStyle = .none
        
        tableView.register(UINib(nibName: "SpeakersTableViewCell", bundle: nil), forCellReuseIdentifier: "SpeakersTableViewCell")
        
        tableView.reloadData()
    }
}

// MARK: UITableViewDataSource, UITableViewDelegate
extension SpeakersViewController {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return speakers.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "SpeakersTableViewCell", for: indexPath) as? SpeakersTableViewCell  else {
            fatalError("The dequeued cell is not an instance of SpeakersTableViewCell.")
        }
        
        let speaker = speakers[indexPath.row]
        var sessions = [String]()
        
        for session in speaker.sessions {
            sessions.append(session.name)
        }
        
        cell.speakerNameLabel.text = speaker.fullName
        cell.speakerDescriptionLabel.text = sessions.joined(separator: " · ")
        cell.speakerImageView.layer.cornerRadius = cell.speakerImageView.frame.height/2
        cell.selectionStyle = .none
        
        if let url = URL(string: speaker.profilePicture) {
            cell.speakerImageView.sd_setImage(with: url)
        }

        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        let speakerViewController = SpeakerViewController()
        speakerViewController.speaker = speakers[indexPath.row]
        speakerViewController.hidesBottomBarWhenPushed = true
        
        self.navigationController?.pushViewController(speakerViewController, animated: true)
        tableView.deselectRow(at: indexPath, animated: true)
    }
}

// MARK: IScheduleData, ISpeakersData
extension SpeakersViewController {
    func onSpeakersDataFetched(speakers: [Speaker]) {
        self.speakers = speakers
        
        if self.tableView != nil {
            self.tableView.reloadData()
        }
        
        print("new speakers list=\(speakers)")
    }
    
    func onSpeakersDataFailed(e: KotlinException) {
        print("Unable to fetch speakers list=\(e)")
    }
}
