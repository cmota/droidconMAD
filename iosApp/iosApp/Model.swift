//
//  Model.swift
//  iosApp
//

import app
import Foundation
import UIKit

struct ScheduleVideoModel {
    
    private let dateFormatterInput = DateFormatter()
    private let dateFormatterOut = DateFormatter()
    
    let date: String
    let sessions: [SessionViewModel]
    
    init(schedule: Schedule) {
        
        var date = ""
        var sessions = [SessionViewModel]()
        
        dateFormatterInput.dateFormat = defaultDateFormat
        dateFormatterOut.dateFormat = "MMM d"
        
        if let scheduleDate = dateFormatterInput.date(from: schedule.date) {
            date = dateFormatterOut.string(from: scheduleDate)
        }
        
        for timeSlot in schedule.timeSlots {
            for session in timeSlot.sessions {
                sessions.append(SessionViewModel.init(session: session))
            }
        }
        
        self.sessions = sessions
        self.date = date
    }
}
struct SessionViewModel {
    
    private let dateFormatterInput = DateFormatter()
    private let dateFormatterOut = DateFormatter()
    
    let session: Session
    let timeStart: String
    let timeEnd: String
    let title: String
    let description: String
    let speakers: String
    let room: String
    var favorite: Bool {
        get {
            return session.favourite
        }
        set {
        }
    }
    
    init(session: Session) {
        
        var timeStart = ""
        var timeEnd = ""
        var speakers = [String]()
        
        dateFormatterInput.dateFormat = defaultDateFormat
        dateFormatterOut.dateFormat = "HH:mm"
        
        for speaker in session.speakers {
            speakers.append(speaker.name)
        }
        
        if
            let startsAt = session.startsAt,
            let date = dateFormatterInput.date(from: startsAt) {
            
            timeStart = dateFormatterOut.string(from: date)
        }
        
        if
            let startsAt = session.endsAt,
            let date = dateFormatterInput.date(from: startsAt) {
            
            timeEnd = dateFormatterOut.string(from: date)
        }
        
        self.session = session
        self.timeStart = timeStart
        self.timeEnd = timeEnd
        self.title = session.title
        self.description = session.summary ?? ""
        self.speakers = speakers.joined(separator: " · ")
        self.room = session.room ?? ""
        self.favorite = session.favourite
    }
}

protocol SessioDataHandler {
    func updateWithSessions(sessions: [SessionViewModel])
}
