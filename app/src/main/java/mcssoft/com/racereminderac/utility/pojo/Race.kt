package mcssoft.com.racereminderac.utility.pojo

class Race {
    // Meeting details.
    lateinit var mtgId: String    // e.g. "2032403456"
    lateinit var meetingCode: String   // e.g. "NR"

    // Race details.
    lateinit var raceNo: String   // e.g. "1"
    lateinit var raceTime: String // e.g. "2019-08-13T12:35:00"
    lateinit var raceName: String // e.g. "LEGACY WEEK 1-8 SEPTEMBER MAIDEN PLATE"
    lateinit var distance: String // e.g. "1600"

    override fun toString(): String {
        return """Race: $raceNo Time: ${raceTime.split("T")[1]} Name: $raceName"""
    }

    fun getRaceDetails() : List<String> {
        return listOf(mtgId, meetingCode, raceNo, raceTime, raceName, distance)
    }
}