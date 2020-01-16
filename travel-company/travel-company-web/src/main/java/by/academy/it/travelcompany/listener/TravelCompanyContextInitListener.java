package by.academy.it.travelcompany.listener;

import by.academy.it.travelcompany.db.connection.pool.TcDataSource;
import by.academy.it.travelcompany.db.migration.DbMigration;
import by.academy.it.travelcompany.service.global.AirlineService;
import by.academy.it.travelcompany.service.global.imp.AirlineServiceImpl;
import by.academy.it.travelcompany.travelitem.airline.Airline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.util.ResourceBundle;

@WebListener()
    public class TravelCompanyContextInitListener implements ServletContextListener{

        private static final Logger LOGGER = LoggerFactory.getLogger(TravelCompanyContextInitListener.class);

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            LOGGER.info("Context initialized");
            try {
                ResourceBundle bundle = ResourceBundle.getBundle("mysql_hikari");
                TcDataSource.configure(bundle);
                DataSource dataSource = TcDataSource.getDataSource();
                DbMigration.migrate(dataSource);


            } catch (Exception e) {
                LOGGER.error("error", e);
                throw new RuntimeException("Datasource initialisation error", e);
            }
            //AirportServiceImpl.getInstance().allAllAirportToBase();
            //RouteMapServiceImpl.getInstance().addToBase("RY--VNO--BGY--Direct");
            //new AirlineServiceImpl().add(new Airline(null,"BOBO"));



        }

    }

