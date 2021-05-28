package ltd.newbee.mall.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * author: mumu
 * date: 2021/5/27
 */
@ConfigurationProperties(prefix = "dpta")
@Data
public class DptaProperties{

    private String url = "";

    private String adminFrontUrl = "";

    private String distrFrontUrl = "";

}
