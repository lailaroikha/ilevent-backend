package com.ilevent.ilevent_backend.config;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


public class CloudinaryConfig {
    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dnt8swtrr",
            "api_key", "432844884133469",
            "api_secret", "qshTD03IXbhO_C9sTqcmSsqFbhg"
    ));

    public static Cloudinary getCloudinary() {
        return cloudinary;
    }

}
