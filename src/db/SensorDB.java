package db;

import exceptions.IllegalOperationException;
import exceptions.NoDBAccessException;
import sensor.SensorData;

public interface SensorDB {
    /**
     * Store SensorData in Data Base
     * @param sd to be stored
     * @throws NoDBAccessException
     * Unable to save sd
     */
    void save(SensorData sd) throws NoDBAccessException;

    /**
     * How many sensor data elements are stored for sensor name
     * @param name name of sensor
     * @return count of stored sensors with name, 0 if no sensor data of name exists
     * @throws NoDBAccessException
     * Couldn't access database
     */
    int count(String name) throws NoDBAccessException;

    /**
     * Calculates the total of all measurements for all sensors
     * @return total of all measurements
     * @throws NoDBAccessException
     * Couldn't access database
     */
    float getTotal() throws NoDBAccessException;

    /**
     * Count all sensor elements in db
     * @return
     */
    int countAll() throws NoDBAccessException;

    /**
     * Calculates the average of all data points for all db elements
     * (sum of all measurements / number of elements)
     * @return 0 if there is no
     * @throws NoDBAccessException couldn't access database
     * @throws IllegalOperationException when there is no data to calculate Average; i.e. no elements in db
     */
    float getAverageAll() throws NoDBAccessException, IllegalOperationException;

    /**
     * Calculate the average for all measurements of sensor data with name
     * (sum of all sensor data for name / number of sensor data for name)
     * @param name
     * @return average of measurements for name
     * @throws NoDBAccessException
     * couldn't access dab
     * @throws IllegalOperationException
     * If sensor data for name is not stored
     */
    float getAverage(String name) throws NoDBAccessException, IllegalOperationException;
}
