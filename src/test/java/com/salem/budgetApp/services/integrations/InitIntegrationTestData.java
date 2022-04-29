package com.salem.budgetApp.services.integrations;

import com.salem.budgetApp.builders.AssetEntityBuilder;
import com.salem.budgetApp.builders.ExpensesEntityBuilder;
import com.salem.budgetApp.builders.PropertyDtoBuilder;
import com.salem.budgetApp.builders.PropertyEntityBuilder;
import com.salem.budgetApp.enums.AssetCategory;
import com.salem.budgetApp.enums.ExpensesCategory;
import com.salem.budgetApp.mappers.ExpensesMapper;
import com.salem.budgetApp.mappers.PropertyMapper;
import com.salem.budgetApp.repositories.AssetsRepository;
import com.salem.budgetApp.repositories.ExpensesRepository;
import com.salem.budgetApp.repositories.PropertyRepository;
import com.salem.budgetApp.repositories.UserRepository;
import com.salem.budgetApp.repositories.entities.AssetEntity;
import com.salem.budgetApp.repositories.entities.ExpensesEntity;
import com.salem.budgetApp.repositories.entities.PropertyEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.*;
import com.salem.budgetApp.services.dtos.PropertyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static java.util.Arrays.asList;

@SpringBootTest
@Transactional
@WithMockUser(username = "userNamePrime", password = "userPasswordPrime")
public abstract class InitIntegrationTestData {

    @Autowired
    protected ExpensesService expensesService;
    @Autowired
    protected ExpensesRepository expensesRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ExpensesMapper expensesMapper;
    @Autowired
    protected AssetsRepository assetsRepository;
    @Autowired
    protected AssetsService assetsService;
    @Autowired
    protected UserDetailsServiceImpl userDetailsService;
    @Autowired
    protected JWTService jwtService;
    @Autowired
    protected AuthenticationManager authenticationManager;
    @Autowired
    protected AssetsRepository assetRepository;
    @Autowired
    protected PropertyService propertyService;
    @Autowired
    protected PropertyRepository propertyRepository;
    @Autowired
    protected PropertyMapper propertyMapper;

    protected static final String USER_NAME_PRIME = "userNamePrime";
    protected static final String USER_PASSWORD_PRIME = "userPasswordPrime";
    protected static final String USER_NAME_SECOND = "userNameSecond";
    protected static final String USER_PASSWORD_SECOND = "userPasswordSecond";

    protected void initDatabaseByAssetsForUser(UserEntity userEntity, String date){
        var suffixDate = "T00:00:00.001Z";
        initDatabaseByAssetsForUser(userEntity, Instant.parse(date + suffixDate), AssetCategory.BONUS);
    }

    protected void initDatabaseByAssetsForUser(UserEntity userEntity, String date, AssetCategory category){
        var suffixDate = "T00:00:00.001Z";
        initDatabaseByAssetsForUser(userEntity, Instant.parse(date + suffixDate), category);
    }

    protected void initDatabaseByAssetsForUser(UserEntity userEntity){
        initDatabaseByAssetsForUser(userEntity, Instant.now(), AssetCategory.BONUS);
    }

    private void initDatabaseByAssetsForUser(UserEntity userEntity, Instant date, AssetCategory category) {
        var assetEntity = new AssetEntityBuilder()
                .withInsuranceDate(date)
                .withUser(userEntity)
                .withAmount(BigDecimal.ONE)
                .withCategory(category)
                .build();

        assetRepository.save(assetEntity);
    }

    protected UserEntity initDatabaseByPrimeUser(){
        UserEntity entity = new UserEntity();
        entity.setUsername(USER_NAME_PRIME);
        entity.setPassword(USER_PASSWORD_PRIME);
        return userRepository.save(entity);
    }

    protected UserEntity initDatabaseBySecondUser(){
        var user = new UserEntity();
        user.setUsername(USER_NAME_SECOND);
        user.setPassword(USER_PASSWORD_SECOND);

        return userRepository.save(user);
    }


    protected void initDatabaseByDefaultMockUserAndHisAssets() {
        var userEntity = initDatabaseByPrimeUser();
        AssetEntity entity1 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(10))
                .withInsuranceDate(Instant.now())
                .withCategory(AssetCategory.BONUS)
                .withUser(userEntity)
                .build();
        AssetEntity entity2 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(50))
                .withInsuranceDate(Instant.now())
                .withCategory(AssetCategory.OTHER)
                .withUser(userEntity)
                .build();
        AssetEntity entity3 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(1500))
                .withInsuranceDate(Instant.now())
                .withCategory(AssetCategory.RENT)
                .withUser(userEntity)
                .build();

        assetsRepository.saveAll(asList(entity1, entity2, entity3));
    }



    protected void initDatabaseBySecondMockUserAndHisAssets() {
        var userEntity = initDatabaseBySecondUser();
        AssetEntity entity1 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(10))
                .withInsuranceDate(Instant.now())
                .withCategory(AssetCategory.BONUS)
                .withUser(userEntity)
                .build();
        AssetEntity entity2 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(50))
                .withInsuranceDate(Instant.now())
                .withCategory(AssetCategory.OTHER)
                .withUser(userEntity)
                .build();
        AssetEntity entity3 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(1500))
                .withInsuranceDate(Instant.now())
                .withCategory(AssetCategory.RENT)
                .withUser(userEntity)
                .build();

        assetsRepository.saveAll(asList(entity1, entity2, entity3));
    }



    protected void initDatabaseByDefaultMockUserAndHisExpenses() {
        var user = initDatabaseByPrimeUser();

        ExpensesEntity expense1 = new ExpensesEntityBuilder()
                .withAmount(BigDecimal.ONE)
                .withCategory(ExpensesCategory.FOR_LIFE)
                .withPurchaseDate(Instant.now())
                .withUser(user)
                .build();

        ExpensesEntity expense2 = new ExpensesEntityBuilder()
                .withAmount(new BigDecimal(20))
                .withCategory(ExpensesCategory.EDUCATION)
                .withPurchaseDate(Instant.now())
                .withUser(user)
                .build();

        ExpensesEntity expense3 = new ExpensesEntityBuilder()
                .withAmount(new BigDecimal(50))
                .withCategory(ExpensesCategory.FUN)
                .withPurchaseDate(Instant.now())
                .withUser(user)
                .build();

        expensesRepository.saveAll(asList(expense1, expense2, expense3));
    }

    protected void initDatabaseBySecondMockUserAndHisExpenses() {
        var user = initDatabaseBySecondUser();

        ExpensesEntity expense1 = new ExpensesEntityBuilder()
                .withAmount(BigDecimal.ONE)
                .withCategory(ExpensesCategory.FOR_LIFE)
                .withPurchaseDate(Instant.now())
                .withUser(user)
                .build();

        ExpensesEntity expense2 = new ExpensesEntityBuilder()
                .withAmount(new BigDecimal(20))
                .withCategory(ExpensesCategory.EDUCATION)
                .withPurchaseDate(Instant.now())
                .withUser(user)
                .build();

        ExpensesEntity expense3 = new ExpensesEntityBuilder()
                .withAmount(new BigDecimal(50))
                .withCategory(ExpensesCategory.FUN)
                .withPurchaseDate(Instant.now())
                .withUser(user)
                .build();

        expensesRepository.saveAll(asList(expense1, expense2, expense3));
    }

    protected UUID initDatabaseByExpenses(UserEntity user, String date){
        var dateSuffix = "T00:00:00.001Z";

        ExpensesEntity expensesEntity = new ExpensesEntityBuilder()
                .withUser(user)
                .withAmount(BigDecimal.ONE)
                .withPurchaseDate(Instant.parse(date + dateSuffix))
                .build();

        var entity = expensesRepository.save(expensesEntity);
        return entity.getId();
    }

    protected void initDatabaseByProperty(UserEntity user){
        var postCode = "70-649";
        var city = "Poznan";
        var street = "Hetmanska";
        var house = "12A";
        var single = false;
        var rooms = 3;

        PropertyEntity property = new PropertyEntityBuilder()
                .withPostCode(postCode)
                .withCity(city)
                .withStreet(street)
                .withHouse(house)
                .withSingle(single)
                .withRooms(rooms)
                .withUser(user)
                .build();

        propertyRepository.save(property);

    }

    protected void initDatabaseByDefaultMockUserAndHisProperty() {
        var user = initDatabaseByPrimeUser();

        PropertyEntity property1 = new PropertyEntityBuilder()
                .withPostCode("70-649")
                .withCity("Poznan")
                .withStreet("Hetmanska")
                .withHouse("12A")
                .withSingle(false)
                .withRooms(4)
                .withUser(user)
                .build();

        PropertyEntity property2 = new PropertyEntityBuilder()
                .withPostCode("16-320")
                .withCity("Warsaw")
                .withStreet("Gajowa")
                .withHouse("213")
                .withSingle(false)
                .withRooms(2)
                .withUser(user)
                .build();

        PropertyEntity property3 = new PropertyEntityBuilder()
                .withPostCode("60-813")
                .withCity("Cracow")
                .withStreet("Bukowska")
                .withHouse("235/13")
                .withSingle(true)
                .withRooms(5)
                .withUser(user)
                .build();

        propertyRepository.saveAll(asList(property1, property2, property3));
    }




}
