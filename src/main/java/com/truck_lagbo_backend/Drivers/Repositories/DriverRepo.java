package com.truck_lagbo_backend.Drivers.Repositories;

import com.truck_lagbo_backend.Drivers.Entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {

    @Query("SELECT d FROM Driver d WHERE " +
            "(:truckType IS NULL OR :truckType MEMBER OF d.vehicaltype) AND " +
            "(:capacity IS NULL OR d.weight = :capacity) AND " +
            "(:location IS NULL OR :location MEMBER OF d.servicearea) AND " +
            "(:price IS NULL OR d.price = :price)")
    List<Driver> searchDrivers(@Param("truckType") String truckType,
                               @Param("capacity") String capacity,
                               @Param("location") String location,
                               @Param("price") String price);

}
