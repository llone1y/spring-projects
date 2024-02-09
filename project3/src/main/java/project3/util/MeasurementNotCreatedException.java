package project3.util;

import project3.models.Measurement;

public class MeasurementNotCreatedException extends RuntimeException{
    public MeasurementNotCreatedException(String msg) {
        super(msg);
    }
}
