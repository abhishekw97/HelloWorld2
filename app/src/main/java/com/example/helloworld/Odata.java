package com.example.helloworld;
import com.google.android.gms.common.data.SingleRefDataBufferIterator;

public class Odata {
        String oId;
        String oName;
        String oAddress;
        String oContact;
        String oRequirements;

        public Odata()
        {

        }

        public Odata(String oId, String oName, String oAddress, String oContact, String oRequirements) {
            this.oId = oId;
            this.oName = oName;
            this.oAddress = oAddress;
            this.oContact = oContact;
            this.oRequirements = oRequirements;
        }

        public String getoId() {
            return oId;
        }

        public String getoName() {
            return oName;
        }

        public String getoAddress() {
            return oAddress;
        }

        public String getoContact() {
            return oContact;
        }

        public String getoRequirements() {
            return oRequirements;
        }
    }

