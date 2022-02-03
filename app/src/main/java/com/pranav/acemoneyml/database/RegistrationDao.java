package com.pranav.acemoneyml.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.pranav.acemoneyml.model.RegistrationEntity;

import java.util.List;

@Dao
public interface RegistrationDao {

    @Insert(onConflict = REPLACE)
    void insert(RegistrationEntity registrationEntity);

    @Query("select count(*) from registration where email like :user and password like :loginPass")
    int getLoginData(String user, String loginPass);
}
