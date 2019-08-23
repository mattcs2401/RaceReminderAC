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
        // TODO - the logic for lists of things, e.g. runners, goes in here.
        var eventType: Int = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                val tag = parser.name
                when(tag) {
                    "RaceDay" -> {
                        Log.i("", "RaceDay")
                        raceDay = readRaceDay(parser)
                    }
                    "Meeting" -> {
                        Log.i("", "Meeting")
                        meeting = readMeeting(parser, raceDay!!.rdId)
                    }
                    "Race" -> {
                        Log.i("", "Race")
                        race = readRace(parser, meeting!!.mtgId)
                        lRunners = arrayListOf<Runner>()
                    }
                    "Runner" -> {
                        Log.i("", "Runner")
                        runner = readRunner(parser, race!!.raceNo)
                        lRunners.add(runner!!)
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

    //<editor-fold default state="collapsed" desc="Region: Tags parsing.">
    /**
     * Parse the <RaceDay></RaceDay> tags.
     * @param parser: The XmlPullParser to use.
     * @return A RaceDay object.
     */
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

    /**
     * Parse the <Meeting></Meeting> tags.
     * @param parser: The XmlPullParser to use.
     * @return A Meeting object.
     */
    private fun readMeeting(parser: XmlPullParser, rdId: Long?): Meeting {
        val code = parser.getAttributeValue(nameSpace, "MeetingCode")
        val id = parser.getAttributeValue(nameSpace, "MtgId")
        val meeting = Meeting(rdId!!, id.toLong())
        meeting.meetingCode = code
        meeting.venueName = parser.getAttributeValue(nameSpace, "VenueName")
        meeting.mtgType = parser.getAttributeValue(nameSpace, "MtgType")
        meeting.trackDesc = parser.getAttributeValue(nameSpace, "TrackDesc")
        meeting.trackRating = parser.getAttributeValue(nameSpace, "TrackRating")
        meeting.weatherDesc = parser.getAttributeValue(nameSpace, "WeatherDesc")
        meeting.mtgAbandoned = parser.getAttributeValue(nameSpace, "MtgAbandoned")
        return meeting
    }

    /**
     * Parse the <Race></Race> tags.
     * @param parser: The XmlPullParser to use.
     * @param mtgId: The id of the Meeting.
     * @return A Race object.
     */
    private fun readRace(parser: XmlPullParser, mtgId: Long): Race {
        val rNo = parser.getAttributeValue(nameSpace, "RaceNo")
        val race = Race(mtgId, rNo.toLong())
        race.raceTime = parser.getAttributeValue(nameSpace, "RaceTime")
        race.raceName = parser.getAttributeValue(nameSpace, "RaceName")
        race.distance = parser.getAttributeValue(nameSpace, "Distance")
        return race
    }

    /**
     * Parse the <Runner></Runner> tags.
     * @param parser: The XmlPullParser to use.
     * @param raceNo: The race number of the Race.
     * @return A Runner object.
     */
    private fun readRunner(parser: XmlPullParser, raceNo: Long): Runner {
        val runnerNo =  parser.getAttributeValue(nameSpace, "RunnerNo")
        val runner = Runner(raceNo, runnerNo.toLong())
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
    //</editor-fold>

    /*
      Collate the parsed details into a "management" class.
     */
    private fun collateRaceDetails() {
        // TBA

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