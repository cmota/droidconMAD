package data.entities

import kotlinx.serialization.Serializable

/**
 *
 *  Sessions
 *
 *  {
 *      "questionAnswers": [],
 *      "id": "131513",
 *      "title": "The Hitchhikers Guide Through Kotlin Multiplatform",
 *      "description": "Since the early days of mobile that we keep seeing new frameworks being designed to overcome one of the biggest challenges:\r\n* How can I develop for both Android and iOS?\r\n\r\nAlthough it’s initial promises, when we talk about performance, maintainability or even customization we keep discarding these solutions and we always choose native.\r\n\r\n\r\nFast forward to the present, and now we have two new languages: Android is Kotlin first and iOS, Swift. And if you put them side by side you can see a lot of similarities between both what will ease switching between one to the other if you have to develop for both platforms.\r\n\r\nBut what I told that you could just develop in Kotlin and run it seamlessly on all devices?\r\n\r\nHere comes Kotlin Multiplatform!",
 *      "startsAt": null,
 *      "endsAt": null,
 *      "isServiceSession": false,
 *      "isPlenumSession": false,
 *      "speakers": [
 *      {
 *      "id": "2bc1e95f-1243-4ad0-8a11-75541fee10e0",
 *      "name": "Carlos Mota"
 *      }
 *      ],
 *      "categories": [],
 *      "roomId": null,
 *      "room": null
 *  }
 */

@Serializable
data class SessionEntity(
    val questionAnswers: List<QuestionAnswersEntity> = mutableListOf(),
    val id: String,
    val title: String,
    val description: String?,
    val startsAt: String?,
    val endsAt: String?,
    val isServiceSession: Boolean,
    val isPlenumSession: Boolean,
    val speakers: List<SpeakerShortEntity>?,
    val categories: List<CategoryEntity>?,
    val roomId: String?,
    val room: String?)
