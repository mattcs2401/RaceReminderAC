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
                        race = readRace(parser, meeting!!.mtgId)
                        lRunners = arrayListOf<Runner>()
                    }
                    "Runner" -> {
                        Log.i("", "Runner")
                        runner = readRunner(parser, race!!.raceNo)
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

    private fun readRace(parser: XmlPullParser, mtgId: String): Race {
        val rNo = parser.getAttributeValue(nameSpace, "RaceNo")
        val race = Race(mtgId, rNo)
        race.raceTime = parser.getAttributeValue(nameSpace, "RaceTime")
        race.raceName = parser.getAttributeValue(nameSpace, "RaceName")
        race.distance = parser.getAttributeValue(nameSpace, "Distance")
        return race
    }

    private fun readRunner(parser: XmlPullParser, raceNo: String): Runner {
        val runnerNo =  parser.getAttributeValue(nameSpace, "RunnerNo")
        val runner = Runner(raceNo, runnerNo)
        runner.runnerName = parser.getAttributeValue(nameSpace, "RunnerName")
        runner.scratched = parser.getAttributeValue(nameSpace, "Scratched")
        runner.rider = parser.getAttributeValue(nameSpace, "Rider")
        runner.riderChanged = parser.getAttributeValue(nameSpace, "RiderChanged")
        runner.barrier = parser.getAttributeValue(nameSpace, "Barrier")
        runner.handicap = parser.getAttributeValue(nameSpace, "Handicap")
        runner.weight = parser.getAttributeValue(nameSpace, "Weight")
        runner.lastResult = parser.getAttributeValue(nameSpace, "LastResult")
        runner.rtng = parser.getAttributeValue(nameSpace, "Rtng")
        return runner
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