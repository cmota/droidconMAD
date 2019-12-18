//
//  SchedulesViewController.swift
//  iosApp
//

import app
import UIKit

class SchedulesViewController: UIViewController, IScheduleData, UITableViewDataSource, UITableViewDelegate, SchedulesTableViewCellProtocol {

    public var sessionDataHandler: SessioDataHandler?
    
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var segmentedControl: UISegmentedControl!
    
    private lazy var presenterSchedule = ServiceLocator.init().getScheduleListPresenter
    private var schedules = [ScheduleVideoModel]()
    private var currentIndex = 0
    
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
        
        presenterSchedule.attachView(currView: self)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationItem.title = "Schedule"
        tableView.separatorStyle = .none
        
        tableView.register(UINib(nibName: "SchedulesTableViewCell", bundle: nil), forCellReuseIdentifier: "SchedulesTableViewCell")
        
        segmentedControl.removeAllSegments()
        segmentedControl.tintColor = themeBaseColor
        
        tableView.reloadData()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        tableView.reloadData()
    }
    
    func setupSegmentedController() {
        var index = 0
        
        for scheduleViewModel in schedules {
            var date = scheduleViewModel.date
            
            if scheduleViewModel.date.count == 0 {
                date = "Day \(index + 1)"
            }
            
            segmentedControl.insertSegment(withTitle: date, at: index, animated: false)
            index += 1
        }
        
        segmentedControl.selectedSegmentIndex = 0
        tableView.reloadData()
    }
    
    @IBAction func segmentedControlValueChanged(_ sender: Any) {
        currentIndex = segmentedControl.selectedSegmentIndex
        tableView.reloadData()
    }
}

extension SchedulesViewController {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return schedules.count > 0 ? schedules[currentIndex].sessions.count : 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "SchedulesTableViewCell", for: indexPath) as? SchedulesTableViewCell  else {
            fatalError("The dequeued cell is not an instance of SpeakersTableViewCell.")
        }
        
        let session = schedules[currentIndex].sessions[indexPath.row]
        var time = session.timeStart
        
        if indexPath.row > 0 {
            let previousSession = schedules[currentIndex].sessions[indexPath.row - 1]
            
            if previousSession.timeStart == time {
                time = ""
            }
        }
        
        cell.timeLabel.text = time
        cell.titleLabel.text = session.title
        cell.subtitleLabel.text = session.speakers
        cell.tagLabel.text = session.room
        cell.delegate = self
        
        cell.updateFavoriteButton(favorite: session.favorite)
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        let session = schedules[currentIndex].sessions[indexPath.row]
        
        let sessionViewController = SessionViewController(sessionViewModel: session)
        sessionViewController.hidesBottomBarWhenPushed = true
        
        self.navigationController?.pushViewController(sessionViewController, animated: true)
        
        tableView.deselectRow(at: indexPath, animated: true)
    }
    
    func tableView(_ tableView: UITableView, accessoryButtonTappedForRowWith indexPath: IndexPath) {
        
    }
}

// MARK: SchedulesTableViewCellProtocol

extension SchedulesViewController {
    func scheduleTableViewCellDidChangeFavorite(tableViewCell: UITableViewCell, favorite: Bool) {
        
        if let indexPath = tableView.indexPath(for: tableViewCell) {
            
            let session = schedules[currentIndex].sessions[indexPath.row].session
            
            session.favourite = !session.favourite
            AppData.init().updateSessionFavouriteState(updatedSession: session)
            
            notifySessionsUpdated()
        }
    }
}

extension SchedulesViewController {
    func onScheduleDataFetched(schedule: [Schedule]) {
        
        handleData(schedules: schedule)
        
        print("new schedules list=\(schedule)")
    }
    
    func onScheduleDataFailed(e: KotlinException) {
        print("Unable to fetch schedule list=\(e)")
    }
    
    func handleData(schedules: [Schedule]) {
        
        var schedulesArray = [ScheduleVideoModel]()
        
        for schedule in schedules {
            schedulesArray.append(ScheduleVideoModel.init(schedule: schedule))
        }
        
        self.schedules = schedulesArray
        
        notifySessionsUpdated()
        setupSegmentedController()
        
        if self.tableView != nil {
            tableView.reloadData()
        }
    }
    
    func notifySessionsUpdated() {
        var sessions = [SessionViewModel]()
        
        for schedule in schedules {
            sessions.append(contentsOf: schedule.sessions)
        }
        
        if let sessionDataHandler = sessionDataHandler {
            sessionDataHandler.updateWithSessions(sessions: sessions)
        }
    }
}
