package com.salesmanager.core.model.system;

import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SYSTEM_TIMER_TASK", uniqueConstraints =
@UniqueConstraint(columnNames = {"MERCHANT_ID", "CORN_KEY"}))
public class SystemTimerTask extends SalesManagerEntity<Long, SystemTimerTask> implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @Column(name = "SYSTEM_TIMER_TASK_ID")
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "SYST_NOTIF_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Column(name = "CORN_KEY", nullable = false)
    private String key;

    @Column(name = "CRON", nullable = false)
    private String cron;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANT_ID")
    private MerchantStore merchantStore;

    @JoinColumn(name = "USER_ID")
    private Long ownerId;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE_AT")
    private Date createdAt;
    @Column(name = "AVAILABLE")
    private boolean available = false;

    @Column(name = "TASK_METHOD", nullable = false)
    private String taskMethod;

    @Column(name = "TASK_FULL_CLASS_NAME", nullable = false)
    private String taskFullyQulifiedClassName;

    /**
     * [java.lang.String,java.lang.Integer]*
     */
    @Column(name = "TASK_ORDERED_PARAMETER_FIELDS")
    private String taskOrderedParameterFields;
    /**
     * ["text",3]*
     */
    @Column(name = "TASK_ORDERED_PARAMETER")
    private String taskOrderedParameters;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setMerchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
    }

    public MerchantStore getMerchantStore() {
        return merchantStore;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getTaskMethod() {
        return taskMethod;
    }

    public void setTaskMethod(String taskMethod) {
        this.taskMethod = taskMethod;
    }

    public String getTaskFullyQulifiedClassName() {
        return taskFullyQulifiedClassName;
    }

    public void setTaskFullyQulifiedClassName(String taskFullyQulifiedClassName) {
        this.taskFullyQulifiedClassName = taskFullyQulifiedClassName;
    }

    public String getTaskOrderedParameterFields() {
        return taskOrderedParameterFields;
    }

    public void setTaskOrderedParameterFields(String taskOrderedParameterFields) {
        this.taskOrderedParameterFields = taskOrderedParameterFields;
    }

    public String getTaskOrderedParameters() {
        return taskOrderedParameters;
    }

    public void setTaskOrderedParameters(String taskOrderedParameter) {
        this.taskOrderedParameters = taskOrderedParameter;
    }
}
