package com.soa.ejbvehicle.util.specification;

import com.soa.ejbvehicle.data.entities.Vehicle;
import com.soa.ejbvehicle.dto.FilterCondition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class VehicleSpecificationBuilder {

    public static javax.persistence.criteria.Predicate buildSpecification(
            FilterCondition filter,
            Root<Vehicle> root,
            CriteriaQuery<?> query,
            CriteriaBuilder cb) {
        
        if (filter == null || filter.getField() == null || filter.getField().isEmpty() ||
                filter.getOperator() == null || filter.getOperator().isEmpty() || 
                filter.getValue() == null || filter.getValue().isEmpty()) {
            return cb.conjunction();
        }

        String field = filter.getField();

        if (field.equals("id") || field.equals("enginePower") || field.equals("numberOfWheels")) {
            return buildLongFilter(filter, root, cb);
        } else if (field.equals("name")) {
            return buildStringFilter(filter, root, cb);
        } else if (field.equals("type") || field.equals("fuelType")) {
            return buildEnumFilter(filter, root, cb);
        } else if (field.equals("creationDate")) {
            return buildDateFilter(filter, root, cb);
        } else if (field.equals("coordinates.x") || field.equals("coordinates.y")) {
            return buildDoubleFilter(filter, root, cb);
        }

        return cb.conjunction();
    }

    private static javax.persistence.criteria.Predicate buildStringFilter(
            FilterCondition filter, Root<Vehicle> root, CriteriaBuilder cb) {
        String value = filter.getValue();
        if (value == null || value.isEmpty()) return cb.conjunction();

        String field = filter.getField();
        String operator = filter.getOperator().toLowerCase();

        switch (operator) {
            case "eq":
                return cb.equal(cb.upper(root.get(field)), value.toUpperCase());
            case "ne":
                return cb.notEqual(cb.upper(root.get(field)), value.toUpperCase());
            case "like":
                return cb.like(cb.upper(root.get(field)), "%" + value.toUpperCase() + "%");
            default:
                return cb.equal(cb.upper(root.get(field)), value.toUpperCase());
        }
    }

    private static javax.persistence.criteria.Predicate buildLongFilter(
            FilterCondition filter, Root<Vehicle> root, CriteriaBuilder cb) {
        String value = filter.getValue();
        if (value == null || value.isEmpty()) return cb.conjunction();

        try {
            Long longValue = Long.parseLong(value);
            String field = filter.getField();
            String operator = filter.getOperator().toLowerCase();

            switch (operator) {
                case "eq":
                    return cb.equal(root.get(field), longValue);
                case "ne":
                    return cb.notEqual(root.get(field), longValue);
                case "gt":
                    return cb.greaterThan(root.get(field), longValue);
                case "gte":
                    return cb.greaterThanOrEqualTo(root.get(field), longValue);
                case "lt":
                    return cb.lessThan(root.get(field), longValue);
                case "lte":
                    return cb.lessThanOrEqualTo(root.get(field), longValue);
                default:
                    return cb.equal(root.get(field), longValue);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid number value: " + value);
            return cb.conjunction();
        }
    }

    private static javax.persistence.criteria.Predicate buildDateFilter(
            FilterCondition filter, Root<Vehicle> root, CriteriaBuilder cb) {
        String value = filter.getValue();
        if (value == null || value.isEmpty()) return cb.conjunction();

        try {
            java.time.ZonedDateTime dateValue = java.time.ZonedDateTime.parse(value,
                    java.time.format.DateTimeFormatter.ISO_DATE_TIME);
            String field = filter.getField();
            String operator = filter.getOperator().toLowerCase();

            switch (operator) {
                case "eq":
                    return cb.equal(root.get(field), dateValue);
                case "ne":
                    return cb.notEqual(root.get(field), dateValue);
                case "gt":
                    return cb.greaterThan(root.get(field), dateValue);
                case "gte":
                    return cb.greaterThanOrEqualTo(root.get(field), dateValue);
                case "lt":
                    return cb.lessThan(root.get(field), dateValue);
                case "lte":
                    return cb.lessThanOrEqualTo(root.get(field), dateValue);
                default:
                    return cb.equal(root.get(field), dateValue);
            }
        } catch (Exception e) {
            System.err.println("Invalid date value: " + value);
            return cb.conjunction();
        }
    }

    private static javax.persistence.criteria.Predicate buildDoubleFilter(
            FilterCondition filter, Root<Vehicle> root, CriteriaBuilder cb) {
        String value = filter.getValue();
        if (value == null || value.isEmpty()) return cb.conjunction();

        try {
            Double doubleValue = Double.parseDouble(value);
            String[] paths = filter.getField().split("\\.");
            
            if (paths.length == 2) {
                String operator = filter.getOperator().toLowerCase();
                switch (operator) {
                    case "eq":
                        return cb.equal(root.get(paths[0]).get(paths[1]), doubleValue);
                    case "ne":
                        return cb.notEqual(root.get(paths[0]).get(paths[1]), doubleValue);
                    case "gt":
                        return cb.greaterThan(root.get(paths[0]).get(paths[1]), doubleValue);
                    case "gte":
                        return cb.greaterThanOrEqualTo(root.get(paths[0]).get(paths[1]), doubleValue);
                    case "lt":
                        return cb.lessThan(root.get(paths[0]).get(paths[1]), doubleValue);
                    case "lte":
                        return cb.lessThanOrEqualTo(root.get(paths[0]).get(paths[1]), doubleValue);
                    default:
                        return cb.equal(root.get(paths[0]).get(paths[1]), doubleValue);
                }
            } else {
                return cb.conjunction();
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid double value: " + value);
            return cb.conjunction();
        }
    }

    private static javax.persistence.criteria.Predicate buildEnumFilter(
            FilterCondition filter, Root<Vehicle> root, CriteriaBuilder cb) {
        String value = filter.getValue();
        if (value == null || value.isEmpty()) return cb.conjunction();

        String field = filter.getField();
        String operator = filter.getOperator().toLowerCase();

        try {
            Object enumValue;
            if (field.equals("type")) {
                enumValue = com.soa.ejbvehicle.data.enums.VehicleType.valueOf(value.toUpperCase());
            } else if (field.equals("fuelType")) {
                enumValue = com.soa.ejbvehicle.data.enums.FuelType.valueOf(value.toUpperCase());
            } else {
                return cb.conjunction();
            }

            switch (operator) {
                case "eq":
                    return cb.equal(root.get(field), enumValue);
                case "ne":
                    return cb.notEqual(root.get(field), enumValue);
                default:
                    return cb.equal(root.get(field), enumValue);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid enum value: " + value);
            return cb.conjunction();
        }
    }
}

