package mcssoft.com.racereminderac.utility.pojo

class Race() {

    lateinit var raceNo: String   // e.g. "1"
    lateinit var raceTime: String // e.g. "2019-08-13T12:35:00"
    lateinit var raceName: String // e.g. "LEGACY WEEK 1-8 SEPTEMBER MAIDEN PLATE"
    lateinit var distance: String // e.g. "1600"

    override fun toString(): String {
        return """Race: $raceNo Time: ${raceTime.split("T")[1]} Name: $raceName"""
    }
}