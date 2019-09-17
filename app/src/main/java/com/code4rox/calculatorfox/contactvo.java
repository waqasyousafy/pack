package com.code4rox.calculatorfox;

import android.graphics.Bitmap;

public class contactvo {
    public Bitmap ContactImage;
    public String ContactName;
    public String ContactNumber;

    public Bitmap getContactImage() {
        return ContactImage;
    }

    public void setContactImage(Bitmap contactImage) {
        this.ContactImage = contactImage;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        this.ContactName = contactName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.ContactNumber = contactNumber;
    }
}
