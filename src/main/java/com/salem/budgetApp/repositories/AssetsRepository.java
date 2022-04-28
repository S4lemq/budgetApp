package com.salem.budgetApp.repositories;

import com.salem.budgetApp.enums.AssetCategory;
import com.salem.budgetApp.repositories.entities.AssetEntity;
import com.salem.budgetApp.repositories.entities.ExpensesEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface AssetsRepository extends JpaRepository<AssetEntity, UUID> {

    List<AssetEntity> getAssetEntitiesByCategory(AssetCategory category);

    List<AssetEntity> getAssetEntitiesByUser(UserEntity userEntity);

    void deleteAllByUser(UserEntity userEntity);

    @Query("SELECT e FROM AssetEntity e" +
            " WHERE e.user = :user " +
            "AND e.incomeDate >= :fromDate " +
            "AND e.incomeDate <= :toDate" +
            " AND e.category in (:categories)")
    List<AssetEntity> findAllBetweenDate(UserEntity user,
                                         Instant fromDate,
                                         Instant toDate,
                                         List<AssetCategory> categories);


}
