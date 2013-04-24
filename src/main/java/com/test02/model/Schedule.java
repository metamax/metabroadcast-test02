package com.test02.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Schedule {
    @XmlElement(name = "programme")
    private List<Programme> programmes;

    public Schedule() { }

    public Schedule(List<Programme> programmes) {
        this.programmes = programmes;
    }

    public List<Programme> getElements() {
        return programmes;
    }

}
