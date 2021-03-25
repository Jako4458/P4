package Logging;

public interface ILogger {
    /**
     * Adds a logable object to the log.
     * @param log
     */
    public void add(Log log);

    /**
     * Clears all logs from the logger.
     */
    public void clear();

    /**
     * Prints all logs in the logger.
     */
    public void print();

    /**
     * Prints all the logs in the logger and clears the logs.
     */
    public default void dump() {
        print();
        clear();
    }
}
