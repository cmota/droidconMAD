//
//  FavoritesViewController.swift
//  iosApp
//

import app
import UIKit

class FavoritesViewController: UIViewController, UITableViewDataSource, UITableViewDelegate, SchedulesTableViewCellProtocol, SessioDataHandler {

    @IBOutlet weak var tableView: UITableView!
    
    private let dateFormatterInput = DateFormatter()
    private let dateFormatterOut = DateFormatter()
    private var sessions = [SessionViewModel]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationItem.title = "Favorites"
        tableView.separatorStyle = .none
        
        tableView.register(UINib(nibName: "SchedulesTableViewCell", bundle: nil), forCellReuseIdentifier: "SchedulesTableViewCell")
        
        dateFormatterInput.dateFormat = defaultDateFormat
        
        tableView.reloadData()
    }
    
    func updateWithSessions(sessions: [SessionViewModel]) {
        
        self.sessions = sessions.filter { $0.favorite }
        
        if tableView != nil {
            tableView.reloadData()
        }
    }
}

extension FavoritesViewController {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return sessions.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "SchedulesTableViewCell", for: indexPath) as? SchedulesTableViewCell  else {
            fatalError("The dequeued cell is not an instance of SpeakersTableViewCell.")
        }
        
        let session = sessions[indexPath.row]
        
        cell.timeLabel.text = session.timeStart
        cell.titleLabel.text = session.title
        cell.subtitleLabel.text = session.speakers
        cell.tagLabel.text = session.room
        cell.delegate = self
        
        cell.updateFavoriteButton(favorite: session.favorite)
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        let session = sessions[indexPath.row]
        
        let sessionViewController = SessionViewController(sessionViewModel: session)
        sessionViewController.hidesBottomBarWhenPushed = true
        
        self.navigationController?.pushViewController(sessionViewController, animated: true)
        
        tableView.deselectRow(at: indexPath, animated: true)
    }
    
    func tableView(_ tableView: UITableView, accessoryButtonTappedForRowWith indexPath: IndexPath) {
        
    }
}

// MARK: SchedulesTableViewCellProtocol

extension FavoritesViewController {
    func scheduleTableViewCellDidChangeFavorite(tableViewCell: UITableViewCell, favorite: Bool) {
        
        if let indexPath = tableView.indexPath(for: tableViewCell) {
            
            let session = sessions[indexPath.row].session
            
            session.favourite = !session.favourite
            AppData.init().updateSessionFavouriteState(updatedSession: session)
            
            sessions.remove(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .automatic)
        }
    }
}
