package com.salesmanager.core.model.promotion.falshSale;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.description.Description;
import com.salesmanager.core.model.reference.language.Language;

import javax.persistence.*;


@Entity
@Table(name="FLASH_SALE_DESCRIPTION",uniqueConstraints={
		@UniqueConstraint(columnNames={
			"FLASH_SALE_ID",
			"LANGUAGE_ID"
		})
	}
)
@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "category_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class FlashSaleDescription extends Description {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne(targetEntity = FlashSale.class)
	@JoinColumn(name = "FLASH_SALE_ID", nullable = false)
	private FlashSale flashSale;

	public FlashSaleDescription(String name, Language language) {
		this.setName(name);
		this.setLanguage(language);
		super.setId(0L);
	}

	public FlashSaleDescription() {

	}

	public FlashSale getFlashSale() {
		return flashSale;
	}

	public void setFlashSale(FlashSale flashSale) {
		this.flashSale = flashSale;
	}
}
