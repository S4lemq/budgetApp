package com.salem.budgetApp.repositories;

import com.salem.budgetApp.repositories.entities.RoomsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomsRepository extends JpaRepository<RoomsEntity, UUID> {
}
