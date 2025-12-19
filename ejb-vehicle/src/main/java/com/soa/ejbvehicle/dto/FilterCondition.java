package com.soa.ejbvehicle.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "filterCondition")
@XmlAccessorType(XmlAccessType.FIELD)
public class FilterCondition {
    @XmlElement
    private String field;

    @XmlElement
    private String operator;

    @XmlElement
    private String value;

    @XmlElement
    private List<String> valueArray;

    public FilterCondition() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getValueArray() {
        return valueArray;
    }

    public void setValueArray(List<String> valueArray) {
        this.valueArray = valueArray;
    }
}

