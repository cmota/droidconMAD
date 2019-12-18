package presentation.cb

import domain.model.Schedule

interface IScheduleData {

    fun onScheduleDataFetched(schedule: List<Schedule>)

    fun onScheduleDataFailed(e: Exception)
}