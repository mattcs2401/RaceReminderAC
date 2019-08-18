package mcssoft.com.racereminderac.utility.pojo

class Runner {

    // Race details.
    lateinit var meetingCode: String   // e.g. "NR"
    lateinit var raceNo: String        // e.g. "1"

    // Runner details.
    lateinit var runnerNo: String      // e.g. "1"
    lateinit var runnerName: String    // e.g. "BADGE OF HONOUR"
    lateinit var scratched: String     // e.g. "N"
    lateinit var riderChanged: String  // e.g. "N"
    lateinit var barrier: String       // e.g. "6"
    lateinit var handicap: String      // e.g. "0"
    lateinit var weight: String        // e.g. "59.0"
    lateinit var rtng: String          // e.g. "82"
    // Special case trial and error testing. May not exist.
    var rider: String? = ""            // e.g. "K O'HARA"
    var lastResult: String? = ""       // e.g. "477"

    override fun toString(): String {
        return """$runnerNo $runnerName $rider"""
    }

    fun getRunnerDetails() : List<String> {
        return listOf(meetingCode, raceNo, runnerNo, runnerName, scratched, riderChanged, barrier,
                handicap, weight, rider!!, lastResult!!)
    }
}
