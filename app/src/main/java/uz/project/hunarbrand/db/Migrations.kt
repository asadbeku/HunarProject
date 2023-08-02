package uz.project.hunarbrand.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS profile_info_new (" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "first_name TEXT NOT NULL DEFAULT 'undefined'," +
                    "last_name TEXT NOT NULL DEFAULT 'undefined'," +
                    "user_roles TEXT NOT NULL DEFAULT 'undefined'," +
                    "auth_type TEXT NOT NULL DEFAULT 'undefined'," +
                    "auth_status TEXT NOT NULL DEFAULT 'undefined'," +
                    "sex TEXT NOT NULL DEFAULT 'undefined'," +
                    "phone_number TEXT NOT NULL DEFAULT 'undefined'," +
                    "bio_uz TEXT DEFAULT 'undefined'," +
                    "bio_ru TEXT DEFAULT 'undefined'," +
                    "bio_en TEXT DEFAULT 'undefined'," +
                    "direction_uz TEXT NOT NULL DEFAULT 'undefined'," +
                    "direction_ru TEXT DEFAULT 'undefined'," +
                    "brand_name TEXT DEFAULT 'undefined'," +
                    "address TEXT DEFAULT 'undefined'," +
                    "avatar TEXT DEFAULT 'undefined'," +
                    "logo TEXT DEFAULT 'undefined')")

        // Copy the data from the old table to the new one
        database.execSQL("INSERT INTO profile_info_new (id, first_name, last_name, user_roles, auth_type, auth_status, sex, phone_number, bio_uz, bio_ru, bio_en, direction_uz, direction_ru, brand_name, address, avatar, logo) " +
                "SELECT id, first_name, last_name, user_roles, auth_type, auth_status, sex, phone_number, bio_uz, bio_ru, bio_en, direction_uz, direction_ru, brand_name, address, avatar, logo FROM profile_info")

        // Drop the old table
        database.execSQL("DROP TABLE profile_info")

        // Rename the new table to the original name
        database.execSQL("ALTER TABLE profile_info_new RENAME TO profile_info")

    }
}