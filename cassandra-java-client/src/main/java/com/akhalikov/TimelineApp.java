package com.akhalikov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class TimelineApp {
  private static final Logger log = LoggerFactory.getLogger(TimelineApp.class);

  public static void main(String[] args) {
    Properties properties = new Properties();
    properties.setProperty("nodes", "localhost");

    try (TimelineClient timelineClient = new TimelineClient(properties)) {



    } catch (Exception e) {
      log.error("Error", e);
    }
  }
}
