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
    private SMLFile headerTags;

    public GPXOutput(SAXHandler handler)
    {
        this.saxHandler = handler;
        this.headerTags = handler.getHeaderTags();
    }

    public StringBuilder buildOutputFile()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(writeHead(headerTags.getUtc(), headerTags.getActivityName()));
        SMLFile lastKnownSmlTags = new SMLFile();
        for (SMLFile Tag : saxHandler.smlTagList)
        {

            lastKnownSmlTags.setAltitude(Tag.getAltitude());
            lastKnownSmlTags.setUtc(Tag.getUtc());
            lastKnownSmlTags.setSimpleHr(Tag.getHr());
            lastKnownSmlTags.setSimpleCadence(Tag.getCadence());
            lastKnownSmlTags.setDistance(Tag.getDistance());
            lastKnownSmlTags.setSpeed(Tag.getSpeed());
            lastKnownSmlTags.setSimpleTemperature(Tag.getTemperature());

            if (Tag.isLocation())
            {
                lastKnownSmlTags.setSimpleLatitude(Tag.getLatitude());
                lastKnownSmlTags.setSimpleLongitude(Tag.getLongitude());
                builder.append(writeTrkptHead(lastKnownSmlTags.getLatitude(), lastKnownSmlTags.getLongitude()));
                builder.append(writeAltitude(lastKnownSmlTags.getAltitude()));
                builder.append(writeTime(lastKnownSmlTags.getUtc()));
                builder.append(writeExtensionHead(headerTags.isExtension()));
                builder.append(writeHR(lastKnownSmlTags.getHr(),headerTags.isHr()));
                builder.append(writeCadence(lastKnownSmlTags.getCadence(),headerTags.isCadence()));
                builder.append(writeTemperature(lastKnownSmlTags.getTemperature(),headerTags.isTemperature()));
                builder.append(writeDistance(lastKnownSmlTags.getDistance()));
               // builder.append(writeSpeed(lastKnownSmlTags.getSpeed())); not recognised by Strava
                builder.append(writeExtensionFoot(headerTags.isExtension()));
                builder.append(writeTrkptFoot());
            }
        }
        builder.append(writeFooter());

        return builder;
    }

    public String writeTrkptHead(String latitude, String longitude)
    {
        String openTag = "   <trkpt";

        return String.format("%s lat=\"%s\" lon=\"%s\">\n", openTag, latitude, longitude);
    }

    public String writeTrkptFoot()
    {
        return "   </trkpt>\n";
    }

    public String writeAltitude(String altitude)
    {
        if (altitude != null)
        {
            return String.format("    <ele>%s</ele>\n", altitude);
        }
        return "";
    }

    public String writeTime(String utc)
    {
        return String.format("    <time>%s</time>\n", utc);
    }
    
    public String writeExtensionHead(boolean isExtension)
    {
        if(isExtension)
        {
            return "    <extensions>\n"
                  +"     <gpxtpx:TrackPointExtension>\n";
        }
        return "";
    }
    
    public String writeExtensionFoot(boolean isExtension)
    {
        if(isExtension)
        {
            return "     </gpxtpx:TrackPointExtension>\n"
                  +"    </extensions>\n";
        }
        return "";
    }

    public String writeHR(String hr, boolean isHr)
    {
        if(isHr)
        {
            if(null != hr)
            {
                return String.format("      <gpxtpx:hr>%s</gpxtpx:hr>\n", hr);
            }else
            {
                return String.format("      <gpxtpx:hr>%s</gpxtpx:hr>\n", "0");
            }
        }
        return "";
    }

    public String writeCadence(String cadence, boolean isCadence)
    {
        if(isCadence)
        {
            if(null != cadence)
            {
                return String.format("      <gpxtpx:cad>%s</gpxtpx:cad>\n", cadence);
            }else
            {
                return String.format("      <gpxtpx:cad>%s</gpxtpx:cad>\n", "0");
            }
        }
        return "";
    }
    
        public String writeTemperature(String temperature, boolean isTemperature)
    {
        if(isTemperature)
        {
            return String.format("      <gpxtpx:atemp>%s</gpxtpx:atemp>\n", temperature);
        }
        return "";
    }

    public String writeDistance(String distance)
    {
        return String.format("      <gpxtpx:distance>%s</gpxtpx:distance>\n", distance);
    }

    public String writeSpeed(String speed)
    {
        return String.format("      <gpxdata:speed>%s</gpxdata:speed>\n", speed);
    }

    public String writeHead(String time, String name)
    {
        return String.format("<gpx xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd "
                + "http://www.cluetrust.com/XML/GPXDATA/1/0 http://www.cluetrust.com/Schemas/gpxdata10.xsd http://www.garmin.com/xmlschemas/TrackPointExtension/v1 "
                + "http://www.garmin.com/xmlschemas/TrackPointExtensionv1.xsd\" xmlns:gpxdata=\"http://www.topografix.com/GPX/1/0\" "
                + "xmlns:gpxtpx=\"http://www.garmin.com/xmlschemas/TrackPointExtension/v1\" "
                + "version=\"1.1\" creator=\"ActivityExporter\" xmlns=\"http://www.topografix.com/GPX/1/1\">\n"
                + " <metadata>\n"
                + "  <time>%s</time>\n"
                + " </metadata>\n"
                + " <trk>\n"
                + "  <name>%s</name>\n"
                + "  <trkseg>\n",time, name);
    }

    public String writeFooter()
    {
        return "    </trkseg>\n"
                + "  </trk>\n"
                + "</gpx>";
    }

}
