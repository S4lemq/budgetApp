package com.salem.budgetApp.repositories;

import com.salem.budgetApp.repositories.entities.RoomsEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomsRepository extends JpaRepository<RoomsEntity, UUID> {
    Optional<List<RoomsEntity>> findAllByUser(UserEntity user);
}
