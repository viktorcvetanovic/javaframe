package data;

import enums.config.PropertyValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ConfigProperty {
    private PropertyValue propertyName;
    private String propertyValue;
}
