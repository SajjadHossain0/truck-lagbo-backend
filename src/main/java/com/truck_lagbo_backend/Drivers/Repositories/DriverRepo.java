package com.truck_lagbo_backend.Drivers.Repositories;

import com.truck_lagbo_backend.Drivers.Entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {
}
