package data.entities

import kotlinx.serialization.Serializable

/**
 *   Speaker
 *
 *   {
 *      "id": "2bc1e95f-1243-4ad0-8a11-75541fee10e0",
 *      "firstName": "Carlos",
 *      "lastName": "Mota",
 *      "fullName": "Carlos Mota",
 *      "bio": "Android team lead at WIT Software, he can easily be spotted there working on the company RCS solution. An enthusiastic for new technology and always trying to reach those last 20% of all of his side projects that seem to be really far away, he loves to share his knowledge with others either by giving talks, teaching, writing or along with a cold beer in the nearest pub. GDG Coimbra organizer and Kotlin evangelist, he also has a huge passion for travel, photography, space and the occasional run.",
 *      "tagLine": "Lead Software Engineer at WIT Software",
 *      "profilePicture": "https://sessionize.com/image?f=fe1844d54d4d99ea359d20d6de2bd748,400,400,True,False,b0b9f1ee-6694-4781-b9ec-af27ef8707ae.jpg",
 *      "sessions": [
 *      {
 *      "id": 131513,
 *      "name": "The Hitchhikers Guide Through Kotlin Multiplatform"
 *      }
 *      ],
 *      "isTopSpeaker": false,
 *      "links": [],
 *      "questionAnswers": [],
 *      "categories": []
 *      }
 */

@Serializable
data class SpeakerEntity (
    val id: String,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val bio: String,
    val tagLine: String,
    val profilePicture: String?,
    val sessions: List<SessionShortEntity>,
    val isTopSpeaker: Boolean,
    val links: List<LinksEntity>,
    val questionAnswers: List<String>,
    val categories: List<String>)