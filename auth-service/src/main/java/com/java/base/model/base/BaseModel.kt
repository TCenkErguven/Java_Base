package com.java.base.model.base

import java.time.LocalDateTime

interface BaseModel {
    var createdBy: String
    var updatedBy: String
    var created: Long
    var updated: Long
    var deleted: Long?
}