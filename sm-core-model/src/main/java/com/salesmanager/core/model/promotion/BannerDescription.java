package com.salesmanager.core.model.promotion;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.description.Description;
import com.salesmanager.core.model.reference.language.Language;

import javax.persistence.*;


@Entity
@Table(name="BANNER_DESCRIPTION",uniqueConstraints={
		@UniqueConstraint(columnNames={
			"BANNER_ID",
			"LANGUAGE_ID"
		})
	}
)
@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "category_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class BannerDescription extends Description {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne(targetEntity = Banner.class)
	@JoinColumn(name = "BANNER_ID", nullable = false)
	private Banner banner;

	public BannerDescription() {

	}

	public Banner getBanner() {
		return banner;
	}

	public void setBanner(Banner banner) {
		this.banner = banner;
	}

	public BannerDescription(String name, Language language) {
		this.setName(name);
		this.setLanguage(language);
		super.setId(0L);
	}
}
