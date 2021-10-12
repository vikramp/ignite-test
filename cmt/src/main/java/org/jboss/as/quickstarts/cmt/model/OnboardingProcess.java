package org.jboss.as.quickstarts.cmt.model;


import org.apache.ignite.binary.BinaryObjectException;
import org.apache.ignite.binary.BinaryReader;
import org.apache.ignite.binary.BinaryWriter;
import org.apache.ignite.binary.Binarylizable;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.Serializable;
import java.util.Random;

@Entity
@Table(name = "OnBoardingProcess")
public class OnboardingProcess implements Serializable {


    @Id
    private String customerName;

    @Column(nullable = false)
    private String status;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status.toLowerCase();
    }

    public void setStatus(OnboardingState status) {
        this.status = status.name().toLowerCase();
    }

    public OnboardingProcess( String customerName, OnboardingState status) {
        this.customerName = customerName;
        this.status = status.name().toLowerCase();
    }

    public OnboardingProcess() {

    }

}

