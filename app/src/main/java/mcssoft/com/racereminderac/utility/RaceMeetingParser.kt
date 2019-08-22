package mcssoft.com.racereminderac.utility

import android.content.Context
import android.util.Log
import android.util.Xml
import mcssoft.com.racereminderac.entity.xml.Meeting
import mcssoft.com.racereminderac.entity.xml.RaceXml
import mcssoft.com.racereminderac.entity.xml.RaceDay
import mcssoft.com.racereminderac.entity.xml.Runner
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

// https://tatts.com/pagedata/racing/2019/8/19/NR1.xml
// https://developer.android.com/training/basics/network-ops/xml
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

//    fun getRace(): RaceXml? {
//        return raceXml
//    }

//    fun getRunner(num: Int): Runner? {
//        val ndx = num - 1
//        return runnersList.get(ndx)
//    }

//    fun getRunners(): ArrayList<Runner>? {
//        return runnersList
//    }

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
                    "RaceXml" -> {
                        Log.i("", "RaceXml")
                        raceXml = readRace(parser)
                        raceXml!!.mtgId = meeting!!.mtgId
                        raceXml!!.meetingCode = meeting!!.meetingCode
                        runnersList = arrayListOf<Runner>()
                    }
                    "Runner" -> {
                        Log.i("", "Runner")
                        runner = readRunner(parser)
                        runner!!.raceNo = raceXml!!.raceNo
//                        runner!!.meetingCode = raceXml!!.meetingCode
                        runnersList.add(runner!!)
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
        val lRaceDay = arrayOf("","","","","","")
        lRaceDay[0] = parser.getAttributeValue(nameSpace,"RaceDayDate")
        lRaceDay[1] = parser.getAttributeValue(nameSpace,"Year")
        lRaceDay[2] = parser.getAttributeValue(nameSpace,"Month")
        lRaceDay[3] = parser.getAttributeValue(nameSpace,"Day")
        lRaceDay[4] = parser.getAttributeValue(nameSpace,"DayOfTheWeek")
        lRaceDay[5] = parser.getAttributeValue(nameSpace,"MonthLong")

        val raceDay = RaceDay(lRaceDay[1], lRaceDay[2], lRaceDay[3])
        raceDay.raceDayDate = lRaceDay[1]
        raceDay.raceDayOfTheWeek = lRaceDay[4]
        raceDay.raceMonthLong = lRaceDay[5]
        return raceDay
    }

    private fun readMeeting(parser: XmlPullParser): Meeting {
        val lMeeting = arrayOf("","","","","","","","")
        lMeeting[0] = parser.getAttributeValue(nameSpace, "MeetingCode")
        lMeeting[1] = parser.getAttributeValue(nameSpace, "MtgId")
        lMeeting[2] = parser.getAttributeValue(nameSpace, "VenueName")
        lMeeting[3] = parser.getAttributeValue(nameSpace, "MtgType")
        lMeeting[4] = parser.getAttributeValue(nameSpace, "TrackDesc")
        lMeeting[5] = parser.getAttributeValue(nameSpace, "TrackRating")
        lMeeting[6] = parser.getAttributeValue(nameSpace, "WeatherDesc")
        lMeeting[7] = parser.getAttributeValue(nameSpace, "MtgAbandoned")

        val meeting = Meeting(lMeeting[0], lMeeting[1])
        meeting.venueName = lMeeting[2]
        meeting.mtgType = lMeeting[3]
        meeting.trackDesc = lMeeting[4]
        meeting.trackRating = lMeeting[5]
        meeting.weatherDesc = lMeeting[6]
        meeting.mtgAbandoned = lMeeting[7]
        return meeting
    }

    private fun readRace(parser: XmlPullParser): RaceXml {
        val lRace = arrayOf("","","","")
        lRace[0] = parser.getAttributeValue(nameSpace, "RaceNo")
        lRace[1] = parser.getAttributeValue(nameSpace, "RaceTime")
        lRace[2] = parser.getAttributeValue(nameSpace, "RaceName")
        lRace[3] = parser.getAttributeValue(nameSpace, "Distance")

        val race = RaceXml(meeting!!.meetingCode, meeting!!.mtgId, lRace[0], lRace[1])
        race.raceName = lRace[2]
        race.distance = lRace[3]
        return race
    }

    private fun readRunner(parser: XmlPullParser): Runner {
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

        val runner = Runner(raceXml!!.raceNo, raceXml!!.raceTime, lRunner[0], lRunner[1])
        runner.scratched = lRunner[2]
        runner.rider = lRunner[3]
        runner.riderChanged = lRunner[4]
        runner.barrier = lRunner[5]
        runner.handicap = lRunner[6]
        runner.weight = lRunner[7]
        runner.lastResult = lRunner[8]
        runner.rtng = lRunner[9]
        return runner
    }

    /*
      Collate the parsed details into a "management" class.
     */
    private fun collateRaceDetails() {
//        RaceDetails.getInstance(context).addRaceDay(raceDay!!.getRaceDayDetails())
//        RaceDetails.getInstance(context).addMeeting(meeting!!.getMeetingDetails())
//        RaceDetails.getInstance(context).addRace(raceXml!!.getRaceDetails())
//
//        val listing: MutableList<List<String>> = arrayListOf()
//            for(runner in runnersList) {
//                listing.add(runner.getRunnerDetails())
//            }
//
//        RaceDetails.getInstance(context).addRunners(listing)

        // Tidy up.
        raceDay = null
        meeting = null
        raceXml = null
        runner = null
        runnersList.clear()
    }

    // We don't use namespaces
    private val nameSpace: String? = null
    private var meeting: Meeting? = null
    private var raceDay: RaceDay? = null
    private var raceXml: RaceXml? = null
    private var runner: Runner? = null                     // single runner details.
    private lateinit var runnersList: ArrayList<Runner>//? = null     // multiple runners per raceXml.
}