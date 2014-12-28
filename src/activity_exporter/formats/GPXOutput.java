/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activity_exporter.formats;

import activity_exporter.domain.SAXHandler;

/**
 *
 * @author Petr
 */
public class GPXOutput
{

    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "lon";
    public static final String ALTITUDE = "ele";
    public static final String TIME = "time";
    public static final String HR = "hr";
    public static final String CADENCE = "Cadence";
    public static final String DISTANCE = "Distance";
    public static final String SPEED = "Speed";
    public static final String VERTICAL_SPEED = "VerticalSpeed";
    public static final String UTC = "UTC";
    public static final String TEMPERATURE = "Temperature";
    public static final String WATTS = "Watts";
    public static final String SAMPLE_TYPE = "SampleType";

    public boolean isLocation = false;
    private SAXHandler saxHandler;

    public GPXOutput(SAXHandler handler)
    {
        this.saxHandler = handler;
    }

    public StringBuilder buildOutputFile()
    {
        StringBuilder builder = new StringBuilder();
        int counter = 0;
        builder.append(appendHead());
        SMLFile lastKnownSmlTags = new SMLFile();
        for (SMLFile smlTag : saxHandler.smlTagList)
        {

            lastKnownSmlTags.setAltitude(smlTag.getAltitude());
            lastKnownSmlTags.setUtc(smlTag.getUtc());
            lastKnownSmlTags.setSimpleHr(smlTag.getHr());
            lastKnownSmlTags.setSimpleCadence(smlTag.getCadence());
            lastKnownSmlTags.setDistance(smlTag.getDistance());
            lastKnownSmlTags.setSpeed(smlTag.getSpeed());

            if (smlTag.isLocation)
            {
                lastKnownSmlTags.setSimpleLatitude(smlTag.getLatitude());
                lastKnownSmlTags.setSimpleLongitude(smlTag.getLongitude());
                builder.append(writeTrkptHead(lastKnownSmlTags.getLatitude(), lastKnownSmlTags.getLongitude()));
                builder.append(writeAltitude(lastKnownSmlTags.getAltitude()));
                builder.append(writeTime(lastKnownSmlTags.getUtc()));
                builder.append(writeHR(lastKnownSmlTags.getHr()));
                builder.append(writeCadence(lastKnownSmlTags.getCadence()));
                builder.append(writeDistance(lastKnownSmlTags.getDistance()));
                builder.append(writeSpeed(lastKnownSmlTags.getSpeed()));
                builder.append(writeTrkptFoot());
            }
            counter++;
        }
        builder.append(appendFooter());

        return builder;
    }

    public String writeTrkptHead(String latitude, String longitude)
    {
        String openTag = "      <trkpt";

        return String.format("%s lat=\"%s\" lon=\"%s\">\n", openTag, latitude, longitude);
    }

    public String writeTrkptFoot()
    {
        return "      </trkpt>\n";
    }

    public String writeAltitude(String altitude)
    {
        if (altitude != null)
        {
            return String.format("        <ele>%s</ele>\n", altitude);
        }
        return "";
    }

    public String writeTime(String utc)
    {
        return String.format("        <time>%s</time>\n", utc);
    }

    public String writeHR(String hr)
    {
        return String.format("          <gpxtpx:TrackPointExtension xmlns:gpxtpx=\"http://www.garmin.com/xmlschemas/TrackPointExtension/v1\">\n"
                + "            <gpxtpx:hr>%s</gpxtpx:hr>\n"
                + "          </gpxtpx:TrackPointExtension>\n", hr);
    }

    public String writeCadence(String cadence)
    {
        return String.format("          <gpxdata:cadence>%s</gpxdata:cadence>\n", cadence);
    }

    public String writeDistance(String distance)
    {
        return String.format("          <gpxdata:distance>%s</gpxdata:distance>\n", distance);
    }

    public String writeSpeed(String speed)
    {
        return String.format("          <gpxdata:speed>%s</gpxdata:speed>\n", speed);
    }

    public String appendHead()
    {
        return "<gpx xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd "
                + "http://www.cluetrust.com/XML/GPXDATA/1/0 http://www.cluetrust.com/Schemas/gpxdata10.xsd http://www.garmin.com/xmlschemas/TrackPointExtension/v1 "
                + "http://www.garmin.com/xmlschemas/TrackPointExtensionv1.xsd\" xmlns:gpxdata=\"http://www.topografix.com/GPX/1/0\" "
                + "xmlns:gpxtpx=\"http://www.garmin.com/xmlschemas/TrackPointExtension/v1\" "
                + "version=\"1.1\" creator=\"ActivityExporter\" xmlns=\"http://www.topografix.com/GPX/1/1\">\n"
                + "  <trk>\n"
                + "    <trkseg>\n";
    }

    public String appendFooter()
    {
        return "    </trkseg>\n"
                + "  </trk>\n"
                + "</gpx>";
    }

}
