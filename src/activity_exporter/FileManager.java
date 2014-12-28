/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activity_exporter;

import activity_exporter.domain.OSFinder;
import activity_exporter.formats.SMLFile;
import activity_exporter.domain.SAXHandler;
import activity_exporter.formats.GPXOutput;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Petr
 */
public class FileManager
{

    private File fileIn;
    private StringBuilder sb;
    private File fileOut;
    private boolean fileSaved;
    private boolean correctFileFormat;

    private String activityName;

    public FileManager(String filename)
    {

        this();
        //setFileName(filename);
        //this.correctFileFormat = false;

    }

    public FileManager()
    {
        this.sb = new StringBuilder();
        this.activityName = "Workout-";

    }

    public void clear()
    {
        this.sb = new StringBuilder();
    }

    public void setInputFileName(String filename)
    {
        this.fileIn = new File(filename);

    }


    private void setOutputFileName()
    {
        File dir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Downloads");
        dir.mkdir();
        this.fileOut = new File(dir, fileOutName(fileIn.toString()));
    }

    public void processFile()
    {
        readFile();
        writeFile();
    }

    private void readFile()
    {
        
        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SAXHandler saxHandler = new SAXHandler();
            saxParser.parse(fileIn, saxHandler);
            
            GPXOutput gpxOut = new GPXOutput(saxHandler);
            
            activityName = saxHandler.getHeaderTags().getActivityName() + "-";
            sb = gpxOut.buildOutputFile();

            

            

        } catch (IOException e)
        {
            System.out.println("Exception Bluck");

        } catch (ParserConfigurationException | SAXException e)
        {
            e.printStackTrace();
            System.out.println("Exception Bluck");
        } 

    }

    private String fileOutName(String filename)
    {
        int dotIndex = filename.indexOf('.');
        String name = filename.substring(dotIndex - 21);
        String outSuffix = name.replace(".sml", ".gpx");

        return activityName + outSuffix;
    }

    private void writeFile()
    {
        try
        {
            setOutputFileName();
            FileWriter writer = new FileWriter(fileOut);
            writer.write(sb.toString());
            writer.close();
            this.fileSaved = true;
        } catch (IOException ex)
        {
            //Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Something is up with output file or folder.");
        }
    }

    public boolean getFileSaved()
    {
        return this.fileSaved;
    }

}
