package com.demo.blogging.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEntity implements Serializable {

	@Column(name = "created_at", updatable =false) 
    @CreationTimestamp
    private Timestamp createdAt;
    
	@Column(name = "updated_at") 
    @UpdateTimestamp
    private Timestamp updatedAt;
    
}
