package project3.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import project3.models.Sensor;

public class MeasurementDTO {
    @NotNull(message = "Значение не должно быть пустым")
    @Min(value = -100, message = "Температура не может быть меньше 100 градусов")
    @Max(value = 100, message = "Температура не может быть больше 100 градусов")
    @Column(name = "value")
    private double value;

    @NotNull(message = "Значение не должно быть пустым")
    @Column(name = "raining")
    private boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
