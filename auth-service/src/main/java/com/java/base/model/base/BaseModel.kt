package com.java.base.model.base
/*
interface BaseModel {
    var createdBy: String
    var updatedBy: String
    var created: Long
    var updated: Long
    var deleted: Long?
}
*/

abstract class BaseModel(
    //Default time alma anatasyonuna bakılıp eklemesi yapılacak
    open var createdBy: String,
    open var updatedBy: String,
    open var created: Long,
    open var updated: Long,
    open var deleted: Long?,
)