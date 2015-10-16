package com.ahalikov.m101j.week1;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class SparkRoutes {
    public static void main(String[] args) {
        Spark.get(new Route("/") {
            @Override
            public Object handle(final Request req, final Response resp) {
                return "Hello World\n";
            }
        });

        Spark.get(new Route("/test") {
            @Override
            public Object handle(final Request req, final Response resp) {
                return "This is a test page\n";
            }
        });

        Spark.get(new Route("/echo/:thing") {
            @Override
            public Object handle(final Request req, final Response resp) {
                return req.params(":thing");
            }
        });
    }
}
