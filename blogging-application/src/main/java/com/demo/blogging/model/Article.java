package com.demo.blogging.model;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@TypeDef(
		name = "list-array",
		typeClass = ListArrayType.class
)
@ToString
@EqualsAndHashCode
public class Article extends BaseEntity{
	@Id
	@GeneratedValue
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
}
