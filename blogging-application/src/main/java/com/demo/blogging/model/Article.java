package com.demo.blogging.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.array.ListArrayType;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@TypeDef(
		name = "list-array",
		typeClass = ListArrayType.class
)
@ToString
@EqualsAndHashCode(callSuper=true)
public class Article extends BaseEntity{

	private static final long serialVersionUID = -7872951122959236349L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	        name = "UUID",
	        strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "ID", updatable = false, nullable = false)
	@ColumnDefault("random_uuid()")
	@Type(type = "uuid-char")
	private UUID id;

	@NotNull
	@Size(max = 128)
	private String title;

	@NotNull
	@Size(max = 128)
	private String description;

	@NotNull
	@Size(max = 128)
	private String body;

	@Type(type = "list-array")
	@Column(name = "tags",  columnDefinition = "ARRAY")
	@Builder.Default
	private List<String> tags = new ArrayList<>(10);
	
	private boolean favorited;
	
	private int favoritesCount;
	
	private String createdBy;
}
