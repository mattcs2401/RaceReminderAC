package mcssoft.com.racereminderac.utility

import android.content.Context
import android.util.Log
import android.util.Xml
import mcssoft.com.racereminderac.entity.xml.Meeting
import mcssoft.com.racereminderac.entity.xml.Race
import mcssoft.com.racereminderac.entity.xml.RaceDay
import mcssoft.com.racereminderac.entity.xml.Runner
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

// https://tatts.com/pagedata/racing/2019/8/19/NR1.xml
// https://developer.android.com/training/basics/network-ops/xml
/**
 * Utility class to parse the race day xml into various lists.
 */
class RaceMeetingParser constructor(val context: Context) {

    // TODO - What about parser exceptions ? This class needs to be more robust.

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream) {
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            readFeed(parser)
            collateRaceDetails()
        }
    }

    private fun readFeed(parser: XmlPullParser) {
        var tag: String = ""
// TODO - the logic for lists of things, e.g. runners, goes in here.
        var eventType: Int = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                tag = parser.name
                when(tag) {
                    "RaceDay" -> {
                        Log.i("", "RaceDay")
                        raceDay = readRaceDay(parser)
                    }
                    "Meeting" -> {
                        Log.i("", "Meeting")
                        meeting = readMeeting(parser)
                    }
                    "Race" -> {
                        Log.i("", "Race")
                        race = readRace(parser)
                        lRunners = arrayListOf<Runner>()
                    }
                    "Runner" -> {
                        Log.i("", "Runner")
//                        runner = readRunner(parser)
//                        runner!!.raceNo = raceXml!!.raceNo
////                        runner!!.meetingCode = raceXml!!.meetingCode
//                        runnersList.add(runner!!)
                    }
                }
            }
            else if (eventType == XmlPullParser.END_TAG) {
                // finnish up ?
                val TBA = ""
            }
            eventType = parser.next()
        }
    }

    private fun readRaceDay(parser: XmlPullParser): RaceDay {
        val date = parser.getAttributeValue(nameSpace,"RaceDayDate")
        val raceDay = RaceDay(date)
        raceDay.raceYear = parser.getAttributeValue(nameSpace,"Year")
        raceDay.raceMonth = parser.getAttributeValue(nameSpace,"Month")
        raceDay.raceDay = parser.getAttributeValue(nameSpace,"Day")
        raceDay.raceDayOfTheWeek = parser.getAttributeValue(nameSpace,"DayOfTheWeek")
        raceDay.raceMonthLong = parser.getAttributeValue(nameSpace,"MonthLong")
        return raceDay
    }

    private fun readMeeting(parser: XmlPullParser): Meeting {
        val code = parser.getAttributeValue(nameSpace, "MeetingCode")
        val id = parser.getAttributeValue(nameSpace, "MtgId")
        val meeting = Meeting(id)
        meeting.meetingCode = code
        meeting.venueName = parser.getAttributeValue(nameSpace, "VenueName")
        meeting.mtgType = parser.getAttributeValue(nameSpace, "MtgType")
        meeting.trackDesc = parser.getAttributeValue(nameSpace, "TrackDesc")
        meeting.trackRating = parser.getAttributeValue(nameSpace, "TrackRating")
        meeting.weatherDesc = parser.getAttributeValue(nameSpace, "WeatherDesc")
        meeting.mtgAbandoned = parser.getAttributeValue(nameSpace, "MtgAbandoned")
        return meeting
    }

    private fun readRace(parser: XmlPullParser): Race {
        val rNo = parser.getAttributeValue(nameSpace, "RaceNo")
        val race = Race(rNo)
        race.raceTime = parser.getAttributeValue(nameSpace, "RaceTime")
        race.raceName = parser.getAttributeValue(nameSpace, "RaceName")
        race.distance = parser.getAttributeValue(nameSpace, "Distance")
        return race
    }

    private fun readRunner(parser: XmlPullParser): Array<String> {
        val lRunner = arrayOf("","","","","","","","","","")
        lRunner[0] = parser.getAttributeValue(nameSpace, "RunnerNo")
        lRunner[1] = parser.getAttributeValue(nameSpace, "RunnerName")
        lRunner[2] = parser.getAttributeValue(nameSpace, "Scratched")
        lRunner[3] = parser.getAttributeValue(nameSpace, "Rider")
        lRunner[4] = parser.getAttributeValue(nameSpace, "RiderChanged")
        lRunner[5] = parser.getAttributeValue(nameSpace, "Barrier")
        lRunner[6] = parser.getAttributeValue(nameSpace, "Handicap")
        lRunner[7] = parser.getAttributeValue(nameSpace, "Weight")
        lRunner[8] = parser.getAttributeValue(nameSpace, "LastResult")
        lRunner[9] = parser.getAttributeValue(nameSpace, "Rtng")

//        val runner = Runner(raceXml!!.raceNo, raceXml!!.raceTime, lRunner[0], lRunner[1])
//        runner.scratched = lRunner[2]
//        runner.rider = lRunner[3]
//        runner.riderChanged = lRunner[4]
//        runner.barrier = lRunner[5]
//        runner.handicap = lRunner[6]
//        runner.weight = lRunner[7]
//        runner.lastResult = lRunner[8]
//        runner.rtng = lRunner[9]
        return lRunner
    }

    /*
      Collate the parsed details into a "management" class.
     */
    private fun collateRaceDetails() {

        // Tidy up.
        raceDay = null
        meeting = null
        race = null
        runner = null
        lRunners.clear()
    }

    // We don't use namespaces
    private val nameSpace: String? = null
    private var meeting: Meeting? = null
    private var raceDay: RaceDay? = null
    private var race: Race? = null
    private var runner: Runner? = null                  // single runner details.
    private lateinit var lRunners: ArrayList<Runner>    // multiple runners.
}