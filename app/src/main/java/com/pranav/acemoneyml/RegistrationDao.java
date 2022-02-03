package com.pranav.acemoneyml;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RegistrationDao {

    @Insert(onConflict = REPLACE)
    void insert(RegistrationEntity registrationEntity);

    @Query("select * from registration where email like :user and password like :loginPass")
    List<RegistrationEntity> getLoginData(String user, String loginPass);
}
