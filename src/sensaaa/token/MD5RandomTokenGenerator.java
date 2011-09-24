package sensaaa.token;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class MD5RandomTokenGenerator implements TokenGenerator {
    
    private Random random = new Random();
    private String salt = "salt";

    @Override
    public String createToken() {
        String code = this.salt + random.nextInt(100000) + System.currentTimeMillis() + random.nextInt(100000);
        // hash here
        return code;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
