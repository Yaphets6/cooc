package cooc.common.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.cvm.v20170312.CvmClient;
import cooc.conf.Args;

/**
 * Creater yangyk
 * time 2026/1/8 0008:20:44
 * package cooc.common.utils
 */
public class TencentCloud {

    

    public static Credential getCredential(){
        return new Credential(Args.SECRET_ID.getValue(), Args.SECRET_KEY.getValue());
    }

    public static ClientProfile getClientProfile(String endpoint){
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(endpoint);
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        return clientProfile;
    }
}
