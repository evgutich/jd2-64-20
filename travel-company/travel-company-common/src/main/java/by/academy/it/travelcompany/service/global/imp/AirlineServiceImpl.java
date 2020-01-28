package by.academy.it.travelcompany.service.global.imp;

import by.academy.it.travelcompany.dao.AirlineDAO;
import by.academy.it.travelcompany.dao.impl.AirlineDAOImpl;
import by.academy.it.travelcompany.service.global.AirlineService;
import by.academy.it.travelcompany.travelitem.airline.Airline;
import by.academy.it.travelcompany.travelitem.airline.AirlineEnum;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AirlineServiceImpl implements AirlineService {

    AirlineDAO airlineDAO = AirlineDAOImpl.getInstance();

    private static final AirlineServiceImpl INSTANCE = new AirlineServiceImpl();

    private AirlineServiceImpl() {
    }

    public static AirlineServiceImpl getInstance() {
        return INSTANCE;
    }

//CRUD

    @Override
    public Airline create(Airline airline) {
        log.info("Add new airline to Base{}", airline);
        try {
            Long id = airlineDAO.create(airline);
            airline.setId(id);
            log.info("Result {}", id);
            return airline;
        } catch (SQLException e) {
            log.error("Error while creating airline " + airline, e);
        }
        return null;
    }

    @Override
    public Optional<Airline> read(Long id) {
        log.info("Getting airline from Base{}", id);
        try {
            Optional<Airline> airline = airlineDAO.read(id);
            log.info("Result {}", airline);
            return airline;
        } catch (SQLException e) {
            log.error("Error while getting airline ", e);
        }
        return Optional.empty();
    }

    @Override
    public Airline update(Airline airline) {
        log.info("Updating airline from Base{}", airline);
        try {
            int update = airlineDAO.update(airline);
            log.info("Result {}", update);
        } catch (SQLException e) {
            log.error("Error while updating airline " + airline, e);
        }
        return airline;
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting airline from Base{}", id);
        try {
            airlineDAO.delete(id);
            log.info("Result {}", id);
        } catch (SQLException e) {
            log.error("Error while deleting airline " + id, e);
        }
    }

//!CRUD

    @Override
    public List<Airline> getAll() {
        log.info("Getting all airline from Base{}");
        try {
            return airlineDAO.getAll();

        } catch (SQLException e) {
            log.error("Error while getting all airline", e);
        }
        return Collections.emptyList();
    }

    @Override
    public void installAll() {
        log.info("Installing all airline to Base{}");
        try {
            for (AirlineEnum aE : AirlineEnum.values()) {
                Airline a = new Airline(null, aE.toString());
                airlineDAO.create(a);
            }
            log.info("Installing all airline to Base{} successfully ended");
        }catch (SQLException e){
            log.error("Error while installing all airline to base", e);
        }
    }

    @Override
    public Long getIdByName(String airlineName) {
        log.info("Getting airline id from Base{}", airlineName);
        Long result = null;
        try {
            result = airlineDAO.getIdByName(airlineName);
            log.info("Result {}", result);
            return result;
        } catch (SQLException e) {
            log.error("Error while getting airline id" + airlineName, e);
        }
        return result;
    }
}