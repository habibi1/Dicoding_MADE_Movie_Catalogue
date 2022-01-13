package com.android.habibi.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences(private val context: Context) {

    private val listKey = intPreferencesKey("type_list_setting")

    fun getTypeListSetting(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[listKey] ?: 0
        }
    }

    suspend fun saveTypeListSetting(typeList: Int) {
        context.dataStore.edit { preferences ->
            preferences[listKey] = typeList
        }
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
}