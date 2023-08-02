package uz.project.hunarbrand.db

import android.content.Context
import androidx.room.Room

object Database {

    lateinit var instance: HunarBrandDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            HunarBrandDatabase::class.java,
            HunarBrandDatabase.DB_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }

}