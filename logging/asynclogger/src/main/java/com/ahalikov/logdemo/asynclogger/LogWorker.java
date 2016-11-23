package com.ahalikov.logdemo.asynclogger;

import org.apache.log4j.Logger;

/**
 * @author ahalikov
 */
class LogWorker {

    private final Logger logger = Logger.getLogger(getClass());

    public void log(String message) {
        logger.info(message);
    }
}
