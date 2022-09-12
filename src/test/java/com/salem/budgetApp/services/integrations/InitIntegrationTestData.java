package com.salem.budgetApp.services.integrations;

import com.salem.budgetApp.repositories.entities.builders.ExpensesEntityBuilder;
import com.salem.budgetApp.repositories.entities.builders.PropertyEntityBuilder;
import com.salem.budgetApp.enums.AssetCategory;
import com.salem.budgetApp.enums.ExpensesCategory;
import com.salem.budgetApp.enums.RoomsType;
import com.salem.budgetApp.mappers.ExpensesMapper;
import com.salem.budgetApp.mappers.PropertyMapper;
import com.salem.budgetApp.repositories.*;
import com.salem.budgetApp.repositories.entities.*;
import com.salem.budgetApp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
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
    @Autowired
    protected RoomsService roomsService;
    @Autowired
    protected RoomsRepository roomsRepository;

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
        var assetEntity = AssetEntity.builder()
                .incomeDate(date)
                .user(userEntity)
                .amount(BigDecimal.ONE)
                .category(category)
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
        AssetEntity entity1 = AssetEntity.builder()
                .amount(new BigDecimal(10))
                .incomeDate(Instant.now())
                .category(AssetCategory.BONUS)
                .user(userEntity)
                .build();
        AssetEntity entity2 = AssetEntity.builder()
                .amount(new BigDecimal(50))
                .incomeDate(Instant.now())
                .category(AssetCategory.OTHER)
                .user(userEntity)
                .build();
        AssetEntity entity3 = AssetEntity.builder()
                .amount(new BigDecimal(1500))
                .incomeDate(Instant.now())
                .category(AssetCategory.RENT)
                .user(userEntity)
                .build();

        assetsRepository.saveAll(asList(entity1, entity2, entity3));
    }



    protected void initDatabaseBySecondMockUserAndHisAssets() {
        var userEntity = initDatabaseBySecondUser();
        AssetEntity entity1 = AssetEntity.builder()
                .amount(new BigDecimal(10))
                .incomeDate(Instant.now())
                .category(AssetCategory.BONUS)
                .user(userEntity)
                .build();
        AssetEntity entity2 = AssetEntity.builder()
                .amount(new BigDecimal(50))
                .incomeDate(Instant.now())
                .category(AssetCategory.OTHER)
                .user(userEntity)
                .build();
        AssetEntity entity3 = AssetEntity.builder()
                .amount(new BigDecimal(1500))
                .incomeDate(Instant.now())
                .category(AssetCategory.RENT)
                .user(userEntity)
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
        initDatabaseByProperty(user, null);
    }

    protected void initDatabaseByProperty(UserEntity user, UUID... roomsId){
        var postCode = "70-649";
        var city = "Poznan";
        var street = "Hetmanska";
        var house = "12A";
        var single = false;

        var roomsIdForEntity = Objects.isNull(roomsId)
                ? null
                : Arrays.stream(roomsId).toList();
        var roomsEntity = Objects.isNull(roomsIdForEntity)
                ? null
                : roomsRepository.findAllById(roomsIdForEntity);

        PropertyEntity property = PropertyEntity.builder()
                .postCode(postCode)
                .city(city)
                .street(street)
                .house(house)
                .single(single)
                .user(user)
                .sold(false)
                .rooms(roomsEntity)
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
//                .withRooms(4)
                .withUser(user)
                .build();

        PropertyEntity property2 = new PropertyEntityBuilder()
                .withPostCode("16-320")
                .withCity("Warsaw")
                .withStreet("Gajowa")
                .withHouse("213")
                .withSingle(false)
//                .withRooms(2)
                .withUser(user)
                .build();

        PropertyEntity property3 = new PropertyEntityBuilder()
                .withPostCode("60-813")
                .withCity("Cracow")
                .withStreet("Bukowska")
                .withHouse("235/13")
                .withSingle(true)
//                .withRooms(5)
                .withUser(user)
                .build();

        propertyRepository.saveAll(asList(property1, property2, property3));
    }

    protected UUID initDatabaseByRoom(RoomsType type,
                                      BigDecimal cost,
                                      UserEntity user){
        var entity = RoomsEntity.builder()
                .user(user)
                .cost(cost)
                .type(type)
                .build();

        var savedEntity = roomsRepository.save(entity);
        return savedEntity.getId();
    }




}
