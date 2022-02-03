package com.pranav.acemoneyml;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface RegistrationDao {

    @Insert(onConflict = REPLACE)
    void insert(RegistrationEntity registrationEntity);
}
