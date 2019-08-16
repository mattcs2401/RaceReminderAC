package mcssoft.com.racereminderac.utility

import android.util.Log
import android.util.Xml
import mcssoft.com.racereminderac.utility.pojo.Meeting
import mcssoft.com.racereminderac.utility.pojo.Race
import mcssoft.com.racereminderac.utility.pojo.RaceDay
import mcssoft.com.racereminderac.utility.pojo.Runner
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

// https://developer.android.com/training/basics/network-ops/xml
class RaceMeetingParser {

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream) {
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            readFeed(parser)
        }
    }
//    fun parse(inputStream: InputStream): List<*> {
//        inputStream.use { inputStream ->
//            val parser: XmlPullParser = Xml.newPullParser()
//            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
//            parser.setInput(inputStream, null)
//            return readFeed(parser)
//        }
//    }

    fun getRace(): Race? {
        return race
    }

    fun getRunner(num: Int): Runner? {
        val ndx = num - 1
        return runnersList[ndx]
    }

    fun getRunners(): ArrayList<Runner>? {
        return runnersList
    }

    private fun readFeed(parser: XmlPullParser) {
        var tag: String = ""

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
                        runnersList = arrayListOf<Runner>()
                    }
                    "Runner" -> {
                        Log.i("", "Runner")
                        runner = readRunner(parser)
                        runnersList.add(runner)
                    }
                }
            }
            else if (eventType == XmlPullParser.END_TAG) {
                // finnish up ?
            }
            eventType = parser.next()
        }
    }

    private fun readRaceDay(parser: XmlPullParser): RaceDay {
        val raceDay = RaceDay()
        raceDay.raceDayDate = parser.getAttributeValue(nameSpace,"RaceDayDate")
        raceDay.raceYear = parser.getAttributeValue(nameSpace,"Year")
        raceDay.raceMonth = parser.getAttributeValue(nameSpace,"Month")
        raceDay.raceDay = parser.getAttributeValue(nameSpace,"Day")
        raceDay.raceDayOfTheWeek = parser.getAttributeValue(nameSpace,"DayOfTheWeek")
        raceDay.raceMonthLong = parser.getAttributeValue(nameSpace,"MonthLong")
        return raceDay
    }

    private fun readMeeting(parser: XmlPullParser): Meeting {
        val meeting = Meeting()
        meeting.meetingCode = parser.getAttributeValue(nameSpace, "MeetingCode")
        meeting.mtgId = parser.getAttributeValue(nameSpace, "MtgId")
        meeting.venueName = parser.getAttributeValue(nameSpace, "VenueName")
        meeting.mtgType = parser.getAttributeValue(nameSpace, "MtgType")
        meeting.trackDesc = parser.getAttributeValue(nameSpace, "TrackDesc")
        meeting.trackRating = parser.getAttributeValue(nameSpace, "TrackRating")
        meeting.weatherDesc = parser.getAttributeValue(nameSpace, "WeatherDesc")
        meeting.mtgAbandoned = parser.getAttributeValue(nameSpace, "MtgAbandoned")
        return meeting
    }

    private fun readRace(parser: XmlPullParser): Race {
        val race = Race()
        race.raceNo = parser.getAttributeValue(nameSpace, "RaceNo")
        race.raceTime = parser.getAttributeValue(nameSpace, "RaceTime")
        race.raceName = parser.getAttributeValue(nameSpace, "RaceName")
        race.distance = parser.getAttributeValue(nameSpace, "Distance")
        return race
    }

    private fun readRunner(parser: XmlPullParser): Runner {
        val runner = Runner()
        runner.runnerNo = parser.getAttributeValue(nameSpace, "RunnerNo")
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

    // We don't use namespaces
    private val nameSpace: String? = null
    private lateinit var meeting: Meeting
    private lateinit var raceDay: RaceDay
    private lateinit var race: Race
    private lateinit var runner: Runner
    private lateinit var runnersList: ArrayList<Runner>
}