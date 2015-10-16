package com.ahalikov.m101j.week1;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * @author ahalikov
 */
public class HelloWorld {
    public static void main(String[] args) {
        Spark.get(new Route("/") {
            @Override
            public Object handle(final Request req, final Response resp) {
                return "Hello World From Spark\n";
            }
        });
    }
}
