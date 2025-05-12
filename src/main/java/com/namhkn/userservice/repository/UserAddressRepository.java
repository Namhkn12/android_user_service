package com.namhkn.userservice.repository;

import com.namhkn.userservice.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {
}
