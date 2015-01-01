/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activity_exporter.formats;

/**
 *
 * @author Petr
 */
public class SMLFile
{

    private String latitude;
    private String longitude;
    private String altitude;
    private String time;
    private String hr;
    private String cadence;
    private String distance;
    private String speed;
    private String verticalSpeed;
    private String energy;
    private String watts;
    private String temperature;
    private String utc;
    private String sampleType;
    private boolean isLocation = false;
    private String activityName;
    private boolean isHr;
    private boolean isCadence;
    private boolean isTemperature;

    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longitude";
    public static final String ALTITUDE = "Altitude";
    public static final String GPS_ALTITUDE = "GPSAltitude";
    public static final String TIME = "Time";
    public static final String HR = "HR";
    public static final String CADENCE = "Cadence";
    public static final String DISTANCE = "Distance";
    public static final String SPEED = "Speed";
    public static final String VERTICAL_SPEED = "VerticalSpeed";
    public static final String UTC = "UTC";
    public static final String TEMPERATURE = "Temperature";
    public static final String WATTS = "Watts";
    public static final String SAMPLE_TYPE = "SampleType";
    public static final String SAMPLE = "Sample";
    public static final String DATE_TIME = "DateTime";
    public static final String ACTIVITY = "Activity";
    public static final String HEADER = "Header";

    public SMLFile()
    {

    }

    public String convertTemp(String temperatureInK)
    {
        return String.valueOf(Integer.parseInt(temperatureInK) - 272);
    }

    public String convertHR(String hr)
    {
        return String.valueOf(Math.round(Double.parseDouble(hr) * 60));
    }

    public String convertCadence(String cadence)
    {
        return String.valueOf(Math.round(Double.parseDouble(cadence) * 60));
    }

    public String convertRadToDegrees(String loc)
    {
        return String.format("%.6f", Math.toDegrees(Double.parseDouble(loc)));
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        if (!isNullOrBlank(latitude))
        {
            this.latitude = convertRadToDegrees(latitude);
            this.isLocation = true;
        }

    }

    public void setSimpleLatitude(String latitude)
    {
        if (!isNullOrBlank(latitude))
        {
            this.latitude = latitude;
            this.isLocation = true;
        }

    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        if (!isNullOrBlank(longitude))
        {
            this.longitude = convertRadToDegrees(longitude);
        }
    }

    public void setSimpleLongitude(String longitude)
    {
        if (!isNullOrBlank(longitude))
        {
            this.longitude = longitude;
        }
    }

    public String getAltitude()
    {
        return altitude;
    }

    public void setAltitude(String altitude)
    {
        if (!isNullOrBlank(altitude))
        {
            this.altitude = altitude;
        }

    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getHr()
    {
        return hr;
    }

    public void setHr(String hr)
    {
        if (!isNullOrBlank(hr))
        {
            this.hr = convertHR(hr);
        }

    }

    public void setSimpleHr(String hr)
    {
        if (!isNullOrBlank(hr))
        {
            this.hr = hr;
        }

    }

    public String getCadence()
    {
        return cadence;
    }

    public void setCadence(String cadence)
    {
        if (!isNullOrBlank(cadence))
        {
            this.cadence = convertCadence(cadence);
        }

    }

    public void setSimpleCadence(String cadence)
    {
        if (!isNullOrBlank(cadence))
        {
            this.cadence = cadence;
        }

    }

    public String getDistance()
    {
        return distance;
    }

    public void setDistance(String distance)
    {
        if (!isNullOrBlank(distance))

        {
            this.distance = distance;
        }
    }

    public String getSpeed()
    {
        return speed;
    }

    public void setSpeed(String speed)
    {
        if (!isNullOrBlank(speed))
        {
            this.speed = speed;
        }
    }

    public String getVerticalSpeed()
    {
        return verticalSpeed;
    }

    public void setVerticalSpeed(String verticalSpeed)
    {
        this.verticalSpeed = verticalSpeed;
    }

    public String getEnergy()
    {
        return energy;
    }

    public void setEnergy(String energy)
    {
        this.energy = energy;
    }

    public String getWatts()
    {
        return watts;
    }

    public void setWatts(String watts)
    {
        this.watts = watts;
    }

    public String getTemperature()
    {
        return temperature;
    }

    public void setTemperature(String temperature)
    {
        this.temperature = convertTemp(temperature);
    }

    public void setSimpleTemperature(String temperature)
    {
        if (!isNullOrBlank(temperature))
        {
            this.temperature = temperature;
        }
    }

    public String getUtc()
    {
        return utc;
    }

    public void setUtc(String utc)
    {
        if (!isNullOrBlank(utc))
        {
            this.utc = utc;
        }
    }

    public String getSampleType()
    {
        return sampleType;
    }

    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }

    public String getActivityName()
    {
        return activityName;
    }

    public void setActivityName(String activityName)
    {
        if (this.activityName == null)

        {
            this.activityName = activityName;

        }
    }

    private boolean isNullOrBlank(String s)
    {
        return (s == null || s.trim().equals(""));
    }

    public boolean isLocation()
    {
        return isLocation;
    }

    public void setIsLocation(boolean isLocation)
    {
        this.isLocation = isLocation;
    }

    public boolean isHr()
    {
        return isHr;
    }

    public void setIsHr(boolean isHr)
    {
        this.isHr = isHr;
    }

    public boolean isCadence()
    {
        return isCadence;
    }

    public void setIsCadence(boolean isCadence)
    {
        this.isCadence = isCadence;
    }

    public boolean isTemperature()
    {
        return isTemperature;
    }

    public void setIsTemperature(boolean isTemperature)
    {
        this.isTemperature = isTemperature;
    }

    public boolean isExtension()
    {
        if (isHr || isCadence || isTemperature)
        {
            return true;
        }
        return false;
    }

}
