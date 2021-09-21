package util.properties;

import data.ConfigProperty;
import enums.config.PropertyValue;
import exception.server.InvalidPropertiesFileException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Properties extends FileFinder {

    private String readProperties() {
        String propFileName = "app.properties";
        InputStream inputStream = findFileInResourceByName(propFileName);
        if (inputStream == null) {
            throw new InvalidPropertiesFileException("Your property file is not on good location or you haven't created it");
        }
        var bf = new BufferedInputStream(inputStream);
        try {
            return new String(bf.readAllBytes());
        } catch (IOException e) {
            throw new InvalidPropertiesFileException("Your property file is not on good location or you haven't created it");
        }
    }

    public List<ConfigProperty> getProperties() {
        String s = readProperties();
        return Arrays.stream(s.trim().split("\n"))
                .filter(e -> {
                    var helpArr = e.trim().split("=");
                    PropertyValue propertyValue = PropertyValue.convertStringToEnum(helpArr[0]);
                    if (propertyValue == null) {
                        return false;
                    }
                    return true;
                })
                .map(e -> {
                    var helpArr = e.trim().split("=");
                    PropertyValue propertyValue = PropertyValue.convertStringToEnum(helpArr[0]);
                    return new ConfigProperty(propertyValue, helpArr[1]);
                }).collect(Collectors.toList());
    }

    public ConfigProperty filterPropertyByPropertyEnum(PropertyValue propertyValue, List<ConfigProperty> properties) {
        return properties.stream().filter(e -> e.getPropertyName() == propertyValue).findFirst().orElse(null);

    }
}
