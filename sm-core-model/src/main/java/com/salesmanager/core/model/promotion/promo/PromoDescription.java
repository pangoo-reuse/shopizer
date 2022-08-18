package com.salesmanager.core.model.promotion.promo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.description.Description;
import com.salesmanager.core.model.reference.language.Language;

import javax.persistence.*;


@Entity
@Table(name="PROMO_DESCRIPTION",uniqueConstraints={
		@UniqueConstraint(columnNames={
			"PROMO_ID",
			"LANGUAGE_ID"
		})
	}
)
@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "promo_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class PromoDescription extends Description {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne(targetEntity = Promo.class)
	@JoinColumn(name = "PROMO_ID", nullable = false)
	private Promo promo;

	public PromoDescription() {

	}


	public PromoDescription(String name, Language language) {
		this.setName(name);
		this.setLanguage(language);
		super.setId(0L);
	}

	public Promo getPromo() {
		return promo;
	}

	public void setPromo(Promo promo) {
		this.promo = promo;
	}
}
