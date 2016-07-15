package edu.pucmm;

import edu.pucmm.handlers.TemplateHandler;
import spark.Spark;
import org.h2.tools.Server;
import spark.Spark.*;

import java.sql.SQLException;

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

        Server server = null;
        try {
            server = Server.createTcpServer("-tcpAllowOthers").start();
        } catch (SQLException e) {
            System.out.println("There was an error opening the database:");
            e.printStackTrace();
        }

        new TemplateHandler().startApp();

    }

}
