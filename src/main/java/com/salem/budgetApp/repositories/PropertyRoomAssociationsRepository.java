package com.salem.budgetApp.repositories;

import com.salem.budgetApp.repositories.entities.PropertyRoomAssociationsEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PropertyRoomAssociationsRepository extends CrudRepository<PropertyRoomAssociationsEntity, UUID> {

    Optional<List<PropertyRoomAssociationsEntity>> getAssociationsByPropertyId(UUID propertyID);

    @Modifying(clearAutomatically = true)
    void setRent(UUID propertyID, UUID roomId, Boolean isRent);
}
