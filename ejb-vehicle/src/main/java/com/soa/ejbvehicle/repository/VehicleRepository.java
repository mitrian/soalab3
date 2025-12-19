package com.soa.ejbvehicle.repository;

import com.soa.ejbvehicle.data.entities.Vehicle;
import com.soa.ejbvehicle.dto.FilterCondition;
import com.soa.ejbvehicle.dto.PaginationOptions;
import com.soa.ejbvehicle.dto.SortOption;
import com.soa.ejbvehicle.util.specification.VehicleSpecificationBuilder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class VehicleRepository {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    public Vehicle save(Vehicle vehicle) {
        try {
            System.out.println("=== VehicleRepository.save ===");
            System.out.println("Vehicle: " + vehicle);
            System.out.println("EntityManager: " + entityManager);
            
            if (vehicle.getId() == null) {
                System.out.println("Persisting new vehicle");
                entityManager.persist(vehicle);
                entityManager.flush(); // Принудительно синхронизируем с БД, чтобы получить ID
                System.out.println("Vehicle persisted with ID: " + vehicle.getId());
                return vehicle;
            } else {
                System.out.println("Merging existing vehicle with ID: " + vehicle.getId());
                Vehicle merged = entityManager.merge(vehicle);
                System.out.println("Vehicle merged");
                return merged;
            }
        } catch (Exception e) {
            System.err.println("=== ERROR in VehicleRepository.save ===");
            System.err.println("Exception type: " + e.getClass().getName());
            System.err.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            System.err.println("======================================");
            throw e;
        }
    }

    public Vehicle findById(Long id) {
        return entityManager.find(Vehicle.class, id);
    }

    public boolean existsById(Long id) {
        return findById(id) != null;
    }

    public void deleteById(Long id) {
        Vehicle vehicle = findById(id);
        if (vehicle != null) {
            entityManager.remove(vehicle);
        }
    }

    public Integer deleteByEnginePower(Long enginePower) {
        Query query = entityManager.createQuery("DELETE FROM Vehicle v WHERE v.enginePower = :enginePower");
        query.setParameter("enginePower", enginePower);
        return query.executeUpdate();
    }

    public Long countByEnginePowerLessThan(Long enginePower) {
        Query query = entityManager.createQuery("SELECT COUNT(v) FROM Vehicle v WHERE v.enginePower < :enginePower");
        query.setParameter("enginePower", enginePower);
        return ((Number) query.getSingleResult()).longValue();
    }

    public List<Vehicle> findByNameStartingWith(String namePrefix) {
        TypedQuery<Vehicle> query = entityManager.createQuery(
                "SELECT v FROM Vehicle v WHERE v.name LIKE :prefix", Vehicle.class);
        query.setParameter("prefix", namePrefix + "%");
        return query.getResultList();
    }

    public List<Vehicle> findAllWithFilters(
            List<FilterCondition> filters,
            List<SortOption> sortOptions,
            PaginationOptions pagination) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Vehicle> query = cb.createQuery(Vehicle.class);
        Root<Vehicle> root = query.from(Vehicle.class);

        // Build predicates from filters
        List<Predicate> predicates = filters != null ? filters.stream()
                .map(filter -> VehicleSpecificationBuilder.buildSpecification(filter, root, query, cb))
                .collect(Collectors.toList()) : Collections.emptyList();

        Predicate finalPredicate = predicates.isEmpty() 
                ? cb.conjunction() 
                : predicates.stream().reduce(cb.conjunction(), cb::and);
        
        query.where(finalPredicate);

        // Build order by
        if (sortOptions != null && !sortOptions.isEmpty()) {
            List<Order> orders = sortOptions.stream()
                    .filter(opt -> isValidSortField(opt.getField()))
                    .map(opt -> {
                        String field = opt.getField();
                        if (field.contains(".")) {
                            String[] paths = field.split("\\.");
                            if (paths.length == 2) {
                                if ("desc".equalsIgnoreCase(opt.getDirection())) {
                                    return cb.desc(root.get(paths[0]).get(paths[1]));
                                } else {
                                    return cb.asc(root.get(paths[0]).get(paths[1]));
                                }
                            }
                        }
                        if ("desc".equalsIgnoreCase(opt.getDirection())) {
                            return cb.desc(root.get(field));
                        } else {
                            return cb.asc(root.get(field));
                        }
                    })
                    .collect(Collectors.toList());
            
            if (!orders.isEmpty()) {
                query.orderBy(orders);
            }
        }

        TypedQuery<Vehicle> typedQuery = entityManager.createQuery(query);
        
        // Apply pagination
        if (pagination != null) {
            typedQuery.setFirstResult(pagination.getPage() * pagination.getSize());
            typedQuery.setMaxResults(pagination.getSize());
        }

        return typedQuery.getResultList();
    }

    public Long countWithFilters(List<FilterCondition> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Vehicle> root = query.from(Vehicle.class);
        query.select(cb.count(root));

        if (filters != null && !filters.isEmpty()) {
            List<Predicate> predicates = filters.stream()
                    .map(filter -> VehicleSpecificationBuilder.buildSpecification(filter, root, query, cb))
                    .collect(Collectors.toList());
            query.where(predicates.stream().reduce(cb.conjunction(), cb::and));
        }

        return entityManager.createQuery(query).getSingleResult();
    }

    private boolean isValidSortField(String field) {
        List<String> validFields = new ArrayList<>();
        validFields.add("id");
        validFields.add("name");
        validFields.add("enginePower");
        validFields.add("type");
        validFields.add("fuelType");
        validFields.add("creationDate");
        validFields.add("coordinates.x");
        validFields.add("coordinates.y");
        return validFields.contains(field);
    }
}

