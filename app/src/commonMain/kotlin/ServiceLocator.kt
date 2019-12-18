import data.ScheduleDb
import data.SessionizeAPI
import domain.GetSchedule
import domain.GetSessions
import domain.GetSpeakers
import domain.dao.ScheduleDao
import domain.dao.SessionDao
import domain.dao.SpeakerDao
import io.ktor.client.engine.HttpClientEngine
import presentation.ScheduleListPresenter
import presentation.SessionsListPresenter
import presentation.SpeakersListPresenter
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object ServiceLocator {

    private val sessionizeAPI by lazy { SessionizeAPI(PlatformServiceLocator.httpClientEngine) }

    private val getSpeakers: GetSpeakers
        get() = GetSpeakers(sessionizeAPI, speakerDao)

    private val getSessions: GetSessions
        get() = GetSessions(sessionizeAPI, sessionDao)

    private val getSchedule: GetSchedule
        get() = GetSchedule(sessionizeAPI, scheduleDao, sessionDao)

    val speakerDao by lazy { SpeakerDao(PlatformServiceLocator.databaseEngine) }

    val sessionDao by lazy { SessionDao(PlatformServiceLocator.databaseEngine) }

    val scheduleDao by lazy { ScheduleDao(PlatformServiceLocator.databaseEngine) }

    val getSpeakersListPresenter: SpeakersListPresenter
        get() = SpeakersListPresenter(getSpeakers)

    val getSessionsListPresenter: SessionsListPresenter
        get() = SessionsListPresenter(getSessions)

    val getScheduleListPresenter: ScheduleListPresenter
        get() = ScheduleListPresenter(getSchedule)
}

expect object PlatformServiceLocator {

    val httpClientEngine: HttpClientEngine

    val databaseEngine: ScheduleDb
}