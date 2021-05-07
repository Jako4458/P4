package logging;

import logging.logs.Log;

public interface ILogger {
    /**
     * Adds a logable object to the log.
     * @param log
     */
    void add(Log log);

    /**
     * Clears all logs from the logger.
     */
    void clear();

    /**
     * Prints all logs in the logger.
     */
    void print(boolean all);

    /**
     * Prints all the logs in the logger and clears the logs.
     */
    void dump(boolean all);
}
