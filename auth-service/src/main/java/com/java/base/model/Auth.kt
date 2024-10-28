package com.java.base.model

import com.java.base.model.base.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
data class Auth (
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    val id: UUID,
    val name: String,
    val surname: String,
    val birthDate: Long,
    val password: String,
    override var createdBy: String,
    override var updatedBy: String,
    override var created: Long,
    override var updated: Long,
    override var deleted: Long?,
): BaseModel(createdBy,updatedBy,created,updated,deleted)