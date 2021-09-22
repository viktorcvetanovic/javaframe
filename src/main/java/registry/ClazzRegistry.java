package registry;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import util.classutil.ClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClazzRegistry {
    private Map<Class<?>, Object> map = new HashMap<>();
    private static ClazzRegistry instance=new ClazzRegistry();

    public static ClazzRegistry getInstance(){
        return instance;
    }

    public Object get(Class<?> clazz) {
        return map.get(clazz);
    }

    public void set(Class<?> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var obj = new ClassUtil().getBestConstructor(clazz).newInstance();
        map.put(clazz, obj);
    }

    public Set<Class<?>> getAllKeys() {
        return map.keySet();
    }


}
