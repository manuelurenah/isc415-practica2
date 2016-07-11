package edu.pucmm;

import edu.pucmm.handlers.TemplateHandler;
import spark.Spark;
import spark.Spark.*;
/**
 * Created by MEUrena on 5/21/16.
 * All rights reserved.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        Spark.port(port);

        Spark.staticFileLocation("/public");

        new TemplateHandler().startApp();

    }

}
