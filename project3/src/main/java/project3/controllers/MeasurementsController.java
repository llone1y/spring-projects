package project3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import project3.dto.MeasurementDTO;
import project3.models.Measurement;
import project3.services.MeasurementService;
import project3.services.SensorService;
import project3.util.ErrorResponse;
import project3.util.MeasurementNotCreatedException;
import project3.util.SensorNotCreatedException;
import project3.util.SensorNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final ModelMapper modelMapper;
    private final SensorService sensorService;
    private final MeasurementService measurementService;

    public MeasurementsController(ModelMapper modelMapper, SensorService sensorService, MeasurementService measurementService) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
        this.measurementService = measurementService;
    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements() {
        return measurementService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public int countRainyDays() {
        return measurementService.countRainyDays();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new MeasurementNotCreatedException(errorMessage.toString());
        }
        Measurement measurement = convertToMeasurement(measurementDTO);
        measurement.setSensor(sensorService.findSensorByNameOrThrowException(measurementDTO.getSensor().getName()));
        measurementService.save(measurement);
        
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO , Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Сенсор не зарегестрирован",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MeasurementNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
