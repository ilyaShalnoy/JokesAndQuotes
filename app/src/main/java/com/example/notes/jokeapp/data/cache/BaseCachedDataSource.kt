package com.example.notes.jokeapp.data.cache

import com.example.notes.jokeapp.*
import com.example.notes.jokeapp.core.data.CommonDataModelMapper
import com.example.notes.jokeapp.core.data.cache.*
import com.example.notes.jokeapp.core.domain.NoCachedException
import com.example.notes.jokeapp.data.*
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseCachedDataSource<T : RealmObject, E>(
    private val realmProvider: RealmProvider,
    private val mapper: CommonDataModelMapper<T, E>,
    private val realmToCommonDataMapper: RealmToCommonDataMapper<T, E>
) : CacheDataSource<E> {

    protected abstract val dbClass: Class<T>

    protected abstract fun findRealmObject(realm: Realm, id: E): T?

    override suspend fun addOrRemove(id: E, model: CommonDataModel<E>): CommonDataModel<E> =
        withContext(Dispatchers.IO) {
            realmProvider.provide().use {
                val itemRealm = findRealmObject(it, id)
                return@withContext if (itemRealm == null) {
                    val newData = model.map(mapper)
                    it.executeTransaction { transaction ->
                        transaction.insert(newData)
                    }
                    model.changeCached(true)
                } else {
                    it.executeTransaction {
                        itemRealm.deleteFromRealm()
                    }
                    model.changeCached(false)
                }
            }
        }


    override suspend fun getData() = getRealmData {
        realmToCommonDataMapper.map(it.random())
    }


    override suspend fun getDataList(): List<CommonDataModel<E>> = getRealmData { results ->
        results.map {
            realmToCommonDataMapper.map(it)
        }
    }


    private fun <R> getRealmData(block: (list: RealmResults<T>) -> R): R {
        realmProvider.provide().use {
            val lists = it.where(dbClass).findAll()
            if (lists.isEmpty()) {
                throw NoCachedException()
            } else
                return block.invoke(lists)
        }
    }

    override suspend fun remove(id: E) = withContext(Dispatchers.IO) {
        realmProvider.provide().use { realm ->
            realm.executeTransaction{
                findRealmObject(realm, id)?.deleteFromRealm()
            }
        }
    }
}

