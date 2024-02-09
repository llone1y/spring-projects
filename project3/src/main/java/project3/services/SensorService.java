package project3.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project3.models.Sensor;
import project3.repositories.SensorRepository;
import project3.util.SensorNotFoundException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    public Optional<Sensor> findSensor(String name) {
        return sensorRepository.findSensorByName(name);
    }

    public Sensor findSensorByNameOrThrowException(String name) {
        return sensorRepository.findSensorByName(name).orElseThrow(SensorNotFoundException::new);
    }

}
