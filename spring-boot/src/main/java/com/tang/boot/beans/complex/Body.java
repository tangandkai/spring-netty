package com.tang.boot.beans.complex;

import lombok.Data;

import java.util.List;

@Data
public class Body {

    private List<String> indicators;
    private Dimensions dimensions;
    private Options options;

    public List<String> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<String> indicators) {
        this.indicators = indicators;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Body{" +
                "indicators=" + indicators +
                ", dimensions=" + dimensions +
                ", options=" + options +
                '}';
    }
}
