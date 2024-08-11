package joonas.niemi.jnotes.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import joonas.niemi.jnotes.core.database.model.TextNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TextNotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(textNoteEntity: TextNoteEntity)

    @Update
    suspend fun update(textNoteEntity: TextNoteEntity)

    @Delete
    suspend fun delete(textNoteEntity: TextNoteEntity)

    @Query("SELECT * FROM text_notes WHERE id = :id")
    fun getById(id: String): Flow<TextNoteEntity?>

    @Query("SELECT * FROM text_notes ORDER BY createdAt DESC")
    fun getAll(): Flow<Map<@MapColumn(columnName = "id") String, TextNoteEntity>>
}