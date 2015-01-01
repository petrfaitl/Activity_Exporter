/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activity_exporter.domain;

import activity_exporter.formats.SMLFile;
import activity_exporter.FileManager;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Petr
 */
/**
 * The Handler for SAX Events.
 */
public class SAXHandler extends DefaultHandler
{

    public List<SMLFile> smlTagList = new ArrayList<>();
    public SMLFile smlTags = null;
    private SMLFile headerTags;
    public String content = null;

    @Override
    //Triggered when the start of tag is found.
    public void startElement(String uri, String localName,
            String qName, Attributes attributes)
            throws SAXException
    {

        switch (qName)
        {
            //Create a new SMLFile object when the start tag is found
            case SMLFile.SAMPLE:
                smlTags = new SMLFile();
                break;
            case SMLFile.HEADER:
                headerTags = new SMLFile();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName,
            String qName) throws SAXException
    {
        switch (qName)
        {
            //Add the employee to list once end tag is found
            case SMLFile.SAMPLE:
                smlTagList.add(smlTags);
                break;
            case SMLFile.ACTIVITY:
                headerTags.setActivityName(content);
                break;
            case SMLFile.DATE_TIME:
                headerTags.setUtc(content);
                break;

            //For all other end tags the employee has to be updated.
            case SMLFile.LATITUDE:
                smlTags.setLatitude(content);

                break;
            case SMLFile.LONGITUDE:
                smlTags.setLongitude(content);
                break;
            case SMLFile.ALTITUDE:
                if (smlTags != null)
                {
                    smlTags.setAltitude(content);
                }
                break;
            case SMLFile.GPS_ALTITUDE:
                if (smlTags != null)
                {
                    smlTags.setAltitude(content);
                }
                break;
            case SMLFile.UTC:
                if (smlTags != null)
                {
                    smlTags.setUtc(content);
                }
                break;
            case SMLFile.HR:
                if (smlTags != null)
                {
                    smlTags.setHr(content);
                    headerTags.setIsHr(true);
                }
                break;
            case SMLFile.CADENCE:
                if (smlTags != null)
                {
                    smlTags.setCadence(content);
                    headerTags.setIsCadence(true);
                }
                break;
            case SMLFile.DISTANCE:
                if (smlTags != null)
                {
                    smlTags.setDistance(content);
                }
                break;
            case SMLFile.SPEED:
                if (smlTags != null)
                {
                    smlTags.setSpeed(content);
                }
                break;
            case SMLFile.VERTICAL_SPEED:
                if (smlTags != null)
                {
                    smlTags.setVerticalSpeed(content);
                }
                break;
            case SMLFile.TEMPERATURE:
                if (smlTags != null)
                {
                    smlTags.setTemperature(content);
                    headerTags.setIsTemperature(true);
                }
                break;
            case SMLFile.WATTS:
                if (smlTags != null)
                {
                    smlTags.setWatts(content);
                }
                break;
            case SMLFile.SAMPLE_TYPE:
                if (smlTags != null)
                {
                    smlTags.setSampleType(content);
                }
                break;

        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException
    {
        content = String.copyValueOf(ch, start, length).trim();
    }

    public SMLFile getHeaderTags()
    {
        return headerTags;
    }

}
