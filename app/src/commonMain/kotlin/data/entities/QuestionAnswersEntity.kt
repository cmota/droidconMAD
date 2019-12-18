package data.entities

import kotlinx.serialization.Serializable

/**
 *
 *  Sessions
 *
 *  {
 *      "groupId": 10663,
 *      "groupName": "Technical",
 *      "sessions": {
 *         ...
 *      }
 *  }
 */

@Serializable
data class QuestionAnswersEntity(
    val id: String,
    val question: String,
    val questionType: String,
    val answer: String,
    val sort: String,
    val answerExtra: String)
