package domain.model

data class Room (val id: String,
                 val name: String,
                 val session: Session,
                 val speakers: List<Info>)

